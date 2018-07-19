package pl.xierip.xieapi.objects.messages;

import com.destroystokyo.paper.Title;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.xierip.xieapi.interfaces.XieMessage;
import pl.xierip.xieapi.interfaces.XieMessageType;
import pl.xierip.xieapi.utils.StringUtil;

import java.util.Collection;

/**
 * Created by xierip on 11.04.18.
 * Web: http://xierip.pl
 */
public class XieMessageTitle implements XieMessage {

    @Getter
    private String titleString, subTitleString;
    @Getter
    private int in, stay, out;

    public XieMessageTitle(String titleString, String subTitleString, int in, int stay, int out) {
        this.titleString = titleString;
        this.subTitleString = subTitleString;
        this.in = in;
        this.stay = stay;
        this.out = out;
    }

    @Override
    public void sendMessage(CommandSender commandSender, String... replace) {
        if (commandSender instanceof Player) {
            Title title = new Title(StringUtil.replace(StringUtil.fixColors(titleString), replace), StringUtil.replace(StringUtil.fixColors(subTitleString), replace), in, stay, out);
            ((Player) commandSender).sendTitle(title);
        } else {
            commandSender.sendMessage(StringUtil.replace(StringUtil.fixColors(getText()), replace));
        }
    }

    @Override
    public void sendBroadcast(String... replace) {
        Title title = new Title(StringUtil.replace(StringUtil.fixColors(titleString), replace), StringUtil.replace(StringUtil.fixColors(subTitleString), replace), in, stay, out);
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendTitle(title);
        }
        System.out.print(getText());
    }

    @Override
    public void sendMessage(Collection<? extends CommandSender> collection, String... replace) {
        Title title = new Title(StringUtil.replace(StringUtil.fixColors(titleString), replace), StringUtil.replace(StringUtil.fixColors(subTitleString), replace), in, stay, out);
        for (CommandSender commandSender : collection) {
            if (commandSender instanceof Player)
                ((Player) commandSender).sendTitle(title);
            else
                commandSender.sendMessage(StringUtil.replace(StringUtil.fixColors(getText()), replace));
        }
    }

    @Override
    public String getText() {
        return titleString + " " + subTitleString;
    }

    @Override
    public String getColoredText() {
        return StringUtil.fixColors(this.getText());
    }

    @Override
    public XieMessageType getType() {
        return XieMessageType.TITLE;
    }
}
