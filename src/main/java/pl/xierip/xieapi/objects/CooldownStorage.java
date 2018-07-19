package pl.xierip.xieapi.objects;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by xierip on 23.05.18.
 * Web: http://xierip.pl
 */
public class CooldownStorage {
    private long cooldown;
    private Map<UUID, Long> players = new HashMap<>();

    public CooldownStorage(long cooldown) {
        this.cooldown = cooldown;
    }

    public boolean hasCooldown(UUID uuid) {
        return players.getOrDefault(uuid, 0L) > System.currentTimeMillis();
    }

    public void addCooldown(UUID uuid) {
        players.put(uuid, System.currentTimeMillis() + cooldown);
    }

    public void removeCooldown(UUID uuid) {
        players.remove(uuid);
    }

    public boolean addIfNotExist(UUID uuid) {
        if (hasCooldown(uuid)) {
            return false;
        }
        addCooldown(uuid);
        return true;
    }
}
