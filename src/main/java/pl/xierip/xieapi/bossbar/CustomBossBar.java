package pl.xierip.xieapi.bossbar;

import lombok.Data;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

/**
 * Created by xierip on 6/30/17. Web: http://xierip.pl
 */
public @Data
class CustomBossBar {

  private BossBar bossBar;
  private BukkitTask bukkitTask;
  private Player player;

  public CustomBossBar(BossBar bossBar, BukkitTask bukkitTask, Player player) {
    this.bossBar = bossBar;
    this.bukkitTask = bukkitTask;
    this.player = player;
  }

  public void destroy() {
    bossBar.removePlayer(player);
    bossBar.setVisible(false);
    bossBar.setTitle(null);
    if (bukkitTask != null) {
      bukkitTask.cancel();
    }
  }
}
