package pl.xierip.xieapi.managers;

import com.google.common.collect.Lists;
import org.bukkit.plugin.java.JavaPlugin;
import pl.xierip.xieapi.XieAPI;
import pl.xierip.xieapi.enums.LogType;
import pl.xierip.xieapi.enums.SaveType;
import pl.xierip.xieapi.objects.Config;

import java.io.*;
import java.util.List;

public class ConfigManager {

    private final static List<Config> CONFIGS = Lists.newArrayList();

    private static void copyDefaults(final InputStream in, final File file) throws IOException {
        try (OutputStream out = new FileOutputStream(file)) {
            final byte[] b = new byte[1024];
            int l;
            while ((l = in.read(b)) > 0)
                out.write(b, 0, l);
        } finally {
            in.close();
        }
    }

    private static Config getConfig(final String id) {
        return CONFIGS.stream().filter(config -> config.getId().equals(id)).findFirst().orElse(null);
    }

    private static Config register(final String id, String resource, String path, final JavaPlugin plugin) {
        if (!resource.isEmpty() && !resource.endsWith(".yml"))
            resource = resource + ".yml";
        if (!path.endsWith(".yml"))
            path = path + ".yml";
        final File file = new File(plugin.getDataFolder(), path);
        final Config cfg = new Config(id, file);
        if (!file.exists() && !file.isFile()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (final IOException e) {
                XieAPI.getLogging().log(LogType.EXCEPTION, "Can't create file of config: " + id, e);
            }
            final InputStream is = plugin.getResource(resource);
            if (is != null) {
                try {
                    copyDefaults(is, file);
                } catch (final Exception e) {
                    XieAPI.getLogging().log(LogType.EXCEPTION, "Can't copy file of config: " + id, e);
                }
            }
        }
        cfg.load();
        if (CONFIGS.stream().filter(config -> config.getId().equals(id)).findFirst().orElse(null) != null)
            return cfg;
        CONFIGS.add(cfg);
        return cfg;
    }

    public static Config register(final String id, final String path, final JavaPlugin plugin) {
        return register(id, path, path, plugin);
    }

    public static void unregister(final String id, final SaveType type) {
        final Config cfg = getConfig(id);
        if (cfg == null)
            return;
        if (type == SaveType.ALL)
            cfg.saveVariables();
        if (type == SaveType.ALL || type == SaveType.FILE)
            cfg.save();
        CONFIGS.remove(cfg);
    }

    public static void unregisters(final SaveType type) {
        CONFIGS.forEach(cfg -> {
            if (type == SaveType.ALL)
                cfg.saveVariables();
            if (type == SaveType.ALL || type == SaveType.FILE)
                cfg.save();
        });
        CONFIGS.clear();
    }
}
