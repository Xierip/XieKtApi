package pl.xierip.xieapi.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import pl.xierip.xieapi.XieAPI;
import pl.xierip.xieapi.enums.LogType;
import pl.xierip.xieapi.interfaces.QueryCallback;
import pl.xierip.xieapi.thread.DatabaseQueueThread;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Xierip
 */
public class DatabaseMySQL {
    @Getter
    private final HikariDataSource hikariDataSource;
    private BukkitTask task = null;
    @Getter
    private Connection connection;
    @Getter
    private DatabaseQueueThread queueThread;

    public DatabaseMySQL(final String mysqlHOST, final String mysqlDATABASE, final String mysqlUSER, final String mysqlPASSWORD) {
        final HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://" + mysqlHOST + ":3306/" + mysqlDATABASE);
        config.setUsername(mysqlUSER);
        if (mysqlPASSWORD != null && !mysqlPASSWORD.isEmpty()) {
            config.setPassword(mysqlPASSWORD);
        }
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");
        config.addDataSourceProperty("useLocalSessionState", "true");
        config.addDataSourceProperty("useLocalTransactionState", "true");
        config.addDataSourceProperty("rewriteBatchedStatements", "true");
        config.addDataSourceProperty("cacheResultSetMetadata", "true");
        config.addDataSourceProperty("cacheServerConfiguration", "true");
        config.addDataSourceProperty("elideSetAutoCommits", "true");
        config.addDataSourceProperty("maintainTimeStats", "false");
        final long time = System.currentTimeMillis();
        hikariDataSource = new HikariDataSource(config);
        try {
            connection = hikariDataSource.getConnection();
            XieAPI.getLogging().log(LogType.INFO, "Connected to database with &c" + (System.currentTimeMillis() - time) + "ms&r");
        } catch (final SQLException e) {
            XieAPI.getLogging().log(LogType.EXCEPTION, "Error with connect to database!", e);
            return;
        }
        queueThread = new DatabaseQueueThread(this);
        task = new BukkitRunnable() {
            @Override
            public void run() {
                query("SELECT 1", true);
            }
        }.runTaskTimerAsynchronously(XieAPI.getInstance(), 1000, 1000);
    }

    public void disable() {
        if (task != null)
            task.cancel();
        queueThread.disableQueue();
        hikariDataSource.close();
    }

    public void checkConnection() {
        try {
            if (connection.isClosed() || connection == null) {
                connection = hikariDataSource.getConnection();
                XieAPI.getLogging().log(LogType.INFO, "Reconnected to database!");
            }
        } catch (SQLException e) {
            XieAPI.getLogging().log(LogType.EXCEPTION, "Error with reconnect to database!", e);
            try {
                connection = hikariDataSource.getConnection();
                XieAPI.getLogging().log(LogType.INFO, "Reconnected to database!");
            } catch (SQLException e1) {
                XieAPI.getLogging().log(LogType.EXCEPTION, "Error with reconnect to database2!", e);
            }
        }
    }

    public ResultSet query(final String sql, final boolean now) {
        try {
            if (sql.startsWith("SELECT")) {
                checkConnection();
                return connection.createStatement().executeQuery(sql);
            } else if (now) {
                checkConnection();
                final Statement statement = connection.createStatement();
                statement.executeUpdate(sql);
                statement.close();
            } else {
                queueThread.getQueue().add(sql);
            }
        } catch (final Exception e) {
            XieAPI.getLogging().log(LogType.EXCEPTION, "Error with query", e);
        }
        return null;
    }

    public void asyncQuery(final String query, final QueryCallback callback) {
        final DatabaseMySQL self = this;
        new BukkitRunnable() {
            @Override
            public void run() {
                callback.onRecive(self.query(query, true));
            }
        }.runTaskAsynchronously(XieAPI.getInstance());
    }

    public void update(final String sql) {
        if (sql.startsWith("SELECT")) {
            XieAPI.getLogging().log(LogType.ERROR, "MYSQL: SELECT cannot be used in update method {" + sql + "}");
        } else {
            queueThread.getQueue().add(sql);
        }
    }
}

