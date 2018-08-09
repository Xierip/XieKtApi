package pl.xierip.xieapi.teleport;

/**
 * @author Xierip
 */

import java.util.concurrent.atomic.AtomicInteger;
import lombok.Getter;
import net.minecraft.server.v1_12_R1.EnumParticle;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.xierip.xieapi.XieAPI;
import pl.xierip.xieapi.events.TeleportCancelEvent;
import pl.xierip.xieapi.messages.XieMessage;
import pl.xierip.xieapi.particles.ParticleData;
import pl.xierip.xieapi.particles.ParticlesUtil;
import pl.xierip.xieapi.utils.SendUtil;
import pl.xierip.xieapi.utils.StringUtil;

public class DelayTeleport {

  @Getter
  private Player player, to;
  @Getter
  private int task;

  public DelayTeleport(final Player p, final Location to, final int time, final XieMessage before,
      final XieMessage after) {
    this.player = p;
    this.to = null;
    AtomicInteger counter = new AtomicInteger();
    this.player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, time * 40, 0));
    before.sendMessage(p);
    this.task = Bukkit.getServer().getScheduler()
        .scheduleSyncRepeatingTask(XieAPI.getInstance(), () -> {
          final int i = counter.get();
          ParticlesUtil.effectTrippleCircle(this.player.getLocation(), 30,
              new ParticleData(EnumParticle.REDSTONE, false, 1, 1, 1, 1, 0),
              new ParticleData(EnumParticle.REDSTONE, false, 1, 1, 1, 1, 0),
              new ParticleData(EnumParticle.REDSTONE, false, 1, 1, 1, 1, 0), XieAPI.getInstance());
          this.player.sendActionBar(StringUtil.fixColors(
              "&a" + StringUtils.repeat('\u2588', i) + "&c" + StringUtils
                  .repeat('\u2588', time - i)));
          if (i == time) {
            Bukkit.getServer().getScheduler().cancelTask(this.task);
            after.sendMessage(p);
            DelayTeleportManager.getTeleports().remove(p);
            p.teleport(to);
            this.player.removePotionEffect(PotionEffectType.BLINDNESS);
            player.sendActionBar(StringUtil.fixColors("&c"));
            return;
          }
          counter.incrementAndGet();
        }, 0, 20);
  }

  public DelayTeleport(final Player p, final Player to, final int time, final XieMessage before,
      final XieMessage after) {
    this.player = p;
    this.to = to;
    AtomicInteger counter = new AtomicInteger();
    this.player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, time * 40, 0));
    before.sendMessage(p);
    this.task = Bukkit.getServer().getScheduler()
        .scheduleSyncRepeatingTask(XieAPI.getInstance(), () -> {
          final int i = counter.get();
          ParticlesUtil.effectTrippleCircle(this.player.getLocation(), 30,
              new ParticleData(EnumParticle.REDSTONE, false, 1, 1, 1, 1, 0),
              new ParticleData(EnumParticle.REDSTONE, false, 1, 1, 1, 1, 0),
              new ParticleData(EnumParticle.REDSTONE, false, 1, 1, 1, 1, 0), XieAPI.getInstance());
          this.player.sendActionBar(StringUtil.fixColors(
              "&a" + StringUtils.repeat('\u2588', i) + "&c" + StringUtils
                  .repeat('\u2588', time - i)));
          if (i == time) {
            Bukkit.getServer().getScheduler().cancelTask(this.task);
            DelayTeleportManager.getTeleports().remove(p);
            DelayTeleportManager.getTeleportsToPlayers().remove(to);
            p.teleport(to);
            this.player.removePotionEffect(PotionEffectType.BLINDNESS);
            player.sendActionBar(StringUtil.fixColors("&c"));
            return;
          }
          counter.incrementAndGet();

        }, 0, 20);

  }


  public void cancel() {
    Bukkit.getServer().getScheduler().cancelTask(this.task);
    DelayTeleportManager.getTeleports().remove(this.player);
    if (DelayTeleportManager.getTeleportsToPlayers().containsValue(this)) {
      DelayTeleportManager.getTeleportsToPlayers().remove(this.to);
    }
    this.player.removePotionEffect(PotionEffectType.BLINDNESS);
    this.player.sendActionBar(StringUtil.fixColors("&c"));
    SendUtil.sendMessage(this.player, "&cTeleportacja zostala anulowana!");
    Bukkit.getPluginManager().callEvent(new TeleportCancelEvent(this.player, this));
  }

}

