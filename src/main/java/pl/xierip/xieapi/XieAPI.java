package pl.xierip.xieapi;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.xierip.xieapi.command.CommandsManager;
import pl.xierip.xieapi.enchantments.EnchantmentsUtil;
import pl.xierip.xieapi.listeners.AsyncPreLoginListener;
import pl.xierip.xieapi.listeners.EntityDamageByEntityListener;
import pl.xierip.xieapi.listeners.PlayerInteractListener;
import pl.xierip.xieapi.listeners.PlayerLeftListener;
import pl.xierip.xieapi.listeners.PlayerMoveListener;
import pl.xierip.xieapi.logging.Logging;
import pl.xierip.xieapi.logging.Logging.LogType;
import pl.xierip.xieapi.packet.AsyncPacketThread;
import pl.xierip.xieapi.storage.ConfigXC;
import pl.xierip.xieapi.tag.TagAPI;
import pl.xierip.xieapi.utils.PlayerUtil;
import pl.xierip.xieapi.utils.Util;


/**
 * @author Xierip
 */
public class XieAPI extends JavaPlugin {

  @Getter
  @Setter
  private static boolean disabling = false;
  @Getter
  private static XieAPI instance;
  @Getter
  private static Logging logging;
  @Getter
  private static ProtocolManager protocolManager;
  private AsyncPacketThread asyncPacketThread;

  public XieAPI() {
    instance = this;
  }

  public void disablePlugin() {
    XieAPI.setDisabling(true);
    instance.getPluginLoader().disablePlugin(instance);
  }

  private void disableSubPlugins() {
    for (final Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
      if (plugin.isEnabled() && plugin.getName().startsWith("Xie") && (!plugin.getName()
          .equals("XieAPI"))) {
        instance.getPluginLoader().disablePlugin(plugin);
      }
    }
  }

  @Override
  public void onDisable() {
    XieAPI.setDisabling(true);
    PlayerUtil.kickAll();
    disableSubPlugins();
    TagAPI.disable();
    AsyncPacketThread.sendPackets();
  }

  @Override
  public void onEnable() {
    logging = new Logging("XieAPI");
    if (!ConfigXC.enable(this)) {
      logging.log(LogType.INFO, "Disabling plugin by error with loading config!");
      disablePlugin();
      return;
    }
    logging.log(LogType.INFO, "Vault Hooked");
    protocolManager = ProtocolLibrary.getProtocolManager();
    EnchantmentsUtil.enable(this);
    Util.registerListeners(this, new AsyncPreLoginListener(),
        new PlayerLeftListener(), new EntityDamageByEntityListener(), new PlayerInteractListener(),
        new PlayerMoveListener());
    ((SimplePluginManager) Bukkit.getPluginManager()).useTimings(false);
    CommandsManager.enableCleaner();
    asyncPacketThread = new AsyncPacketThread();
    this.getServer().getScheduler().runTaskAsynchronously(this, asyncPacketThread);
  }

}
