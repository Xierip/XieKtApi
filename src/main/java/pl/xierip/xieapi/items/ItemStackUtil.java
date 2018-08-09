package pl.xierip.xieapi.items;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;
import pl.xierip.xieapi.XieAPI;
import pl.xierip.xieapi.logging.Logging.LogType;
import pl.xierip.xieapi.parsers.Parsers;
import pl.xierip.xieapi.random.RandomUtil;
import pl.xierip.xieapi.reflection.ReflectionUtil;

/**
 * Created by Xierip on 2016-06-25. DarkElite.pl ©
 */
public class ItemStackUtil {

  private static List<Material> repairable = Arrays
      .asList(Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS,
          Material.DIAMOND_BOOTS, Material.IRON_HELMET, Material.IRON_CHESTPLATE,
          Material.IRON_LEGGINGS, Material.IRON_BOOTS, Material.CHAINMAIL_HELMET,
          Material.CHAINMAIL_CHESTPLATE, Material.CHAINMAIL_LEGGINGS, Material.CHAINMAIL_BOOTS,
          Material.GOLD_HELMET, Material.GOLD_CHESTPLATE, Material.GOLD_LEGGINGS,
          Material.GOLD_BOOTS, Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE,
          Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS, Material.DIAMOND_SWORD,
          Material.IRON_SWORD, Material.GOLD_SWORD, Material.STONE_SWORD, Material.WOOD_SWORD,
          Material.DIAMOND_AXE, Material.IRON_AXE, Material.GOLD_AXE, Material.STONE_AXE,
          Material.WOOD_AXE, Material.DIAMOND_SPADE, Material.IRON_SPADE, Material.GOLD_SPADE,
          Material.STONE_SPADE, Material.WOOD_SPADE, Material.BOW, Material.FISHING_ROD,
          Material.DIAMOND_PICKAXE, Material.IRON_PICKAXE, Material.GOLD_PICKAXE,
          Material.STONE_PICKAXE, Material.WOOD_PICKAXE);

  public static int calcHas(final ItemStack itemStack, final Inventory inventory) {
    int has = 0;
    for (final ItemStack stack : inventory.getContents()) {
      if (stack != null) {
        if (stack.isSimilar(itemStack)) {
          has += stack.getAmount();
        }
      }
    }
    return has;
  }

  public static boolean checkAndRemove(final List<ItemStack> items, final Player player) {
    final boolean has = checkItems(items, player);
    if (has) {
      removeItems(items, player);
    }
    return has;
  }

  public static boolean checkItems(final List<ItemStack> items, final Player p) {
    for (final ItemStack item : items) {
      if (!p.getInventory().containsAtLeast(item, item.getAmount())) {
        return false;
      }
    }
    return true;
  }

  public static String convertItemStackToJson(final ItemStack itemStack) {
    final Class<?> craftItemStackClazz = ReflectionUtil.getOBCClass("inventory.CraftItemStack");
    final Method asNMSCopyMethod = ReflectionUtil
        .getMethod(craftItemStackClazz, "asNMSCopy", ItemStack.class);
    final Class<?> nmsItemStackClazz = ReflectionUtil.getCraftClass("ItemStack");
    final Class<?> nbtTagCompoundClazz = ReflectionUtil.getCraftClass("NBTTagCompound");
    final Method saveNmsItemStackMethod = ReflectionUtil
        .getMethod(nmsItemStackClazz, "save", nbtTagCompoundClazz);
    final Object nmsNbtTagCompoundObj;
    final Object nmsItemStackObj;
    final Object itemAsJsonObject;
    try {
      nmsNbtTagCompoundObj = nbtTagCompoundClazz.newInstance();
      nmsItemStackObj = asNMSCopyMethod.invoke(null, itemStack);
      itemAsJsonObject = saveNmsItemStackMethod.invoke(nmsItemStackObj, nmsNbtTagCompoundObj);
    } catch (final Exception e) {
      XieAPI.getLogging().log(LogType.EXCEPTION, "Failed to covert itemstack to nms item", e);
      return null;
    }
    return itemAsJsonObject.toString();
  }

