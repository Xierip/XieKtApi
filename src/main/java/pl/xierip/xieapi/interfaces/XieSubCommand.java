package pl.xierip.xieapi.interfaces;


import org.bukkit.command.CommandSender;

/**
 * @author Xierip
 */
public interface XieSubCommand {
    void onSubCommand(CommandSender commandSender, String[] args);
}
