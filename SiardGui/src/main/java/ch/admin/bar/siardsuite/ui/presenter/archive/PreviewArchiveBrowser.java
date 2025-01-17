package ch.admin.bar.siardsuite.ui.presenter.archive;

import ch.admin.bar.siard2.api.Archive;
import ch.admin.bar.siardsuite.model.TreeAttributeWrapper;
import ch.admin.bar.siardsuite.ui.component.ButtonBox;
import ch.admin.bar.siardsuite.framework.errors.ErrorHandler;
import ch.admin.bar.siardsuite.framework.ServicesFacade;
import ch.admin.bar.siardsuite.framework.dialogs.Dialogs;
import ch.admin.bar.siardsuite.framework.steps.StepperNavigator;
import ch.admin.bar.siardsuite.model.Tuple;
import ch.admin.bar.siardsuite.ui.View;
import ch.admin.bar.siardsuite.model.database.SiardArchive;
import ch.admin.bar.siardsuite.ui.presenter.archive.browser.GenericArchiveBrowserPresenter;
import ch.admin.bar.siardsuite.ui.presenter.archive.browser.TreeBuilder;
import ch.admin.bar.siardsuite.service.database.model.DbmsConnectionData;
import ch.admin.bar.siardsuite.framework.view.LoadedView;
import ch.admin.bar.siardsuite.framework.i18n.DisplayableText;
import ch.admin.bar.siardsuite.framework.i18n.keys.I18nKey;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import lombok.val;

import java.util.*;
import java.util.stream.Collectors;

import static ch.admin.bar.siardsuite.ui.component.ButtonBox.Type.DEFAULT;
import static ch.admin.bar.siardsuite.ui.presenter.archive.browser.GenericArchiveBrowserPresenter.*;

/**
 * 스키마 및 엔티티 선택 화면
 */
public class PreviewArchiveBrowser {

    private static final I18nKey TITLE = I18nKey.of("archivePreview.view.title");
    private static final I18nKey TEXT = I18nKey.of("archivePreview.view.text");

    private ButtonBox buttonsBox;
    private LoadedView<GenericArchiveBrowserPresenter> loadedView;

    public Node getView() {
        //Preview 에서는 search in record 버튼을 비활성화하기 위해
        // GenericArchiveBrowserPresenter 에서 removeRecordSearchButton 호출
        GenericArchiveBrowserPresenter presenter = loadedView.getController();
        presenter.removeRecordSearchButton();
        presenter.removeResetButton();
        return loadedView.getNode();
    }

    public void init(
            final Archive archive,
            final DbmsConnectionData connectionData,
            final StepperNavigator<Tuple<Archive, DbmsConnectionData>> navigator,
            final Dialogs dialogs,
            final ErrorHandler errorHandler
    ) {
        val archiveBrowserView = TreeBuilder.builder()
                .siardArchive(new SiardArchive("", archive, true))
                .readonly(true)
                .columnSelectable(false)
                .build();

        TreeItem<TreeAttributeWrapper> rootItem = archiveBrowserView.createRootItem();
        this.buttonsBox = new ButtonBox().make(DEFAULT);

        // next 버튼 이벤트 등록
        buttonsBox.next().setOnAction(
                (event) -> {
                    // 선택된 엔티티 조회
                    Map<String, List<String>> selectedSchemaTableMap = getSelectedSchemaTableMap(rootItem);
                    if (!selectedSchemaTableMap.isEmpty())
                        archive.setSelectedSchemaTableMap(selectedSchemaTableMap); // 스키마, 테이블 세팅
                    navigator.next(new Tuple<>(archive, connectionData));
                }
        );

        buttonsBox.previous().setOnAction((event) -> navigator.previous());
        buttonsBox.cancel().setOnAction(
                (event) -> dialogs
                        .open(View.ARCHIVE_ABORT_DIALOG));

        this.loadedView = GenericArchiveBrowserPresenter.load(
                dialogs,
                errorHandler,
                DisplayableText.of(TITLE),
                DisplayableText.of(TEXT),
                this.buttonsBox,
                rootItem,
                archiveBrowserView,
                ArchiveStep.PREVIEW
        );
    }

    public static LoadedView<PreviewArchiveBrowser> load(
            final Tuple<Archive, DbmsConnectionData> data,
            final StepperNavigator<Tuple<Archive, DbmsConnectionData>> navigator,
            final ServicesFacade servicesFacade
    ) {
        val browser = new PreviewArchiveBrowser();
        browser.init(
                data.getValue1(),
                data.getValue2(),
                navigator,
                servicesFacade.dialogs(),
                servicesFacade.errorHandler()
        );
        return new LoadedView<>(browser::getView, browser);
    }


    /**
     * 체크박스에 체크된 아이템
     *
     * @param rootItem 생성한 루트 아이템
     * @return
     */
    private Map<String, List<String>> getSelectedSchemaTableMap(TreeItem<TreeAttributeWrapper> rootItem) {
        List<TreeAttributeWrapper> selectedTables = new ArrayList<>();
        collectSelectedTables(rootItem, selectedTables);
        return getSchemaTableMap(selectedTables);
    }

    private interface KeyValueMapper<T, K> {
        K map(T function);
    }

    public Map<String, List<String>> getSchemaTableMap(List<TreeAttributeWrapper> selectedCheckBoxes) {

        // 스키마명
        KeyValueMapper<TreeAttributeWrapper, String> keyMapper = attr -> attr.getDatabaseTable().getTable().getParentSchema().getMetaSchema().getName();

        // 테이블명
        KeyValueMapper<TreeAttributeWrapper, String> valueMapper = attr -> attr.getDatabaseTable().getTable().getMetaTable().getName(); //

        return selectedCheckBoxes.stream()
                .collect(
                        Collectors.groupingBy(
                                keyMapper::map,
                                Collectors.mapping(
                                        valueMapper::map, Collectors.toList()
                                )
                        )
                );
    }

    private void collectSelectedTables(TreeItem<TreeAttributeWrapper> item, List<TreeAttributeWrapper> selectedTables) {
        TreeAttributeWrapper attr = item.getValue();

        if (attr == null) return;
        if (attr.isSelected() && attr.isTransferable()) {
            selectedTables.add(item.getValue());
        }
        if (attr.shouldPropagate()) {
            item.getChildren().
                    forEach(child -> collectSelectedTables(child, selectedTables));
        }
    }
}
