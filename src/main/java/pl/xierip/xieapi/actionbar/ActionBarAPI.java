package pl.xierip.xieapi.actionbar;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.xierip.xieapi.XieAPI;
import pl.xierip.xieapi.utils.StringUtil;

/**
 * Created by xierip on 6/30/17. Web: http://xierip.pl
 */
public class ActionBarAPI {


  private static Map<UUID, CustomActionBar> actionBarMap = new TreeMap<>();

  public static void removeBar(Player player) {
    if (actionBarMap.containsKey(player.getUniqueId())) {
      CustomActionBar customActionBar = actionBarMap.remove(player.getUniqueId());
      if (customActionBar != null) {
        customActionBar.destroy();
      }
    }
  }

  public static void setMessage(Player player, String message, int time) {
    removeBar(player);
    String messageColored = StringUtil.fixColors(message);
    player.sendActionBar(messageColored);
    if (time < 2) {
      return;
    }
    final int[] currentTime = {time};
    actionBarMap
        .put(player.getUniqueId(), new CustomActionBar(messageColored, new BukkitRunnable() {
              @Override
              public void run() {
                player.sendActionBar(messageColored);
                currentTime[0]--;
                if (currentTime[0] <= 0) {
                  CustomActionBar remove = actionBarMap.remove(player.getUniqueId());
                  if (remove != null) {
                    remove.destroy();
                  }
                }
              }
            }.runTaskTimer(XieAPI.getInstance(), 20, 20), player)
        );

  }
}
