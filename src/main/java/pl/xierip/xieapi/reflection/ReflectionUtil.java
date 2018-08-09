package pl.xierip.xieapi.reflection;

/**
 * @author Xieirp.
 */

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import pl.xierip.xieapi.XieAPI;
import pl.xierip.xieapi.logging.Logging.LogType;

public class ReflectionUtil {

  private static Map<Class<?>, Map<String, Field>> loadedFields = new HashMap<Class<?>, Map<String, Field>>();
  private static Map<Class<?>, Map<String, Field>> loadedDeclaredFields = new HashMap<Class<?>, Map<String, Field>>();

  private static Map<Class<?>, Map<String, Method>> loadedMethods = new HashMap<Class<?>, Map<String, Method>>();

  private static Map<String, Class<?>> loadedNMSClasses = new HashMap<String, Class<?>>();

  private static Map<String, Class<?>> loadedOBCClasses = new HashMap<String, Class<?>>();
  private static String version = Bukkit.getServer().getClass().getPackage().getName()
      .substring(Bukkit.getServer().getClass().getPackage().getName().lastIndexOf('.') + 1) + ".";

  private static boolean ClassListEqual(final Class<?>[] l1, final Class<?>[] l2) {
    boolean equal = true;
    if (l1.length != l2.length) {
      return false;
    }
    for (int i = 0; i < l1.length; i++) {
      if (l1[i] != l2[i]) {
        equal = false;
        break;
      }
    }
    return equal;
  }

  public static Set<Class<?>> getClasses(final File jarFile, final String packageName) {
    final Set<Class<?>> classes = new HashSet<Class<?>>();
    try {
      final JarFile file = new JarFile(jarFile);
      for (final Enumeration<JarEntry> entry = file.entries(); entry.hasMoreElements(); ) {
        final JarEntry jarEntry = entry.nextElement();
        final String name = jarEntry.getName().replace("/", ".");
        if (name.startsWith(packageName) && name.endsWith(".class") && !name.contains("$")) {
          classes.add(Class.forName(name.substring(0, name.length() - 6)));
        }
      }
      file.close();
    } catch (final Exception e) {
      e.printStackTrace();
    }
    return classes;
  }

  public static Class<?> getCraftClass(final String nmsClassName) {
    if (loadedNMSClasses.containsKey(nmsClassName)) {
      return loadedNMSClasses.get(nmsClassName);
    }

    final String clazzName = "net.minecraft.server." + version + nmsClassName;
    final Class<?> clazz;

    try {
      clazz = Class.forName(clazzName);
    } catch (final Throwable t) {
      t.printStackTrace();
      return loadedNMSClasses.put(nmsClassName, null);
    }

    loadedNMSClasses.put(nmsClassName, clazz);
    return clazz;
  }

  public static Field getDeclaredField(final Class<?> cl, final String field_name) {
    try {
      if (loadedDeclaredFields.containsKey(cl)) {
        final Map<String, Field> fieldMap = loadedDeclaredFields.get(cl);
        if (fieldMap.containsKey(field_name)) {
          return fieldMap.get(field_name);
        }
      }
      final Field declaredField = cl.getDeclaredField(field_name);
      declaredField.setAccessible(true);
      if (loadedDeclaredFields.containsKey(cl)) {
        loadedDeclaredFields.get(cl).put(field_name, declaredField);
      } else {
        HashMap<String, Field> v = new HashMap<>();
        v.put(field_name, declaredField);
        loadedDeclaredFields.put(cl, v);
      }
      return declaredField;
    } catch (final Exception var3) {
      var3.printStackTrace();
      return null;
    }
  }

  public static Field getField(final Class<?> cl, final String field_name) {
    try {
      if (loadedFields.containsKey(cl)) {
        final Map<String, Field> fieldMap = loadedFields.get(cl);
        if (fieldMap.containsKey(field_name)) {
          return fieldMap.get(field_name);
        }
      }
      final Field field = cl.getField(field_name);
      field.setAccessible(true);
      if (loadedFields.containsKey(cl)) {
        loadedFields.get(cl).put(field_name, field);
      } else {
        HashMap<String, Field> v = new HashMap<>();
        v.put(field_name, field);
        loadedFields.put(cl, v);
      }
      return field;
    } catch (final Exception var3) {
      var3.printStackTrace();
      return null;
    }
  }

  public static Object getHandle(final Entity entity) {
    try {
      return getMethod(entity.getClass(), "getHandle").invoke(entity);
    } catch (final Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public static Object getHandle(final World world) {
    try {
      return getMethod(world.getClass(), "getHandle").invoke(world);
    } catch (final Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public static Method getMethod(final Class<?> cl, final String method, final Class<?>... args) {
    for (final Method m : cl.getMethods()) {
      if (m.getName().equals(method) && ClassListEqual(args, m.getParameterTypes())) {
        m.setAccessible(true);
        return m;
      }
    }
    return null;
  }

  public static Method getMethod(final Class<?> cl, final String method) {
    if (loadedMethods.containsKey(cl)) {
      final Map<String, Method> map = loadedMethods.get(cl);
      if (map.containsKey(method)) {
        return map.get(method);
      }
    }
    for (final Method m : cl.getMethods()) {
      if (m.getName().equals(method)) {
        if (loadedMethods.containsKey(cl)) {
          loadedMethods.get(cl).put(method, m);
        } else {
          HashMap<String, Method> v = new HashMap<>();
          v.put(method, m);
          loadedMethods.put(cl, v);
        }
        m.setAccessible(true);
        return m;
      }
    }
    return null;
  }

  public synchronized static Class<?> getOBCClass(final String obcClassName) {
    if (loadedOBCClasses.containsKey(obcClassName)) {
      return loadedOBCClasses.get(obcClassName);
    }
    final String clazzName = "org.bukkit.craftbukkit." + version + obcClassName;
    final Class<?> clazz;
    try {
      clazz = Class.forName(clazzName);
    } catch (final Throwable t) {
      t.printStackTrace();
      loadedOBCClasses.put(obcClassName, null);
      return null;
    }

    loadedOBCClasses.put(obcClassName, clazz);
    return clazz;
  }

  public static double[] getRecentTps() {
    try {
      final Server server = Bukkit.getServer();
      final Field consoleField = getDeclaredField(server.getClass(), "console");
      final Object minecraftServer = consoleField.get(server);
      final Field recentTps = getDeclaredField(minecraftServer.getClass().getSuperclass(),
          "recentTps");
      return (double[]) recentTps.get(minecraftServer);
    } catch (final IllegalAccessException e) {
      XieAPI.getLogging().log(LogType.EXCEPTION, "getRecentTps", e);
    }
    return new double[]{-1, -1, -1};
  }

}