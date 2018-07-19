package pl.xierip.xieapi.utils;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Xierip on 2016-06-25.
 * DarkElite.pl ©
 */
public class PlayerUtil {

    public static List<Player> getPlayersInRadius(final Location location, final int size) {
        return location.getWorld().getPlayers().stream().filter(p -> location.distance(p.getLocation()) <= size).collect(Collectors.toList());
    }

    public static void kickAll() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.kickPlayer(StringUtil.fixColors("&c&lRestart!\n&aWroc do nas!"));
        });
    }

    public static void prepareClearPlayer(final Player player) {
        player.setGameMode(GameMode.SURVIVAL);
        for (final PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
        player.setFireTicks(0);
    }

    public static boolean hasSpace(final Player player) {
        for (final ItemStack i : player.getInventory().getStorageContents()) {
            if (i == null || i.getType().equals(Material.AIR)) {
                return true;
            }
        }
        return false;
    }
}
