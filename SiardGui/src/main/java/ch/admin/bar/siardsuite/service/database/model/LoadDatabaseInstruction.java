package ch.admin.bar.siardsuite.service.database.model;

import ch.admin.bar.siard2.api.Archive;
import ch.admin.bar.siard2.api.Schema;
import ch.admin.bar.siard2.api.ext.form.FormData;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.util.Pair;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.io.File;
import java.net.URI;
import java.util.*;
import java.util.function.Consumer;

@Value
@Builder
public class LoadDatabaseInstruction {
    @NonNull
    DbmsConnectionData connectionData;

    @NonNull
    @Builder.Default
    Optional<File> saveAt = Optional.empty();

    @NonNull
    @Builder.Default
    Optional<URI> externalLobs = Optional.empty();

    @NonNull
    @Builder.Default
    Boolean loadOnlyMetadata = false;

    @NonNull
    @Builder.Default
    Boolean viewsAsTables = false;

    @NonNull
    @Builder.Default
    Consumer<Archive> onSuccess = archive -> {
    };

    @NonNull
    @Builder.Default
    EventHandler<WorkerStateEvent> onFailure = workerStateEvent -> {
    };

    @NonNull
    @Builder.Default
    ChangeListener<Number> onProgress = (observableValue, number, t1) -> {
    };

    @NonNull
    @Builder.Default
    ChangeListener<ObservableList<Pair<String, Long>>> onStepCompleted = (observableValue, pairs, t1) -> {
    };

    @Builder.Default
    Map<String, Schema> selectedSchemaMap = new HashMap<>();

    @Builder.Default
    Map<String, List<String>> selectedSchemaTableMap = new HashMap<>();

    @Builder.Default
    Boolean preview = true;

    @Builder.Default
    Set<FormData> formDataSet = new LinkedHashSet<>();

}
