package pl.xierip.xieapi.command;


import org.bukkit.command.CommandSender;

/**
 * @author Xierip
 */
public interface XieCommand extends XieCommandStructure {

  void onCommand(CommandSender commandSender, String[] args);
}
