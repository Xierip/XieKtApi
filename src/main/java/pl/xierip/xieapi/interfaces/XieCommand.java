package pl.xierip.xieapi.interfaces;


import org.bukkit.command.CommandSender;

/**
 * @author Xierip
 */
public interface XieCommand extends XieCommandStructure {
    void onCommand(CommandSender commandSender, String[] args);
}
