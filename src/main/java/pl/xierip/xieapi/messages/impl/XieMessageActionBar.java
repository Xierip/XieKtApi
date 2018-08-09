package pl.xierip.xieapi.messages.impl;

import java.util.Collection;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.xierip.xieapi.actionbar.ActionBarAPI;
import pl.xierip.xieapi.messages.XieMessage;
import pl.xierip.xieapi.messages.XieMessageType;
import pl.xierip.xieapi.utils.StringUtil;

/**
 * Created by xierip on 11.04.18. Web: http://xierip.pl
 */
public class XieMessageActionBar implements XieMessage {

  @Getter
  private String text;
  @Getter
  private int stay;

  public XieMessageActionBar(String text, int stay) {
    this.text = text;
    this.stay = stay;
  }

  @Override
  public void sendMessage(CommandSender commandSender, String... replace) {
    if (commandSender instanceof Player) {
      ActionBarAPI.setMessage((Player) commandSender,
          StringUtil.replace(StringUtil.fixColors(text), replace), stay);
    } else {
      commandSender.sendMessage(StringUtil.replace(StringUtil.fixColors(text), replace));
    }
  }

  @Override
  public void sendBroadcast(String... replace) {
    String message = StringUtil.replace(StringUtil.fixColors(text), replace);
    for (Player player : Bukkit.getOnlinePlayers()) {
      ActionBarAPI.setMessage(player, message, stay);
    }
    System.out.print(message);
  }

  @Override
  public void sendMessage(Collection<? extends CommandSender> collection, String... replace) {
    String message = StringUtil.replace(StringUtil.fixColors(text), replace);
    for (CommandSender commandSender : collection) {
      if (commandSender instanceof Player) {
        ActionBarAPI.setMessage((Player) commandSender, message, stay);
      } else {
        System.out.print(message);
      }
    }
  }

  @Override
  public String getColoredText() {
    return StringUtil.fixColors(this.text);
  }

  @Override
  public XieMessageType getType() {
    return XieMessageType.ACTIONBAR;
  }
}
