package ch.admin.bar.siard2.cmd.utils.db.column;

import ch.admin.bar.siard2.api.MetaColumn;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class OracleDataTypeConverter extends AbstractDataTypeConverter {

    private static final Map<String, String> COMPATIBLE_DATA_TYPE_MAP = Map.of(
//            "BIGINT", "NUMBER",
//            "TINYINT", "NUMBER"
    );

    private static final Set<String> SUPPORTED_DATA_TYPE = Set.of(
            "CHAR", "VARCHAR", "VARCHAR2", "NCHAR", "NVARCHAR2",
            "RAW", "ROWID", "INT", "INTEGER", "SMALLINT",
            "NUMBER", "DECIMAL", "FLOAT", "BINARY_DOUBLE", "BINARY_FLOAT",
            "BINARY DOUBLE", "BINARY FLOAT", "DATE", "TIMESTAMP", "BLOB",
            "CLOB", "BFILE", "INTERVAL"
    );

    private static final Map<String, String> DEFAULT_COLUMN_SPECIFIER = Map.of(
            "VARCHAR", "4000",
            "VARCHAR2", "4000",
            "NCHAR", "2000",
            "NVARCHAR2", "4000",
            "NUMBER", "38, 0",
            "RAW", "2000"
    );

    public OracleDataTypeConverter(String databaseProductName, MetaColumn metaColumn) throws IOException {
        super(databaseProductName, metaColumn);
    }

    @Override
    protected String getAlternativeType(String dataType) {
        return COMPATIBLE_DATA_TYPE_MAP.get(dataType);
    }

    @Override
    protected String getDefaultSpecifier(String dataType) {
        return DEFAULT_COLUMN_SPECIFIER.get(dataType);
    }

    @Override
    protected boolean requiresSpecifier(String dataType) {
        return DEFAULT_COLUMN_SPECIFIER.containsKey(dataType);
    }

}