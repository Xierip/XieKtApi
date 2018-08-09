package pl.xierip.xieapi.events;

import lombok.Getter;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.material.Button;


/**
 * @author Xierip
 */
public class ButtonClickEvent extends Event {

  private static final HandlerList handlers = new HandlerList();
  @Getter
  private Block blockBehind;
  @Getter
  private Button button;
  @Getter
  private Player player;

  public ButtonClickEvent(Player player, Block blockBehind, Button button) {
    this.player = player;
    this.blockBehind = blockBehind;
    this.button = button;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }
}
