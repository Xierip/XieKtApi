package pl.xierip.xieapi.managers;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.xierip.xieapi.interfaces.XieMessage;
import pl.xierip.xieapi.objects.DelayTeleport;
import pl.xierip.xieapi.utils.SendUtil;

import java.util.HashMap;

/**
 * Created by xierip on 12.05.18.
 * Web: http://xierip.pl
 */
public class DelayTeleportManager {
    @Getter
    private static HashMap<Player, DelayTeleport> teleports = new HashMap<>();
    @Getter
    private static HashMap<Player, DelayTeleport> teleportsToPlayers = new HashMap<>();

    public static boolean teleport(final Player player, final Location target, final int time, final XieMessage before, final XieMessage after) {
        if (teleports.containsKey(player)) {
            SendUtil.sendMessage(player, "&cPosiadasz oczekujaca teleportacje!");
            return false;
        }
        teleports.put(player, new DelayTeleport(player, target, time, before, after));
        return true;
    }

    public static boolean teleport(final Player player, final Player target, final int time, final XieMessage before, final XieMessage after) {
        if (teleports.containsKey(player)) {
            SendUtil.sendMessage(player, "&cPosiadasz oczekujaca teleportacje!");
            return false;
        }
        if (teleportsToPlayers.containsKey(player)) {
            SendUtil.sendMessage(player, "&cWybrany gracz posiada oczekujaca teleportacje!");
            return false;
        }
        DelayTeleport delayTeleport = new DelayTeleport(player, target, time, before, after);
        teleports.put(player, delayTeleport);
        teleportsToPlayers.put(target, delayTeleport);
        return true;
    }
}
