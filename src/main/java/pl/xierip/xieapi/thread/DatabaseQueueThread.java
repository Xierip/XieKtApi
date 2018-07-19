package pl.xierip.xieapi.thread;

import lombok.Getter;
import pl.xierip.xieapi.database.DatabaseMySQL;
import pl.xierip.xieapi.XieAPI;
import pl.xierip.xieapi.config.ConfigXC;
import pl.xierip.xieapi.enums.LogType;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author Xierip
 */
public class DatabaseQueueThread extends Thread {

    private DatabaseMySQL mysql;
    @Getter
    private BlockingQueue<String> queue;
    private Thread thread;
    private boolean works = true;

    public DatabaseQueueThread(final DatabaseMySQL databaseMySQL) {
        mysql = databaseMySQL;
        thread = this;
        queue = new ArrayBlockingQueue<>(ConfigXC.getMysqlQueueSize());
        thread.start();
    }

    public void disableQueue() {
        if (mysql == null) return;
        try {
            XieAPI.getLogging().log(LogType.INFO, "Disabling SQL queue!");
            works = false;
            final long time = System.currentTimeMillis();
            thread = null;
            final Connection connection = mysql.getHikariDataSource().getConnection();
            final Statement statement = connection.createStatement();
            connection.setAutoCommit(false);
            int i = 0;
            for (final String query : queue) {
                statement.addBatch(query);
                i++;
            }
            statement.executeBatch();
            statement.close();
            connection.setAutoCommit(true);
            connection.close();
            XieAPI.getLogging().log(LogType.INFO, "SQL queue was disabled with " + i + " records and time: " + ((System.currentTimeMillis() - time)) + "ms.");
        } catch (final SQLException e) {
            XieAPI.getLogging().log(LogType.EXCEPTION, "Error with query", e);
        }
    }

    @Override
    public void run() {
        while (works) {
            if (XieAPI.isDisabling()) {
                if (thread != null)
                    thread.interrupt();
            } else {
                try {
                    final String remove = queue.take();
                    try {
                        mysql.checkConnection();
                        final Statement statement = mysql.getConnection().createStatement();
                        statement.executeUpdate(remove);
                        statement.close();
                    } catch (final Exception ex) {
                        XieAPI.getLogging().log(LogType.EXCEPTION, "Queue execute error \"" + remove + "\"", ex);
                    }
                } catch (final InterruptedException ignored) {
                }
            }
        }
    }

}
