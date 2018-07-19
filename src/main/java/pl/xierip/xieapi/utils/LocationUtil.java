package pl.xierip.xieapi.utils;

import org.bukkit.Location;
import org.bukkit.Material;

/**
 * Created by Xierip on 2016-06-25.
 * DarkElite.pl ©
 */
public class LocationUtil {

    public static String coloredLocation(final Location location) {
        return "&7X: &9" + location.getBlockX() + " &7Y: &9" + location.getBlockY() + " &7Z: &9" + location.getBlockZ();
    }

    public static Location getSafeLocation(Location location) {
        if (location.getBlockY() <= 0) {
            return location.getWorld().getHighestBlockAt(location).getLocation().add(0, 1, 0);
        }
        if (!location.clone().add(0, -1, 0).getBlock().getType().equals(Material.AIR)) {
            return location;
        }
        while (location.getBlock().getType().equals(Material.AIR)) {
            location = location.add(0, -1, 0);
            if (location.getBlockY() <= 0) {
                return location.getWorld().getHighestBlockAt(location).getLocation().add(0, 1, 0);
            }
        }
        while (!location.getBlock().getType().equals(Material.AIR)) {
            location.add(0, 1, 0);
        }
        return location.add(0, 1, 0);
    }

    public static String locationToString(final Location location) {
        return "X: " + location.getBlockX() + " Y: " + location.getBlockY() + " Z: " + location.getBlockZ() + " World: " + location.getWorld().getName();
    }
}
