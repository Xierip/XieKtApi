package pl.xierip.xieapi.command;


import org.bukkit.command.CommandSender;

/**
 * @author Xierip
 */
public interface XieSubObjectCommand {

  void onSubCommand(CommandSender commandSender, String[] args, Object... objects);
}
