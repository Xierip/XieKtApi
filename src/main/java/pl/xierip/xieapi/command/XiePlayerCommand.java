package pl.xierip.xieapi.command;


import org.bukkit.entity.Player;

/**
 * @author Xierip
 */
public interface XiePlayerCommand extends XieCommandStructure {

  void onCommand(Player player, String[] args);
}
