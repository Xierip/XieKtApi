package pl.xierip.xieapi.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.xierip.xieapi.bossbar.BossBarAPI;
import pl.xierip.xieapi.teleport.DelayTeleportManager;
import pl.xierip.xieapi.tag.TagAPI;

/**
 * @author Xieirp.
 */
public class PlayerLeftListener implements Listener {

  @EventHandler(priority = EventPriority.MONITOR)
  private void onLeft(final PlayerQuitEvent event) {
    Player player = event.getPlayer();
    if (DelayTeleportManager.getTeleportsToPlayers().containsKey(player)) {
      DelayTeleportManager.getTeleportsToPlayers().remove(player).cancel();
    }
    if (DelayTeleportManager.getTeleports().containsKey(player)) {
      DelayTeleportManager.getTeleports().remove(player).cancel();
    }
    TagAPI.quitPlayer(player);
    BossBarAPI.removeBar(player);
  }
}
