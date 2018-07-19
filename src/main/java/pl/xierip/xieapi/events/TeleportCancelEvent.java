package pl.xierip.xieapi.events;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import pl.xierip.xieapi.objects.DelayTeleport;

/**
 * @author Xieirp.
 */
public class TeleportCancelEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    @Getter
    private DelayTeleport delayTeleport;
    @Getter
    private Player player;

    public TeleportCancelEvent(final Player player, final DelayTeleport delayTeleport) {
        this.player = player;
        this.delayTeleport = delayTeleport;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}