  public static void drop(final List<ItemStack> iss, final Location loc,
      final List<Material> ignored) {
    final List<ItemStack> is = iss.stream().map(ItemStack::clone).collect(Collectors.toList());
    if (is != null) {
      for (final ItemStack item : is) {
        if (item == null || item.getType().equals(Material.AIR)) {
          continue;
        }
        loc.getWorld().dropItemNaturally(loc, item);
      }
    }
  }

  public static String getCustomOrPolishName(final ItemStack itemStack) {
    return (itemStack.getItemMeta().getDisplayName() != null ? itemStack.getItemMeta()
        .getDisplayName()
        : (Parsers.getPolishNames().containsKey(itemStack.getType()) ? Parsers.getPolishNames()
            .get(itemStack.getType()) : itemStack.getType().name()));
  }

  public static String getCustomOrPolishName(final ItemStack itemStack, final String air) {
    if (itemStack == null || itemStack.getType().equals(Material.AIR)) {
      return "reki";
    }
    return (itemStack.getItemMeta().getDisplayName() != null ? itemStack.getItemMeta()
        .getDisplayName()
        : (Parsers.getPolishNames().containsKey(itemStack.getType()) ? Parsers.getPolishNames()
            .get(itemStack.getType()) : itemStack.getType().name()));
  }

  public static List<String> getCustomOrPolishNameList(final List<ItemStack> list) {
    return list.stream().map(
        item -> "&7" + getCustomOrPolishName(item) + "&7(" + item.getTypeId() + ":" + (
            item.getDurability() == 1 ? 1 : 0) + ") &fx" + item.getAmount())
        .collect(Collectors.toList());
  }

  public static String getCustomOrPolishNameListString(final List<ItemStack> items) {
    StringBuilder itemsString = new StringBuilder("\n");
    for (final String s1 : getCustomOrPolishNameList(items)) {
      itemsString.append("&7 - ").append(s1).append("\n");
    }
    return itemsString.toString();
  }

  public static void giveOrDrop(final Player p, final ItemStack is, final Location loc) {
    if (is != null && !is.getType().equals(Material.AIR)) {
      for (final ItemStack i : p.getInventory().addItem(is).values()) {
        loc.getWorld().dropItem(loc, i);
      }
      p.updateInventory();
    }
  }

  public static void giveOrDrop(final Player p, final ItemStack is) {
    if (is != null && !is.getType().equals(Material.AIR)) {
      for (final ItemStack i : p.getInventory().addItem(is).values()) {
        p.getWorld().dropItem(p.getLocation(), i);
      }
      p.updateInventory();
    }
  }

  public static boolean giveOrDrop(final Player p, final List<ItemStack> is, final Location loc) {
    final List<ItemStack> itemStacks = is.stream().map(ItemStack::clone)
        .collect(Collectors.toList());
    boolean full = false;
    if (itemStacks != null) {
      for (final ItemStack item : itemStacks) {
        if (item == null || item.getType().equals(Material.AIR)) {
          continue;
        }
        for (final ItemStack i : p.getInventory().addItem(item).values()) {
          if (i == null) {
            continue;
          }
          loc.getWorld().dropItem(loc, i);
          full = true;
        }
      }
    }
    return full;
  }

  public static void giveOrDrop(final Player p, ItemStack is, final Location loc,
      final List<Material> ignored) {
    is = is.clone();
    if (is != null) {
      for (final ItemStack i : p.getInventory().addItem(is).values()) {
        if (i == null || i.getType().equals(Material.AIR)) {
          continue;
        }
        if (ignored.contains(i.getType())) {
          continue;
        }
        loc.getWorld().dropItem(loc, i);
      }
    }
  }

