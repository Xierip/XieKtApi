package pl.xierip.xieapi.utils;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import pl.xierip.xieapi.objects.ParticleData;

import java.util.Collection;

/**
 * Created by xierip on 12.07.18.
 * Web: http://xierip.pl
 */
public class EffectsUtil {
    public static void effectTrippleCircle(Location location, int range, ParticleData particlePacket1, ParticleData particlePacket2, ParticleData particlePacket3, JavaPlugin plugin) {
        new BukkitRunnable() {
            @Override
            public void run() {
                final Collection<Entity> entities = location.getWorld().getNearbyEntities(location, range, range, range);
                for (Entity entity : entities) {
                    if (!(entity instanceof Player)) continue;
                    Player near = (Player) entity;
                    for (int i = 0; i < 360; i += 5) {
                        final Location circle = location.clone();
                        circle.setY(circle.getY());
                        circle.setZ(circle.getZ() + Math.cos(i) * 0.5);
                        circle.setX(circle.getX() + Math.sin(i) * 0.5);
                        final Location circle2 = circle.clone();
                        circle2.setY(circle2.getY() + 1.0);
                        circle2.setX(circle.getX());
                        circle2.setZ(circle.getZ());
                        final Location circle3 = circle.clone();
                        circle3.setY(circle3.getY() + 2.0);
                        circle3.setX(circle.getX());
                        circle3.setZ(circle.getZ());
                        ((CraftPlayer) near).getHandle().playerConnection.networkManager.sendPacket(particlePacket1.toPacket((float) circle.getX(), (float) circle.getY(), (float) circle.getZ()));
                        ((CraftPlayer) near).getHandle().playerConnection.networkManager.sendPacket(particlePacket2.toPacket((float) circle2.getX(), (float) circle2.getY(), (float) circle2.getZ()));
                        ((CraftPlayer) near).getHandle().playerConnection.networkManager.sendPacket(particlePacket3.toPacket((float) circle3.getX(), (float) circle3.getY(), (float) circle3.getZ()));

                    }
                }
            }
        }.runTaskAsynchronously(plugin);
    }
}
