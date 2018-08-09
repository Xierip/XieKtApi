package pl.xierip.xieapi.storage;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import pl.xierip.xieapi.XieAPI;
import pl.xierip.xieapi.config.Config;
import pl.xierip.xieapi.config.ConfigManager;
import pl.xierip.xieapi.config.exception.ConfigException;
import pl.xierip.xieapi.logging.Logging.LogType;

/**
 * @author Xierip
 */
public class ConfigXC {

  @Getter
  private static Config config;
  @Getter
  private static Map<Integer, Long> lvls = new HashMap<>();
  @Getter
  private static String mysqlHOST, mysqlPASSWORD, mysqlUSER, mysqlDATABASE;
  @Getter
  private static Integer mysqlQueueSize, packetQueueSize;

  public static boolean enable(final JavaPlugin plugin) {
    try {
      config = ConfigManager.register("xiektapi", "config.yml", plugin);
      mysqlHOST = config.getString("XieAPI.Database.host");
      mysqlUSER = config.getString("XieAPI.Database.user");
      mysqlPASSWORD = config.getString("XieAPI.Database.password");
      mysqlDATABASE = config.getString("XieAPI.Database.database");
      mysqlQueueSize = config.getInt("XieAPI.Database.queueSize");
      packetQueueSize = config.getInt("XieAPI.Packet.queueSize");
      return true;
    } catch (final ConfigException e) {
      XieAPI.getLogging().log(LogType.ERROR, "[CONFIG] Error with loading messages:");
      XieAPI.getLogging().log(LogType.ERROR, "[CONFIG] " + e.getMessage());
      return false;
    }
  }


}
