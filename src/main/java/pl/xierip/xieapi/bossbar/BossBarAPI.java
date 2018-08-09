package pl.xierip.xieapi.bossbar;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.xierip.xieapi.XieAPI;
import pl.xierip.xieapi.utils.StringUtil;

/**
 * Created by xierip on 6/30/17. Web: http://xierip.pl
 */
public class BossBarAPI {

  private static Map<UUID, CustomBossBar> bossBarMap = new TreeMap<>();

  public static void removeBar(Player player) {
    if (bossBarMap.containsKey(player.getUniqueId())) {
      CustomBossBar customBossBar = bossBarMap.remove(player.getUniqueId());
      if (customBossBar != null) {
        customBossBar.destroy();
      }
    }
  }

  public static void setMessage(Player player, BarColor barColor, BarStyle barStyle, String title,
      int time, BarFlag... barFlags) {
    removeBar(player);
    final int[] currentTime = {time};
    BossBar bossBar = Bukkit
        .createBossBar(StringUtil.fixColors(title), barColor, barStyle, barFlags);
    bossBar.addPlayer(player);
    bossBar.setVisible(true);
    bossBarMap.put(player.getUniqueId(), new CustomBossBar(bossBar, new BukkitRunnable() {
          @Override
          public void run() {
            currentTime[0]--;
            bossBar.setProgress(((currentTime[0]) / ((double) time)));
            if (currentTime[0] <= 0) {
              bossBar.removePlayer(player);
              CustomBossBar remove = bossBarMap.remove(player.getUniqueId());
              if (remove != null) {
                remove.destroy();
              }
            }
          }
        }.runTaskTimer(XieAPI.getInstance(), 20, 20), player)
    );

  }

  public static void setMessage(Player player, BarColor barColor, BarStyle barStyle, String title,
      BarFlag... barFlags) {
    removeBar(player);
    BossBar bossBar = Bukkit
        .createBossBar(StringUtil.fixColors(title), barColor, barStyle, barFlags);
    bossBar.addPlayer(player);
    bossBar.setVisible(true);
    bossBarMap.put(player.getUniqueId(), new CustomBossBar(bossBar, null, player));
  }
}
