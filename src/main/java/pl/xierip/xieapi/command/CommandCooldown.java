package pl.xierip.xieapi.command;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Xierip on 01.02.2017. Web: http://xierip.pl
 */
public class CommandCooldown {

  private Long cooldown = 0L;
  private List<String[]> cooldownList;
  private Table<String[], UUID, Long> cooldownPlayers;
  private HashMap<UUID, Long> cooldownPlayersSoft;
  private HashMap<String[], Long> cooldowns;

  public CommandCooldown(String[] strings, Long time) {
    this.cooldownList = new ArrayList<>();
    this.cooldownList.add(strings);
    this.cooldowns = new HashMap<>();
    this.cooldowns.put(strings, time);
    this.cooldownPlayers = HashBasedTable.create();
  }

  public CommandCooldown(final Long cooldown) {
    this.cooldown = cooldown;
    this.cooldownPlayersSoft = new HashMap<>();
  }

  public void add(String[] args, Long time) {
    this.cooldownList.add(args);
    this.cooldowns.put(args, time);
    this.cooldownList.sort(Comparator.comparingInt(o -> -o.length));
  }

  public boolean addCooldown(final UUID uuid, final String[] args) {
    if (cooldown != 0) {
      if (cooldownPlayersSoft.containsKey(uuid)) {
        if (cooldownPlayersSoft.get(uuid) > System.currentTimeMillis()) {
          return false;
        } else {
          cooldownPlayersSoft.put(uuid, System.currentTimeMillis() + cooldown);
          return true;
        }
      } else {
        cooldownPlayersSoft.put(uuid, System.currentTimeMillis() + cooldown);
        return true;
      }
    }
    if (cooldownList.size() == 0) {
      return true;
    }
    final String[] cooldownType = findCooldownByArgs(args);
    if (cooldownType == null) {
      return true;
    }
    if (!cooldownPlayers.containsColumn(uuid)) {
      cooldownPlayers
          .put(cooldownType, uuid, System.currentTimeMillis() + cooldowns.get(cooldownType));
      return true;
    } else if (cooldownPlayers.get(cooldownType, uuid) > System.currentTimeMillis()) {
      return false;
    } else {
      cooldownPlayers
          .put(cooldownType, uuid, System.currentTimeMillis() + cooldowns.get(cooldownType));
      return true;
    }


  }

  public void clean() {
    if (cooldown != 0L) {
      if (cooldownPlayersSoft.isEmpty()) {
        return;
      }
      for (final Map.Entry<UUID, Long> entry : new HashMap<>(cooldownPlayersSoft).entrySet()) {
        if (entry.getValue() < System.currentTimeMillis()) {
          cooldownPlayersSoft.remove(entry.getKey());
        }
      }
    } else {
      if (cooldownPlayers.isEmpty()) {
        return;
      }
      for (Map.Entry<String[], Map<UUID, Long>> entry : new HashMap<>(cooldownPlayers.rowMap())
          .entrySet()) {
        for (Map.Entry<UUID, Long> longEntry : entry.getValue().entrySet()) {
          if (longEntry.getValue() < System.currentTimeMillis()) {
            cooldownPlayers.remove(entry.getKey(), longEntry.getKey());
          }
        }
      }
    }
  }

  private String[] findCooldownByArgs(final String[] args) {
    if (args.length == 0) {
      return null;
    }
    for (final String[] strings : cooldownList) {
      if (strings.length > args.length) {
        continue;
      }
      boolean all = true;
      for (int i = 0; i < strings.length; i++) {

        if ((!strings[i].equalsIgnoreCase(args[i])) && (!strings[i].equals("<any>"))) {
          all = false;
          break;
        }
      }
      if (all) {
        return strings;
      }
    }
    return null;
  }

  public Long getCooldown(final UUID uuid, final String[] args) {
    if (cooldown != 0) {
      if (cooldownPlayersSoft.containsKey(uuid)) {
        return cooldownPlayersSoft.get(uuid);
      }
      return 0L;
    }
    final String[] cooldownType = findCooldownByArgs(args);
    if (cooldownType == null || (!cooldownPlayers.contains(cooldownType, uuid))) {
      return 0L;
    }
    return cooldownPlayers.get(cooldownType, uuid);
  }
}
