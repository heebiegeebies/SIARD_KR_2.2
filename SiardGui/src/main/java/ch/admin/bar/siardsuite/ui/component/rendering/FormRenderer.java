package ch.admin.bar.siardsuite.ui.component.rendering;

import ch.admin.bar.siardsuite.framework.errors.ErrorHandler;
import ch.admin.bar.siardsuite.framework.i18n.DisplayableText;
import ch.admin.bar.siardsuite.framework.i18n.keys.I18nKey;
import ch.admin.bar.siardsuite.ui.component.rendering.model.*;
import ch.admin.bar.siardsuite.util.I18n;
import ch.admin.bar.siardsuite.util.OptionalHelper;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class FormRenderer<T> {

    private static final I18nKey VALIDATION_ERRORS = I18nKey.of("storage.failed.validationErrors");
    private static final I18nKey UNKNOWN_ERROR = I18nKey.of("storage.failed.unknownError");
    private static final I18nKey SINGLE_FIELD_UNKNOWN_ERROR = I18nKey.of("storage.singleField.failed.unknownError");

    private static final String TITLE_STYLE_CLASS = "table-container-label";
    private static final String VALIDATION_STYLE_CLASS = "validation-text";
    private static final String FIELD_STYLE_CLASS = "rendered-field";

    @Getter
    private final RenderableForm<T> renderableForm;
    private final T data;

    private final ErrorHandler errorHandler;

    private final List<EditableFormField> editableFormFields = new ArrayList<>();
    private final List<SearchableFormEntry> searchableFormEntries = new ArrayList<>();

    private final BooleanProperty hasChanged;

    @Setter
    @Getter
    private VBox rendered;

    @Builder
    public FormRenderer(
            @NonNull final RenderableForm<T> renderableForm,
            @NonNull final ErrorHandler errorHandler,
            @NonNull final BooleanProperty hasChanged
    ) {
        this.renderableForm = renderableForm;
        this.errorHandler = errorHandler;
        this.hasChanged = hasChanged;

        this.data = renderableForm.getDataSupplier().get();
        this.rendered = renderForm();
    }

    private VBox renderForm() {
        val vbox = new VBox();
        val groups = renderableForm.getGroups().stream()
                .map(renderableGroup -> createGroup(renderableGroup, data))
                .collect(Collectors.toList());

        vbox.getChildren().setAll(groups);
        vbox.setSpacing(40); // space between groups
        VBox.setVgrow(vbox, Priority.ALWAYS);

        return vbox;
    }

    private VBox createGroup(final RenderableFormGroup<T> group, final T data) {
        val vbox = new VBox();
        val fields = group.getProperties().stream()
                .map(renderableProperty -> {
                    if (renderableProperty instanceof ReadWriteStringProperty) {
                        return createField((ReadWriteStringProperty<T>) renderableProperty, data);
                    }

                    if (renderableProperty instanceof ReadOnlyStringProperty) {
                        return createField((ReadOnlyStringProperty<T>) renderableProperty, data);
                    }

                    if (renderableProperty instanceof RenderableTable) {
                        val renderer = TableRenderer.<T, Object>builder()
                                .data(data)
                                .renderableTable((RenderableTable<T, Object>) renderableProperty)
                                .build();
                        searchableFormEntries.add(renderer);

                        return renderer
                                .render();
                    }

                    if (renderableProperty instanceof RenderableLazyLoadingTable) {
                        return LazyLoadingTableRenderer.<T, Object>builder()
                                .dataHolder(data)
                                .errorHandler(errorHandler)
                                .renderableTable((RenderableLazyLoadingTable<T, Object>) renderableProperty)
                                .build()
                                .render();
                    }

                    throw new IllegalArgumentException(String.format(
                            "Property type %s ins not supported yet.",
                            renderableProperty.getClass().getName()
                    ));
                })
                .collect(Collectors.toList());

        vbox.getChildren().setAll(fields);
        vbox.setSpacing(10);
        VBox.setVgrow(vbox, Priority.ALWAYS);

        return vbox;
    }

    private VBox createField(final ReadWriteStringProperty<T> property, final T data) {
        if (renderableForm.isReadOnlyForm()) {
            return new ReadOnlyFormField<>(
                    property.getTitle(),
                    property.getValueExtractor(),
                    data);
        }

        val formField = new EditableFormField<>(property, data, hasChanged);
        editableFormFields.add(formField);

        return formField;
    }

    private VBox createField(final ReadOnlyStringProperty<T> property, final T data) {
        return new ReadOnlyFormField<>(property, data);
    }

    public void dropChanges() {
        this.editableFormFields.forEach(EditableFormField::reset);
        this.hasChanged.set(false);
    }

    public SaveChangesReport saveChanges() {
        val invalidFields = this.editableFormFields.stream()
                .filter(editableFormField -> !editableFormField.hasValidValue())
                .collect(Collectors.toList());

        if (!invalidFields.isEmpty()) {
            return new SaveChangesReport(DisplayableText.of(VALIDATION_ERRORS).getText());
        }

        val failedFields = this.editableFormFields.stream()
                .filter(editableFormField -> !editableFormField.save())
                .collect(Collectors.toList());

        try {
            this.renderableForm.getAfterSaveAction()
                    .doAfterSaveChanges(data);

            if (failedFields.isEmpty()) {
                hasChanged.set(false);
                return new SaveChangesReport();
            }
        } catch (Exception e) {
            log.error("Storage failed because of after-storage-action because {}",
                    e.getMessage());
        }
        return new SaveChangesReport(DisplayableText.of(UNKNOWN_ERROR).getText());
    }

    public void applySearchTerm(final String searchTerm) {
        searchableFormEntries.forEach(entry -> entry.applySearchTerm(searchTerm));
    }

    public void clearSearchTerm() {
        searchableFormEntries.forEach(SearchableFormEntry::clearSearchTerm);
    }

    public boolean hasSearchableData() {
        return !searchableFormEntries.isEmpty();
    }

    private static class ReadOnlyFormField<T> extends VBox {

        private final Label titleLabel;
        private final TextField valueTextField;

        public ReadOnlyFormField(
                @NonNull final DisplayableText title,
                @NonNull final Function<T, String> valueExtractor,
                @NonNull final T data) {

            titleLabel = new Label();
            titleLabel.textProperty()
                    .bind(I18n.bind(title));
            titleLabel.getStyleClass().add(TITLE_STYLE_CLASS);

            val value = valueExtractor.apply(data);

            valueTextField = new TextField();
            valueTextField.setText(value);
            valueTextField.setEditable(false);
            valueTextField.getStyleClass().add(FIELD_STYLE_CLASS);

            this.getChildren().setAll(titleLabel, valueTextField);
        }

        public ReadOnlyFormField(
                @NonNull final ReadOnlyStringProperty<T> property,
                @NonNull final T data) {
            this(property.getTitle(), property.getValueExtractor(), data);
        }
    }

    private static class EditableFormField<T> extends VBox {

        private final Label title;
        private final TextField value;
        private final Label validationMsg;

        private final ReadWriteStringProperty<T> property;
        private final T data;

        public EditableFormField(
                final ReadWriteStringProperty<T> property,
                final T data,
                final BooleanProperty hasChanged) {
            this.property = property;
            this.data = data;

            title = new Label();
            val titleSuffix = property.getValueValidators().stream()
                    .map(validator -> validator.getTitleSuffix().orElse(""))
                    .collect(Collectors.joining());

            title.textProperty()
                    .bind(Bindings
                            .concat(I18n.bind(property.getTitle()))
                            .concat(titleSuffix));
            title.getStyleClass().add(TITLE_STYLE_CLASS);

            // 제목 로그 출력
          //  System.out.println("Title: " + property.getTitle().getText() + titleSuffix);

            value = new TextField();
            value.getStyleClass().add(FIELD_STYLE_CLASS);

         //  System.out.println("Value: " + property.getValueExtractor().apply(data));

            validationMsg = new Label();
            validationMsg.getStyleClass().add(VALIDATION_STYLE_CLASS);
            hideValidationLabel();

            reset();
            value.textProperty()
                    .addListener((observable, oldValue, newValue) -> hasChanged.set(true));

            this.getChildren().setAll(title, value, validationMsg);
        }

        public boolean hasValidValue() {
            val currentValue = value.getText();
            val failedValidator = this.property.getValueValidators().stream()
                    .filter(validator -> !validator.getIsValidCheck().test(currentValue))
                    .findAny();

            OptionalHelper.ifPresentOrElse(
                    failedValidator,
                    validator -> showValidationLabel(validator.getMessage()),
                    this::hideValidationLabel
            );

            return !failedValidator.isPresent();
        }

        public void reset() {
            val originalValue = property.getValueExtractor().apply(data);
            value.setText(originalValue);
            hideValidationLabel();
        }

        public boolean hasChanges() {
            val originalValue = property.getValueExtractor().apply(data);
            return !Objects.equals(originalValue, value.getText());
        }

        public boolean save() {
            if (!hasChanges()) {
                return true;
            }

            val currentValue = value.getText();

            try {
                property.getValuePersistor()
                        .persist(data, currentValue);
                return true;
            } catch (Exception e) {
                log.error("Storage failed for field {} with value {} because {}",
                        title.getText(),
                        currentValue,
                        e.getMessage());
                showValidationLabel(DisplayableText.of(SINGLE_FIELD_UNKNOWN_ERROR));
                return false;
            }
        }

        private void showValidationLabel(final DisplayableText message) {
            validationMsg.setText(message.getText());
            validationMsg.setVisible(true);
            validationMsg.setManaged(true);
        }

        private void hideValidationLabel() {
            validationMsg.setVisible(false);
            validationMsg.setManaged(false); // otherwise, the VBox still does reserve space for the label
        }
    }
}
