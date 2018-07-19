package pl.xierip.xieapi.utils;

import org.bukkit.enchantments.Enchantment;
import pl.xierip.xieapi.exceptions.ConfigException;
import pl.xierip.xieapi.managers.ConfigManager;
import pl.xierip.xieapi.objects.Config;
import pl.xierip.xieapi.XieAPI;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Xierip
 */
public final class EnchantmentsUtil {
    private final static Map<String, Enchantment> ENCHANTMENTS = new HashMap<>();

    public static void enable(XieAPI plugin) {
        Config config = ConfigManager.register("xieapi_enchantments", "enchantments.yml", plugin);
        for (String s : config.getSafeKeys("Xierip.XieAPI.Enchantments")) {
            try {
                ENCHANTMENTS.put(s, Enchantment.getByName(config.getString("Xierip.XieAPI.Enchantments."+ s)));
            } catch (ConfigException e) {
                e.printStackTrace();
            }
        }
        for (Enchantment enchantment : Enchantment.values()) {
            ENCHANTMENTS.put(enchantment.getName().toLowerCase(), enchantment);
        }
    }

    public static Enchantment getEnchantment(final String string) {
        final String enchantmentName = string.toLowerCase();
        if (EnchantmentsUtil.ENCHANTMENTS.get(enchantmentName) != null) {
            return EnchantmentsUtil.ENCHANTMENTS.get(enchantmentName);
        }
        return null;
    }

    public static Map<Enchantment, Integer> getEnchantments(final String string) {
        final Map<Enchantment, Integer> map = new HashMap<>();
        if (string.contains(" ")) {
            final String[] split = string.split(" ");
            for (final String s : split) {
                final String[] split1 = s.split(":");
                map.put(getEnchantment(split1[0]), Integer.valueOf(split1[1]));
            }
        } else {
            final String[] split = string.split(":");
            map.put(getEnchantment(split[0]), Integer.valueOf(split[1]));
        }
        return map;
    }
}
