package pl.xierip.xieapi.command;


import org.bukkit.entity.Player;

/**
 * @author Xierip
 */
public interface XieSubPlayerCommand {

  void onSubCommand(Player player, String[] args);
}
