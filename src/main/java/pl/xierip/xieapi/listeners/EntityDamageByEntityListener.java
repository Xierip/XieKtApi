package pl.xierip.xieapi.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import pl.xierip.xieapi.events.PlayerDamageByPlayerEvent;
import pl.xierip.xieapi.events.PlayerDamageByPlayerEvent.AttackType;
import pl.xierip.xieapi.teleport.DelayTeleportManager;

/**
 * @author Xierip
 */
public class EntityDamageByEntityListener implements Listener {

  @EventHandler(priority = EventPriority.HIGHEST)
  private void onDMG(final EntityDamageByEntityEvent event) {
    if (event.isCancelled()) {
      return;
    }
    if (event.getEntity() instanceof Player) {
      final Player p = (Player) event.getEntity();
      if (DelayTeleportManager.getTeleports().containsKey(p)) {
        DelayTeleportManager.getTeleports().get(p).cancel();
      }
      Player damager = null;
      AttackType attackType = AttackType.NORMAL;
      Projectile projectile = null;
      if (event.getDamager() instanceof Player) {
        damager = (Player) event.getDamager();
        ((Player) event.getEntity()).setSprinting(false);
      }
      if (damager == null) {
        if (event.getDamager() instanceof Projectile) {
          if (((Projectile) event.getDamager()).getShooter() instanceof Player) {
            damager = (Player) ((Projectile) event.getDamager()).getShooter();
            if (event.getDamager() instanceof EnderPearl) {
              if (damager.getUniqueId().equals(event.getEntity().getUniqueId())) {
                return;
              }
            }
            projectile = (Projectile) event.getDamager();
            attackType = event.getDamager() instanceof Arrow ? AttackType.ARROW
                : AttackType.OTHER_PROJECTILE;
          } else {
            return;
          }
        } else {
          return;
        }
      }
      if (damager != null) {
        final PlayerDamageByPlayerEvent playerDamageByPlayerEvent = new PlayerDamageByPlayerEvent(p,
            damager, event.getDamage(), event.isCancelled(), attackType, projectile);
        Bukkit.getPluginManager().callEvent(playerDamageByPlayerEvent);
        if (playerDamageByPlayerEvent.isCancelled()) {
          event.setCancelled(true);
        } else {
          event.setDamage(playerDamageByPlayerEvent.getDamage());
        }
      }
    }
  }
}
