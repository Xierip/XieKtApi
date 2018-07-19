package pl.xierip.xieapi.objects.messages;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.xierip.xieapi.api.BossBarAPI;
import pl.xierip.xieapi.interfaces.XieMessage;
import pl.xierip.xieapi.interfaces.XieMessageType;
import pl.xierip.xieapi.utils.StringUtil;

import java.util.Collection;

/**
 * Created by xierip on 11.04.18.
 * Web: http://xierip.pl
 */
public class XieMessageBossBar implements XieMessage {

    @Getter
    private String text;
    @Getter
    private int stay;
    @Getter
    private BarStyle barStyle;
    @Getter
    private BarColor barColor;

    public XieMessageBossBar(String text, int stay, BarStyle barStyle, BarColor barColor) {
        this.text = text;
        this.stay = stay;
        this.barStyle = barStyle;
        this.barColor = barColor;
    }

    @Override
    public void sendMessage(CommandSender commandSender, String... replace) {
        if (commandSender instanceof Player)
            BossBarAPI.setMessage((Player) commandSender, barColor, barStyle, StringUtil.replace(StringUtil.fixColors(text), replace), stay);
        else
            commandSender.sendMessage(StringUtil.replace(StringUtil.fixColors(text), replace));
    }

    @Override
    public void sendBroadcast(String... replace) {
        String message = StringUtil.replace(StringUtil.fixColors(text), replace);
        for (Player player : Bukkit.getOnlinePlayers()) {
            BossBarAPI.setMessage(player, barColor, barStyle, message, stay);
        }
        System.out.print(message);
    }

    @Override
    public void sendMessage(Collection<? extends CommandSender> collection, String... replace) {
        String message = StringUtil.replace(StringUtil.fixColors(text), replace);
        for (CommandSender commandSender : collection) {
            if (commandSender instanceof Player)
                BossBarAPI.setMessage((Player) collection, barColor, barStyle, message, stay);
            else
                System.out.print(message);
        }
    }

    @Override
    public String getColoredText() {
        return StringUtil.fixColors(this.text);
    }

    @Override
    public XieMessageType getType() {
        return XieMessageType.BOSSBAR;
    }
}
