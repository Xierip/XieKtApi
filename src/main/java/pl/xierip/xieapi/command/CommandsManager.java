package pl.xierip.xieapi.command;

import com.google.common.collect.Lists;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import pl.xierip.xieapi.XieAPI;
import pl.xierip.xieapi.logging.Logging.LogType;
import pl.xierip.xieapi.utils.DateUtil;
import pl.xierip.xieapi.utils.SendUtil;
import pl.xierip.xieapi.utils.StringUtil;

/**
 * @author Xierip
 */
public class CommandsManager extends BukkitCommand {

  @Getter
  private static final Map<XieCommandStructure, Method> COMMANDS = new HashMap<>();
  private static final Map<XieCommandStructure, CommandCooldown> cooldowns = new HashMap<>();
  private static CommandMap commandMap = null;

  private CommandsManager(final String name) {
    super(name);
  }

  public static boolean addCooldown(String name, final Long time) {
    name = name.toLowerCase();
    for (final Map.Entry<XieCommandStructure, Method> listeners : COMMANDS.entrySet()) {
      final XieCommandStructure key = listeners.getKey();
      final Command cmd = listeners.getValue().getAnnotation(Command.class);
      if (cmd.name().equalsIgnoreCase(name) || Arrays.asList(cmd.aliases()).contains(name)) {
        cooldowns.put(key, new CommandCooldown(time));
        return true;
      }

    }
    return false;
  }

  public static boolean addCooldown(String name, final String[] args, final Long time) {
    name = name.toLowerCase();
    for (final Map.Entry<XieCommandStructure, Method> listeners : COMMANDS.entrySet()) {
      final XieCommandStructure key = listeners.getKey();
      final Command cmd = listeners.getValue().getAnnotation(Command.class);
      if (cmd.name().equalsIgnoreCase(name) || Arrays.asList(cmd.aliases()).contains(name)) {
        if (cooldowns.containsKey(key)) {
          cooldowns.get(key).add(args, time);
        } else {
          cooldowns.put(key, new CommandCooldown(args, time));
        }
        return true;
      }

    }
    return false;
  }

  public static void enableCleaner() {
  }

  public static CommandMap getCommandMap() {
    if (commandMap == null) {
      final Field field;
      try {
        field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
        field.setAccessible(true);
        commandMap = (CommandMap) field.get(Bukkit.getServer());
      } catch (final Exception e) {
        XieAPI.getLogging().log(LogType.EXCEPTION, "Error with Commands Manager", e);
      }
    }
    return commandMap;
  }

  public static void registerCommand(final Plugin plugin, final XieCommandStructure handler) {
    for (final Method method : handler.getClass().getDeclaredMethods()) {
      final Command cmd = method.getAnnotation(Command.class);
      if (cmd == null) {
        continue;
      }
      final Class<?>[] value = method.getParameterTypes();
      if (value.length != 2 || !(CommandSender.class.equals(value[0]) || Player.class
          .equals(value[0])) || !String[].class.equals(value[1])) {
        continue;
      }
      final CommandsManager command = new CommandsManager(cmd.name());
      if (!Arrays.equals(cmd.aliases(), new String[]{""})) {
        command.setAliases(Lists.newArrayList(cmd.aliases()));
      }
      if (!cmd.description().equals("")) {
        command.setDescription(cmd.description());
      }
      getCommandMap().register(cmd.name(), command);
      COMMANDS.put(handler, method);

    }
  }

  public static void registerCommands(final Plugin plugin,
      final XieCommandStructure... xieCommands) {
    for (final XieCommandStructure xieCommand : xieCommands) {
      registerCommand(plugin, xieCommand);
    }
  }

  @Override
  public boolean execute(final CommandSender commandsender, final String label,
      final String[] args) {
    for (final Map.Entry<XieCommandStructure, Method> listeners : COMMANDS.entrySet()) {
      final XieCommandStructure key = listeners.getKey();
      final Method methods = listeners.getValue();
      final Command cmd = methods.getAnnotation(Command.class);
      if (!cmd.name().equalsIgnoreCase(this.getName())) {
        continue;
      }
      if (!cmd.permission().equals("") && !commandsender.hasPermission(cmd.permission())) {
        SendUtil.sendMessage(commandsender,
            StringUtil.replace(cmd.permissionMessage(), "{PERMISSION}", cmd.permission()));
        return true;
      }
      final boolean playerCommand = key instanceof XiePlayerCommand;
      if (commandsender instanceof Player) {
        if (cmd.senderType().equals(SenderType.CONSOLE)) {
          SendUtil.sendMessage(commandsender, "&cTa komenda jest tylko dla konsoli!");
          return true;
        }
        final Player player = (Player) commandsender;
        if ((!player.hasPermission("xiektapi.bypass.cooldown." + cmd.name())) && cooldowns
            .containsKey(key)) {
          final CommandCooldown commandCooldown = cooldowns.get(key);
          if (!commandCooldown.addCooldown(player.getUniqueId(), args)) {
            SendUtil.sendMessage(commandsender, "&cBedziesz mogl uzyc tej komendy za: " + DateUtil
                .formatTimeTo(commandCooldown.getCooldown(player.getUniqueId(), args)));
            return true;
          }

        }
      } else {
        if (cmd.senderType().equals(SenderType.PLAYER)) {
          SendUtil.sendMessage(commandsender, "&cTa komenda jest tylko dla graczy!");
          return true;
        }
        if (playerCommand) {
          SendUtil.sendMessage(commandsender, "&cTa komenda jest tylko dla graczy!");
          return true;
        }
      }
      try {
        methods.invoke(key, playerCommand ? (Player) commandsender : commandsender, args);
        return true;
      } catch (final Exception e) {
        XieAPI.getLogging().log(LogType.EXCEPTION, "Error with Commands Manager", e);
      }

    }
    return false;
  }

}