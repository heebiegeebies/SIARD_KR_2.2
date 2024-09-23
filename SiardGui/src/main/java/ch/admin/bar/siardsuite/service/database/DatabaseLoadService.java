package ch.admin.bar.siardsuite.service.database;

import ch.admin.bar.siard2.api.Archive;
import ch.admin.bar.siard2.api.Schema;
import ch.admin.bar.siard2.cmd.ArchiveMapping;
import ch.admin.bar.siard2.cmd.MetaDataFromDb;
import ch.admin.bar.siard2.cmd.PrimaryDataFromDb;
import ch.admin.bar.siard2.cmd.PrimaryDataTransfer;
import ch.admin.bar.siardsuite.service.ArchiveHandler;
import ch.admin.bar.siardsuite.service.database.model.LoadDatabaseInstruction;
import ch.admin.bar.siardsuite.service.preferences.UserPreferences;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.util.Pair;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.sql.Connection;
import java.util.Map;

@Slf4j
public class DatabaseLoadService extends Service<ObservableList<Pair<String, Long>>> {
    private final DatabaseConnectionFactory connectionFactory;
    private final ArchiveHandler archiveHandler;
    private final UserPreferences userPreferences;

    private final LoadDatabaseInstruction instruction;

    public DatabaseLoadService(DatabaseConnectionFactory connectionFactory, ArchiveHandler archiveHandler, UserPreferences userPreferences, LoadDatabaseInstruction instruction) {
        this.connectionFactory = connectionFactory;
        this.archiveHandler = archiveHandler;
        this.userPreferences = userPreferences;
        this.instruction = instruction;

        this.setOnFailed(instruction.getOnFailure());
        this.valueProperty().addListener(instruction.getOnStepCompleted());
        this.progressProperty().addListener(instruction.getOnProgress());
    }

    @Override
    protected Task<ObservableList<Pair<String, Long>>> createTask() {
        return new DatabaseLoadTask(
                connectionFactory,
                archiveHandler,
                userPreferences,
                instruction
        );
    }

    @RequiredArgsConstructor
    private static class DatabaseLoadTask extends Task<ObservableList<Pair<String, Long>>> {

        private final DatabaseConnectionFactory connectionFactory;
        private final ArchiveHandler archiveHandler;
        private final UserPreferences userPreferences;

        private final LoadDatabaseInstruction instruction;

        @Override
        protected ObservableList<Pair<String, Long>> call() throws Exception {
            val connection = connectionFactory.getOrCreateConnection(instruction.getConnectionData());
            val timeout = userPreferences.getStoredOptions().getQueryTimeout();

            Archive archive = instruction.getSaveAt()
                    .map(archiveHandler::init)
                    .orElseGet(archiveHandler::init);

            // 선택한 엔티티로 교체
            Map<String, Schema> selectedSchemaMap = instruction.getSelectedSchemaMap();
            if (!selectedSchemaMap.isEmpty()) archive.replaceWithSelectedSchemaMap(selectedSchemaMap);

            val metaDataFromDb = MetaDataFromDb.newInstance(connection.getMetaData(), archive.getMetaData());
            metaDataFromDb.setQueryTimeout(timeout);

            updateValue(FXCollections.observableArrayList(new Pair<>("Metadata", -1L)));
            updateProgress(0, 100);

            // meta data 조회
            metaDataFromDb.download(
                    instruction.getViewsAsTables(),
                    false,
                    new SiardCmdProgressListener(this::updateProgress),
                    archive);

            instruction.getExternalLobs()
                    .ifPresent(uri -> archiveHandler.setExternalLobFolder(archive, uri));

            ObservableList<Pair<String, Long>> progressData = FXCollections.observableArrayList();
            if (!instruction.getLoadOnlyMetadata()) {

                PrimaryDataFromDb data = PrimaryDataFromDb.newInstance(connection, archive);
//                archive.test();

                data.setQueryTimeout(timeout);
                updateValue(FXCollections.observableArrayList(new Pair<>("Dataload", -1L)));
                updateProgress(0, 100);
                data.download(new SiardCmdProgressListener(this::updateProgress)); // 읽어들인 데이터 다운로드

                archive.getSelectedSchemaMap().forEach(
                        (schemaName, schema) -> schema.getSelectedTables().forEach(
                                table -> {
                                    Pair<String, Long> stringLongPair =
                                            new Pair<>(
                                                    schemaName + "." + table.getMetaTable().getName(),
                                                    table.getMetaTable().getRows()
                                            );
                                    progressData.add(stringLongPair);
                                    System.out.println("string long pair = " + stringLongPair);
                                }
                        )
                );

//                    for (int i = 0; i < archive.getSchemas(); i++) {
//                        Schema schema = archive.getSchema(i);
//                        for (int y = 0; y < schema.getTables(); y++) {
//                            Pair<String, Long> stringLongPair = new Pair<>(schema.getMetaSchema().getName() + "." + schema.getTable(y)
//                                    .getMetaTable()
//                                    .getName(),
//                                    schema.getTable(y).getMetaTable().getRows());
//                            progressData.add(stringLongPair);
//                            System.out.println("string long pair = " + stringLongPair);
//                        }
//                    }
                }

                updateValue(progressData);
//            }

            /*
            Workaround: It seems that the default onSucceed mechanism sometimes is not very stable in java fx 8.
            For that reason, the result is returned with a callback.
             */
            Platform.runLater(() -> instruction.getOnSuccess().accept(archive));

            return progressData;
        }
    }

}
