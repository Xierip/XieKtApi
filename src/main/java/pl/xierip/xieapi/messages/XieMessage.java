package pl.xierip.xieapi.messages;

import java.util.Collection;
import org.bukkit.command.CommandSender;

/**
 * Created by xierip on 11.04.18. Web: http://xierip.pl
 */
public interface XieMessage {

  public void sendMessage(CommandSender commandSender, String... replace);

  public void sendBroadcast(String... replace);

  public void sendMessage(Collection<? extends CommandSender> collection, String... replace);

  public String getText();

  public String getColoredText();

  public XieMessageType getType();
}
