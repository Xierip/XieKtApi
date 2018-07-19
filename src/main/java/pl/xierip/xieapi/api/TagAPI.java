package pl.xierip.xieapi.api;

import org.bukkit.entity.Player;
import pl.xierip.xieapi.enums.LogType;
import pl.xierip.xieapi.thread.AsyncPacketThread;
import pl.xierip.xieapi.utils.PacketUtil;
import pl.xierip.xieapi.utils.StringUtil;
import pl.xierip.xieapi.XieAPI;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Xierip on 24.11.2015.
 */
public class TagAPI {
    private static boolean enabled = true;
    private static Map<UUID, Map<String, String>> tagMAP = new HashMap<>();

    public static void disable() {
        enabled = false;
        tagMAP.clear();
    }

    public static void quitPlayer(Player player) {
        tagMAP.remove(player.getUniqueId());
    }

    public static void updateTag(Player player, Player target, String tag) {
        updateTagAndSuffix(player, target, tag, "");
    }

    public static void updateTagAndSuffix(Player player, Player target, String tag, String suffix) {
        if (!enabled) {
            return;
        }
        if (player == null) {
            XieAPI.getLogging().log(LogType.ERROR, "Cannot send tag, but player is null!");
            return;
        }
        if (target == null) {
            XieAPI.getLogging().log(LogType.ERROR, "Cannot send tag, but target is null!");
            return;
        }
        if (tag == null) {
            tag = "";
        }
        if (suffix == null) {
            suffix = "";
        }
        suffix = StringUtil.fixColors(suffix.substring(0, Math.min(suffix.length(), 16)));
        tag = StringUtil.fixColors(tag.substring(0, Math.min(tag.length(), 16)));
        UUID uuid = target.getUniqueId();
        Map<String, String> map = tagMAP.get(uuid);
        String name = player.getName();
        if (map == null) {
            map = new HashMap<>();
            tagMAP.put(uuid, map);
        }
        if (!map.containsKey(name)) {
            AsyncPacketThread.addPacket(target, PacketUtil.buildTeamPacket(name, name, tag, suffix, 0, name));
            map.put(name, tag);
        } else {
            AsyncPacketThread.addPacket(target, PacketUtil.buildTeamPacket(name, name, tag, suffix, 2, name));
            map.put(name, tag);
        }
    }
}
