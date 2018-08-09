package pl.xierip.xieapi.actionbar;

import lombok.Data;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import pl.xierip.xieapi.utils.StringUtil;

/**
 * Created by xierip on 6/30/17. Web: http://xierip.pl
 */
public @Data
class CustomActionBar {

  private String message;
  private BukkitTask bukkitTask;
  private Player player;

  public CustomActionBar(String message, BukkitTask bukkitTask, Player player) {
    this.message = StringUtil.fixColors(message);
    this.bukkitTask = bukkitTask;
    this.player = player;
  }

  public void destroy() {
    if (bukkitTask != null) {
      bukkitTask.cancel();
    }
    player.sendActionBar(null);
  }
}
