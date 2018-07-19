package pl.xierip.xieapi.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.xierip.xieapi.XieAPI;

import java.util.Collection;
import java.util.List;

/**
 * Created by Xierip on 2016-06-25.
 * DarkElite.pl ©
 */
public class SendUtil {
    public static void broadcastPermission(String message, String permission) {
        message = StringUtil.fixColors(message);
        for (final Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission(permission))
                player.sendMessage(message);
        }
    }

    public static void clearChat() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (final Player player1 : Bukkit.getOnlinePlayers()) {
                    for (int i = 0; i < 150; i++) {
                        SendUtil.sendMessage(player1, " &k");
                    }
                }
            }
        }.runTaskAsynchronously(XieAPI.getInstance());
    }

    public static void sendBroadcast(final List<String> strings) {
        strings.forEach(SendUtil::sendBroadcast);
    }

    public static void sendBroadcast(final String message, String... replace) {
        if (!message.equalsIgnoreCase("off"))
            Bukkit.broadcastMessage(StringUtil.fixColors(StringUtil.replace(message, replace)));
    }

    public static void sendMessage(final CommandSender s, final List<String> message) {
        message.forEach(string -> sendMessage(s, string));
    }

    public static void sendMessage(final CommandSender s, final String message, String... replace) {
        if (!message.equalsIgnoreCase("off")) {
            s.sendMessage(StringUtil.fixColors(StringUtil.replace(message, replace)));
        }
    }

    public static void sendMessage(final Collection<Player> commandSenders, final String message, String... replace) {
        if (!message.equalsIgnoreCase("off")) {
            String mes = StringUtil.fixColors(StringUtil.replace(message, replace));
            for (CommandSender commandsender : commandSenders) {
                sendMessage(commandsender, mes);
            }
        }
    }

    public static void sendMessageArray(final CommandSender s, final String... strings) {
        for (final String string : strings) {
            sendMessage(s, string);
        }
    }
}
