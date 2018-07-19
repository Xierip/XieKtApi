package pl.xierip.xieapi.events;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import pl.xierip.xieapi.enums.AttackType;

/**
 * Created by xierip on 26.02.17.
 * Web: http://xierip.pl
 */
public class PlayerDamageByPlayerEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    @Getter
    private AttackType attackType;
    @Getter
    private Projectile projectile;
    private boolean cancelled;
    @Getter
    @Setter
    private double damage;
    @Getter
    private Player player, damager;

    public PlayerDamageByPlayerEvent(final Player player, final Player damager, final double damage, boolean cancelled, AttackType attackType, Projectile projectile) {
        this.player = player;
        this.damager = damager;
        this.damage = damage;
        this.cancelled = cancelled;
        this.attackType = attackType;
        this.projectile = projectile;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(final boolean b) {
        this.cancelled = b;
    }
}
