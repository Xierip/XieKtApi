package pl.xierip.xieapi.managers;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.xierip.xieapi.objects.Config;
import pl.xierip.xieapi.objects.EditingInventory;
import pl.xierip.xieapi.objects.XieCallback;
import pl.xierip.xieapi.utils.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Xierip on 17.08.2015.
 */
public class InventoryManager {
    @Getter
    private static Map<UUID, EditingInventory> inventoryMap = new HashMap<>();

    public static boolean closeInventory(final UUID uuid) {
        if (InventoryManager.getInventoryMap().containsKey(uuid)) {
            InventoryManager.getInventoryMap().remove(uuid).update();
            return true;
        }
        return false;
    }

    public static void updateList(final Player player, final List<ItemStack> list, final String path, final Config config, final Inventory inventoryUpdate) {
        inventoryMap.put(player.getUniqueId(), new EditingInventory(player, Bukkit.createInventory(null, 54, StringUtil.fixColors("&4&lEdytowanie")), list, path, config, inventoryUpdate));

    }

    public static void updateList(final Player player, final List<ItemStack> list, final String path, final Config config, final Inventory inventoryUpdate, final XieCallback callback) {
        inventoryMap.put(player.getUniqueId(), new EditingInventory(player, Bukkit.createInventory(null, 54, StringUtil.fixColors("&4&lEdytowanie")), list, path, config, inventoryUpdate, callback));

    }

    public static void updateList(final Player player, final List<ItemStack> list, final String path, final Config config) {
        inventoryMap.put(player.getUniqueId(), new EditingInventory(player, Bukkit.createInventory(null, 54, StringUtil.fixColors("&4&lEdytowanie")), list, path, config));
    }

}
