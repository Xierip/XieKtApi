package pl.xierip.xieapi.enums;

import lombok.Getter;
import org.bukkit.entity.EntityType;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


public class Mob {
    @Getter
    private static Map<String, EntityType> mobs = new HashMap<>();

    public static void enable() {
        try {
            Field fi = EntityType.class.getDeclaredField("NAME_MAP");
            fi.setAccessible(true);
            Map<String, EntityType> entityTypeMap = (Map<String, EntityType>) fi.get(null);
            for (Map.Entry<String, EntityType> entry : entityTypeMap.entrySet()) {
                if (entry.getValue().isSpawnable() && entry.getValue().isAlive()) {
                    mobs.put(entry.getKey(), entry.getValue());
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static EntityType getEntityByName(String name) {
        name = name.toLowerCase();
        if (mobs.containsKey(name)) {
            return mobs.get(name);
        }
        return null;
    }
}
