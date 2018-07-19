package pl.xierip.xieapi.managers;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Xierip on 23.02.2016.
 */
public class CustomItemsManager {
    @Getter
    private static Map<String, ItemStack> items = new HashMap<>();

    public static void addCustomItem(final String name, final ItemStack item) {
        items.put(name, item);
    }

    public static ItemStack getCustomItem(final String name) {
        if (!items.containsKey(name)) {
            return null;
        }
        return items.get(name).clone();
    }

    public static void removeCustomItem(final String name) {
        if (!items.containsKey(name)) {
            return;
        }
        items.remove(name);
    }
}
