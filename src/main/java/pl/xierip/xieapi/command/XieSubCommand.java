package pl.xierip.xieapi.command;


import org.bukkit.command.CommandSender;

/**
 * @author Xierip
 */
public interface XieSubCommand {

  void onSubCommand(CommandSender commandSender, String[] args);
}
