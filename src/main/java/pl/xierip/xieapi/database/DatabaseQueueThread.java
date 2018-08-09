package pl.xierip.xieapi.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import lombok.Getter;
import org.bukkit.scheduler.BukkitRunnable;
import pl.xierip.xieapi.XieAPI;
import pl.xierip.xieapi.logging.Logging.LogType;
import pl.xierip.xieapi.storage.ConfigXC;

/**
 * @author Xierip
 */
public class DatabaseQueueThread extends BukkitRunnable {

  private DatabaseMySQL mysql;
  @Getter
  private BlockingQueue<String> queue;
  private boolean works = true;

  public DatabaseQueueThread(final DatabaseMySQL databaseMySQL) {
    mysql = databaseMySQL;
    queue = new ArrayBlockingQueue<>(ConfigXC.getMysqlQueueSize());

  }

  public void disableQueue() {
    if (mysql == null) {
      return;
    }
    try {
      XieAPI.getLogging().log(LogType.INFO, "Disabling SQL queue!");
      works = false;
      final long time = System.currentTimeMillis();
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
      XieAPI.getLogging().log(LogType.INFO,
          "SQL queue was disabled with " + i + " records and time: " + ((System.currentTimeMillis()
              - time)) + "ms.");
    } catch (final SQLException e) {
      XieAPI.getLogging().log(LogType.EXCEPTION, "Error with query", e);
    }
  }

  @Override
  public void run() {
    while (works) {
      if (XieAPI.isDisabling()) {
        if (!this.isCancelled()) {
          this.cancel();
        }
      } else {
        try {
          final String remove = queue.take();
          try {
            mysql.checkConnection();
            final Statement statement = mysql.getConnection().createStatement();
            statement.executeUpdate(remove);
            statement.close();
          } catch (final Exception ex) {
            XieAPI.getLogging()
                .log(LogType.EXCEPTION, "Queue execute error \"" + remove + "\"", ex);
          }
        } catch (final InterruptedException ignored) {
        }
      }
    }
  }

}
