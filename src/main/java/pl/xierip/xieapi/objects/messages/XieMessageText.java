package pl.xierip.xieapi.objects.messages;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import pl.xierip.xieapi.interfaces.XieMessage;
import pl.xierip.xieapi.interfaces.XieMessageType;
import pl.xierip.xieapi.utils.StringUtil;

import java.util.Collection;

/**
 * Created by xierip on 11.04.18.
 * Web: http://xierip.pl
 */
public class XieMessageText implements XieMessage {

    @Getter
    private final String text;

    public XieMessageText(String text) {
        this.text = text;
    }

    @Override
    public void sendMessage(CommandSender commandSender, String... replace) {
        commandSender.sendMessage(StringUtil.replace(StringUtil.fixColors(text), replace));
    }

    @Override
    public void sendBroadcast(String... replace) {
        Bukkit.broadcastMessage(StringUtil.replace(StringUtil.fixColors(text), replace));
    }

    @Override
    public void sendMessage(Collection<? extends CommandSender> collection, String... replace) {
        String message = StringUtil.replace(StringUtil.fixColors(text), replace);
        for (CommandSender commandSender : collection) {
            commandSender.sendMessage(message);
        }
    }

    @Override
    public XieMessageType getType() {
        return XieMessageType.TEXT;
    }

    @Override
    public String getColoredText() {
        return StringUtil.fixColors(this.text);
    }
}
