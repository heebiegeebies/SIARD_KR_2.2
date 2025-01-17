package ch.admin.bar.siardsuite.ui.common;

import ch.admin.bar.siardsuite.framework.i18n.DisplayableText;
import ch.admin.bar.siardsuite.framework.i18n.keys.I18nKey;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;

@Value
public class Validator<T> {
    private static final I18nKey CAN_NOT_BE_EMPTY = I18nKey.of("valueValidation.canNotBeEmpty");
    private static final I18nKey COLONS_NOT_ALLOWED = I18nKey.of("valueValidation.doesNotIncludeColons");
    private static final I18nKey SLASH_NOT_ALLOWED = I18nKey.of("connection.view.error.connection.name.symbol");
    private static final I18nKey NEED_TO_EXIST = I18nKey.of("valueValidation.needsToBeExistingFile");
    private static final I18nKey SHOULD_HAVE_NUMERIC_VALUE = I18nKey.of("valueValidation.");

    public static final Validator<String> IS_NOT_EMPTY_STRING_VALIDATOR = Validator.<String>builder()
            .message(DisplayableText.of(CAN_NOT_BE_EMPTY))
            .isValidCheck(nullableValue -> Optional.ofNullable(nullableValue)
                    .filter(value -> !value.isEmpty() && !value.trim().isEmpty())
                    .isPresent())
            .titleSuffix("*")
            .build();

    public static final Validator<String> DOES_NOT_INCLUDE_COLONS_VALIDATOR = Validator.<String>builder()
            .message(DisplayableText.of(COLONS_NOT_ALLOWED))
            .isValidCheck(nullableValue -> Optional.ofNullable(nullableValue)
                    .filter(value -> !value.contains(":"))
                    .isPresent())
            .build();

    public static final Validator<String> SLASH_NOT_ALLOWED_VALIDATOR = Validator.<String>builder()
            .message(DisplayableText.of(SLASH_NOT_ALLOWED))
            .isValidCheck(nullableValue -> Optional.ofNullable(nullableValue)
                    .filter(value -> !value.contains("/"))
                    .isPresent())
            .build();

    public static final Validator<File> IS_EXISTING_FILE_VALIDATOR = Validator.<File>builder()
            .message(DisplayableText.of(NEED_TO_EXIST))
            .isValidCheck(nullableValue -> Optional.ofNullable(nullableValue)
                    .filter(value -> value.isFile() && value.exists())
                    .isPresent())
            .build();

    public static final Pattern NUMERIC_VALUE = Pattern.compile("^\\d+$");

    public static final Validator<String> SHOULD_HAVE_NUMERIC_VALUE_VALIDATOR = Validator.<String>builder()
            .message(DisplayableText.of("should have numeric value"))
            .isValidCheck(
                    nonNullableValue -> Optional.of(nonNullableValue)
                            .filter(v -> NUMERIC_VALUE.matcher(v).matches())
                            .isPresent()
            )
            .build();

    @NonNull
    DisplayableText message;
    @NonNull
    Predicate<T> isValidCheck;
    @NonNull
    Optional<String> titleSuffix;

    @Builder
    public Validator(
            @NonNull final DisplayableText message,
            @NonNull final Predicate<T> isValidCheck,
            @Nullable final String titleSuffix) {
        this.message = message;
        this.isValidCheck = isValidCheck;
        this.titleSuffix = Optional.ofNullable(titleSuffix);
    }
}
