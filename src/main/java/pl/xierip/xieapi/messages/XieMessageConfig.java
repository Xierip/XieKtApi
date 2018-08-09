package pl.xierip.xieapi.messages;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import pl.xierip.xieapi.config.Config;
import pl.xierip.xieapi.config.exception.ConfigException;
import pl.xierip.xieapi.logging.Logging.LogType;
import pl.xierip.xieapi.logging.Logging;
import pl.xierip.xieapi.messages.impl.XieMessageActionBar;
import pl.xierip.xieapi.messages.impl.XieMessageBossBar;
import pl.xierip.xieapi.messages.impl.XieMessageText;
import pl.xierip.xieapi.messages.impl.XieMessageTitle;

/**
 * Created by xierip on 11.04.18. Web: http://xierip.pl
 */
public interface XieMessageConfig extends XieMessage {

  public static <E extends Enum<E> & XieMessageConfig> void loadAndSortConfig(Class<E> eClass,
      String path, Config config, Logging logging) {
    boolean exception = false;
    for (E message : eClass.getEnumConstants()) {
      save(path + "." + message.name(), config, message.getMessage(), false);
      try {
        message.setMessage(parseMessage(path + "." + message.name(), config));
      } catch (ConfigException e) {
        logging.log(LogType.INFO, "[CONFIG] Error with loading message, using default...");
        logging.log(LogType.INFO, "[CONFIG] " + e.getMessage());
        exception = true;
      }
    }
    if (exception) {
      try {
        String newName =
            "broken_" + config.getFile().getName() + "_" + System.currentTimeMillis() + ".yml";
        FileUtils.copyFile(config.getFile(), new File(config.getFile().getParentFile(), newName));
        logging.log(LogType.INFO,
            "[CONFIG] Making copy of: " + config.getFile().getName() + ", new name: " + newName
                + "");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    List<E> messages = new LinkedList<>();
    Collections.addAll(messages, eClass.getEnumConstants());
    messages.sort((xm1, xm2) -> xm1.name().compareToIgnoreCase(xm2.name()));
    config.set(path, null);
    for (E message : messages) {
      save(path + "." + message.name(), config, message.getMessage(), true);
    }
    config.save();
  }

  public static void save(String path, Config config, XieMessage message, boolean force) {
    if (!force && config.isSet(path)) {
      return;
    }
    config.set(path, null);
    switch (message.getType()) {
      case TEXT:
        if (message.getText().contains("\n")) {
          config.set(path + ".text", Arrays.asList(message.getText().split("\n")));
        } else {
          config.set(path + ".text", message.getText());
        }
        return;
      case BOSSBAR:
        XieMessageBossBar messageBossBar = (XieMessageBossBar) message;
        config.set(path + ".type", message.getType().name());
        config.set(path + ".text", messageBossBar.getText());
        config.set(path + ".stay", messageBossBar.getStay());
        config.set(path + ".color", messageBossBar.getBarColor().name());
        config.set(path + ".style", messageBossBar.getBarStyle().name());
        break;
      case ACTIONBAR:
        XieMessageActionBar messageActionBar = (XieMessageActionBar) message;
        config.set(path + ".type", message.getType().name());
        config.set(path + ".text", messageActionBar.getText());
        config.set(path + ".stay", messageActionBar.getStay());
        break;
      case TITLE:
        XieMessageTitle messageTitle = (XieMessageTitle) message;
        config.set(path + ".type", message.getType().name());
        config.set(path + ".title", messageTitle.getTitleString());
        config.set(path + ".subtitle", messageTitle.getSubTitleString());
        config.set(path + ".in", messageTitle.getIn());
        config.set(path + ".stay", messageTitle.getStay());
        config.set(path + ".out", messageTitle.getOut());
        break;
    }
  }

  public static XieMessage parseMessage(String path, Config config) throws ConfigException {
    try {
      XieMessageType xieMessageType;
      if (!config.isSet(path + ".type") && config.isSet(path + ".text")) {
        xieMessageType = XieMessageType.TEXT;
      } else {
        xieMessageType = XieMessageType.valueOf(config.getString(path + ".type").toUpperCase());
      }
      switch (xieMessageType) {
        case TEXT:
          return new XieMessageText(
              config.isInstanceOf(path + ".text", String.class) ? config.getString(path + ".text")
                  : StringUtils.join(config.getStringList(path + ".text"), "\n"));
        case BOSSBAR:
          return new XieMessageBossBar(config.getString(path + ".text"),
              config.getInt(path + ".stay"),
              BarStyle.valueOf(config.getString(path + ".style").toUpperCase()),
              BarColor.valueOf(config.getString(path + ".color").toUpperCase()));
        case ACTIONBAR:
          return new XieMessageActionBar(config.getString(path + ".text"),
              config.getInt(path + ".stay"));
        case TITLE:
          return new XieMessageTitle(config.getString(path + ".title"),
              config.getString(path + ".subtitle"), config.getInt(path + ".in"),
              config.getInt(path + ".stay"), config.getInt(path + ".out"));
      }
    } catch (IllegalArgumentException e) {
      throw new ConfigException(
          "'" + path + "' Cannot find enum with this name (" + e.getMessage() + ")");
    }
    return null;
  }

  public XieMessage getMessage();

  public void setMessage(XieMessage xieMessage);
}
