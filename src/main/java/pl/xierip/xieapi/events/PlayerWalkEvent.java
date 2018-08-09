package pl.xierip.xieapi.events;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by xierip on 04.03.17. Web: http://xierip.pl
 */
public class PlayerWalkEvent extends Event implements Cancellable {

  private static final HandlerList handlers = new HandlerList();
  private boolean canceled;
  @Getter
  private Location fromLocation;
  @Getter
  private Player player;
  @Getter
  private Location toLocation;

  public PlayerWalkEvent(final Player player, final Location fromLocation,
      final Location toLocation) {
    this.player = player;
    this.fromLocation = fromLocation;
    this.toLocation = toLocation;
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
    return this.canceled;
  }

  @Override
  public void setCancelled(final boolean b) {
    this.canceled = b;
  }
}
