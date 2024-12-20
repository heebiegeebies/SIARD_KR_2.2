package ch.admin.bar.siardsuite.service.database;

import ch.admin.bar.dbexception.proxy.ConnectionProxy;
import ch.admin.bar.dbexception.proxy.DatabaseMetaDataProxy;
import ch.admin.bar.siardsuite.service.database.model.DbmsConnectionData;
import ch.admin.bar.siardsuite.service.preferences.UserPreferences;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;

import static ch.admin.bar.dbexception.DatabaseExceptionHandlerHelper.doHandleSqlException;

@Slf4j
public class DatabaseConnectionFactory {

    private final AtomicReference<EstablishedConnection> connectionCache = new AtomicReference();

    public void disconnect() {
        connectionCache.updateAndGet(nullableEstablishedConnection -> {
            if (nullableEstablishedConnection != null) {
                nullableEstablishedConnection.close();
            }

            return null;
        });
    }

    public Connection getOrCreateConnectionProxy(final DbmsConnectionData connectionData) {
        return connectionCache.updateAndGet(establishedConnection -> {
            try {
                if (establishedConnection != null) {
                    if (!establishedConnection.getConnection().isClosed() &&
                            establishedConnection.getDbmsConnectionData().equals(connectionData)) {
                        // connection can be re-used
                        log.info("Re-use previously established connection (Properties: {})", connectionData);
                        return establishedConnection;
                    } else {
                        establishedConnection.close();
                    }
                }

                return new EstablishedConnection(createConnectionProxy(connectionData), connectionData);
            } catch (SQLException sqlException) {
                System.out.println("no connection made");
                String databaseProductName = connectionData.getDbms().getName();
                doHandleSqlException(databaseProductName, null, sqlException);
                throw new RuntimeException(sqlException); // 위에서 처리안되는 경우 원본 예외 출력
            }
        }).getConnection();
    }

    private static Connection createConnectionProxy(final DbmsConnectionData connectionData) throws SQLException {
        log.info("Create new connection (Properties: {})", connectionData);

        loadDriver(connectionData.getDbms().getDriverClassName());

        val options = UserPreferences.INSTANCE.getStoredOptions();
        DriverManager.setLoginTimeout(options.getLoginTimeout());

        val connection = DriverManager.getConnection(
                connectionData.getJdbcConnectionString(),
                connectionData.getUser(),
                connectionData.getPassword());
        connection.setAutoCommit(false);
        // setting proxy
        Connection connectionProxy = ConnectionProxy.createProxy(connection);
        return connectionProxy;
    }

    private static void loadDriver(String jdbcDriverClass) {
        try {
            Class.forName(jdbcDriverClass);
        } catch (ClassNotFoundException var7) {
            throw new RuntimeException("Driver " + jdbcDriverClass + " could not be loaded!");
        }
    }

    @Value
    private static class EstablishedConnection {
        @NonNull
        Connection connection;
        @NonNull
        DbmsConnectionData dbmsConnectionData;

        public void close() {
            try {
                log.info("Close previously established connection (Properties: {})", dbmsConnectionData);
                connection.close();
            } catch (SQLException e) {
                log.error("Can not close established connection for properties {} cause '{}'",
                        dbmsConnectionData,
                        e.getMessage());
            }
        }
    }
}
