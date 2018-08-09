package pl.xierip.xieapi.utils;

import java.io.File;
import java.lang.reflect.Field;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.xierip.xieapi.command.CommandsManager;
import pl.xierip.xieapi.command.XieCommandStructure;
import pl.xierip.xieapi.reflection.ReflectionUtil;

/**
 * @author Xierip
 */
public final class Util {


  public static void registerListeners(final Plugin plugin, final Listener... listeners) {
    final PluginManager pluginManager = Bukkit.getPluginManager();
    for (final Listener listener : listeners) {
      pluginManager.registerEvents(listener, plugin);
    }
  }

  public static int registerListenersFromPackage(final JavaPlugin plugin, String packageS) {
    try {
      final Field fileField = ReflectionUtil.getDeclaredField(JavaPlugin.class, "file");
      final File file = (File) fileField.get(plugin);
      int listeners = 0;
      final PluginManager pluginManager = Bukkit.getPluginManager();
      for (final Class<?> clazz : ReflectionUtil.getClasses(file.getAbsoluteFile(), packageS)) {
        try {
          final Object o = clazz.newInstance();
          if (o instanceof Listener) {
            listeners++;
            pluginManager.registerEvents((Listener) o, plugin);
          }
        } catch (final Exception e) {
          e.printStackTrace();
        }
      }
      return listeners;
    } catch (final Exception e) {
      e.printStackTrace();
      return 0;
    }
  }

  public static int registerCommandsFromPackage(final JavaPlugin plugin, String packageS) {
    try {
      int commands = 0;
      final Field fileField = ReflectionUtil.getDeclaredField(JavaPlugin.class, "file");
      final File file = (File) fileField.get(plugin);
      for (final Class<?> clazz : ReflectionUtil.getClasses(file.getAbsoluteFile(), packageS)) {
        try {
          final Object o = clazz.newInstance();
          if (o instanceof XieCommandStructure) {
            commands++;
            CommandsManager.registerCommand(plugin, (XieCommandStructure) o);
          }
        } catch (final Exception e) {
          e.printStackTrace();
        }
      }
      return commands;
    } catch (final Exception e) {
      e.printStackTrace();
      return 0;
    }
  }


}