  public static boolean giveOrDrop(final Player p, final List<ItemStack> iss, final Location loc,
      final List<Material> ignored) {
    final List<ItemStack> is = iss.stream().map(ItemStack::clone).collect(Collectors.toList());
    boolean full = false;
    if (is != null) {
      for (final ItemStack item : is) {
        if (item == null || item.getType().equals(Material.AIR)) {
          continue;
        }
        for (final ItemStack i : p.getInventory().addItem(item).values()) {
          if (i == null) {
            continue;
          }
          if (ignored.contains(i.getType())) {
            continue;
          }
          if (i.getType() != Material.AIR) {
            loc.getWorld().dropItem(loc, i);
          }
          full = true;
        }
      }
    }
    return full;
  }

  public static boolean hasSpace(final Player player) {
    for (final ItemStack item : player.getInventory().getContents()) {
      if (item == null) {
        return true;
      }
      if (item.getType().equals(Material.AIR)) {
        return true;
      }
    }
    return false;
  }

  public static ItemStack[] itemStackArrayFromBase64(final String data) throws IOException {
    try {
      final ByteArrayInputStream inputStream = new ByteArrayInputStream(
          Base64Coder.decodeLines(data));
      final BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
      final ItemStack[] items = new ItemStack[dataInput.readInt()];
      for (int i = 0; i < items.length; i++) {
        items[i] = (ItemStack) dataInput.readObject();
      }
      dataInput.close();
      return items;
    } catch (final ClassNotFoundException e) {
      throw new IOException("Unable to decode class type.", e);
    }
  }

  public static String itemStackArrayToBase64(final ItemStack[] items)
      throws IllegalStateException {
    try {
      final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      final BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
      dataOutput.writeInt(items.length);
      for (final ItemStack item : items) {
        dataOutput.writeObject(item);
      }
      dataOutput.close();
      return Base64Coder.encodeLines(outputStream.toByteArray());
    } catch (final Exception e) {
      throw new IllegalStateException("Unable to save item stacks.", e);
    }
  }

  public static String playerInventoryToBase64(final PlayerInventory playerInventory)
      throws IllegalStateException {
    final List<ItemStack> list = new ArrayList<>();
    Arrays.stream(playerInventory.getContents()).filter(itemStack -> itemStack != null)
        .forEach(list::add);
    Arrays.stream(playerInventory.getArmorContents()).filter(itemStack -> itemStack != null)
        .forEach(list::add);
    return itemStackArrayToBase64(list.toArray(new ItemStack[list.size()]));
  }

  public static void recalculateDurability(final Player player, final ItemStack item) {
    if (item == null) {
      return;
    }
    if (item.getType().equals(Material.AIR)) {
      return;
    }
    if (item.getType().getMaxDurability() == 0) {
      return;
    }
    final int enchantLevel = item.getEnchantmentLevel(Enchantment.DURABILITY);
    final short d = item.getDurability();
    if (enchantLevel > 0) {
      if (100 / (enchantLevel + 1) > RandomUtil.getIntBetween(0, 100)) {
        if (d == item.getType().getMaxDurability()) {
          player.getInventory().clear(player.getInventory().getHeldItemSlot());
          player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
        } else {
          item.setDurability((short) (d + 1));
        }
      }
    } else if (d == item.getType().getMaxDurability()) {
      player.getInventory().clear(player.getInventory().getHeldItemSlot());
      player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
    } else {
      item.setDurability((short) (d + 1));
    }
  }

  public static void removeItems(final List<ItemStack> items, final Player player) {
    final Inventory inv = player.getInventory();
    final List<ItemStack> removes = items.stream()
        .filter(item -> inv.containsAtLeast(item, item.getAmount())).collect(Collectors.toList());
    if (removes.size() == items.size()) {
      for (final ItemStack item : items) {
        player.getInventory().removeItem(item);
      }
    }
    player.updateInventory();
    removes.clear();
  }

  public static boolean repairItem(final ItemStack itemStack) {
    if (itemStack == null) {
      return false;
    }
    if (repairable.contains(itemStack.getType())) {
      itemStack.setDurability((short) 0);
      return true;
    } else {
      return false;
    }
  }
}
