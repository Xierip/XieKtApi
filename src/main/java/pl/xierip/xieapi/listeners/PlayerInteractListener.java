package pl.xierip.xieapi.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Button;
import pl.xierip.xieapi.events.ButtonClickEvent;

/**
 * @author Xierip
 */
public class PlayerInteractListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    private void onInteract(final PlayerInteractEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if ((event.getAction() != Action.RIGHT_CLICK_BLOCK) && (event.getAction() != Action.LEFT_CLICK_BLOCK)) {
            return;
        }
        if ((event.getClickedBlock().getType() != Material.STONE_BUTTON) && (event.getClickedBlock().getType() != Material.WOOD_BUTTON)) {
            return;
        }
        final BlockState state = event.getClickedBlock().getState();
        if ((state.getData() instanceof Button)) {
            final Button button = (Button) state.getData();
            if (button.isPowered()) {
                return;
            }
        }
        Bukkit.getPluginManager().callEvent(new ButtonClickEvent(event.getPlayer(), event.getClickedBlock().getRelative(((Button) event.getClickedBlock().getState().getData()).getAttachedFace()), (Button) event.getClickedBlock().getState().getData()));
    }
}
