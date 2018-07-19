package pl.xierip.xieapi.interfaces;


import org.bukkit.entity.Player;

/**
 * @author Xierip
 */
public interface XiePlayerCommand extends XieCommandStructure {
    void onCommand(Player player, String[] args);
}
