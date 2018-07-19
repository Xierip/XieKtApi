package pl.xierip.xieapi.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import pl.xierip.xieapi.events.PlayerWalkEvent;
import pl.xierip.xieapi.managers.DelayTeleportManager;

/**
 * @author Xierip
 */
public class PlayerMoveListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    private void onEvent(final PlayerMoveEvent e) {
        if (e.isCancelled()) return;
        final Player player = e.getPlayer();
        final Location from = e.getFrom().clone();
        final Location to = e.getTo().clone();
        if (DelayTeleportManager.getTeleports().containsKey(player)) {
            if (from.getBlockX() != to.getBlockX() || from.getBlockY() != to.getBlockY() || from.getBlockZ() != to.getBlockZ()) {
                DelayTeleportManager.getTeleports().get(player).cancel();
            }

        }
        if (from.getBlockX() != to.getBlockX() || from.getBlockZ() != to.getBlockZ()) {
            final PlayerWalkEvent playerWalkEvent = new PlayerWalkEvent(player, from, to);
            Bukkit.getPluginManager().callEvent(playerWalkEvent);
            if (playerWalkEvent.isCancelled()) {
                e.setCancelled(true);
            }
        }
    }
}
