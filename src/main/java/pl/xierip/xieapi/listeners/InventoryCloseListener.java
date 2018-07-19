package pl.xierip.xieapi.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import pl.xierip.xieapi.managers.InventoryManager;
import pl.xierip.xieapi.utils.SendUtil;
import pl.xierip.xieapi.utils.StringUtil;

/**
 * @author Xierip on 16.08.2015.
 */
public class InventoryCloseListener implements Listener {
    @EventHandler
    private void onEvent(final InventoryCloseEvent event) {
        if (event.getInventory().getTitle() == null) {
            return;
        }
        if (event.getInventory().getTitle().equals(StringUtil.fixColors("&4&lEdytowanie"))) {
            if (InventoryManager.closeInventory(event.getPlayer().getUniqueId())) {
                SendUtil.sendMessage((Player) event.getPlayer(), "&7Lista przedmiotow zostala uaktualniona.");
                return;
            }
        }
    }
}
