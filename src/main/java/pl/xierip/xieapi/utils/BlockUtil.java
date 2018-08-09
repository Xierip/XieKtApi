package pl.xierip.xieapi.utils;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.CropState;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NetherWartsState;
import org.bukkit.SkullType;
import org.bukkit.block.Banner;
import org.bukkit.block.Block;
import org.bukkit.block.ShulkerBox;
import org.bukkit.block.Skull;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.CocoaPlant;
import org.bukkit.material.Crops;
import org.bukkit.material.Leaves;
import org.bukkit.material.NetherWarts;
import pl.xierip.xieapi.random.RandomUtil;

/**
 * Created by Xierip on 2016-06-25. DarkElite.pl ©
 */
public class BlockUtil {

  public static void createBox(final Location loc, int radius, final Material material) {
    radius = (radius + 1) / 2;
    for (int X = loc.getBlockX() - radius; X <= loc.getBlockX() + radius; X++) {
      for (int Y = loc.getBlockY() - radius; Y <= loc.getBlockY() + radius; Y++) {
        for (int Z = loc.getBlockZ() - radius; Z <= loc.getBlockZ() + radius; Z++) {
          if (X == loc.getBlockX() + radius || X == loc.getBlockX() - radius ||
              Y == loc.getBlockY() + radius || Y == loc.getBlockY() - radius ||
              Z == loc.getBlockZ() + radius || Z == loc.getBlockZ() - radius) {
            if (((X == loc.getBlockX() + radius - 1 || X == loc.getBlockX() - radius + 1)
                && (Z == loc.getBlockZ() + radius - 1 || Z == loc.getBlockZ() - radius + 1))
                && Y == loc.getBlockY() + radius) {
              loc.getWorld().getBlockAt(X, Y, Z).setType(Material.GLOWSTONE);
            } else if (Y == loc.getBlockY() - radius) {
              loc.getWorld().getBlockAt(X, Y, Z).setType(Material.BEDROCK);
            } else {
              loc.getWorld().getBlockAt(X, Y, Z).setType(material);
            }
          } else {
            loc.getWorld().getBlockAt(X, Y, Z).setType(Material.AIR);
          }
        }
      }
    }
  }

  public static Block getBlockBehindPlacedBlock(final Block block, final Location loc2) {
    final Location loc1 = block.getLocation();

    final org.bukkit.util.Vector vec = new org.bukkit.util.Vector(0, 0, 0);
    if (loc1.getBlockX() < loc2.getBlockX()) {
      if (loc1.getBlockX() == loc2.getBlockX()) {
        vec.setX(0);
      } else {
        if (loc1.getBlockX() == loc2.getBlockX() + 1) {
          vec.setX(0);
        } else if (loc1.getBlockX() == loc2.getBlockX() - 1) {
          vec.setX(0);
        } else {
          vec.setX(-1);
        }
      }
    } else if (loc1.getBlockX() > loc2.getBlockX()) {
      if (loc1.getBlockX() == loc2.getBlockX()) {
        vec.setX(0);
      } else {
        if (loc1.getBlockX() == loc2.getBlockX() + 1) {
          vec.setX(0);
        } else if (loc1.getBlockX() == loc2.getBlockX() - 1) {
          vec.setX(0);
        } else {
          vec.setX(1);
        }
      }
    }
    if (loc1.getBlockZ() < loc2.getBlockZ()) {
      if (loc1.getBlockZ() == loc2.getBlockZ()) {
        vec.setZ(0);
      } else {
        if (loc1.getBlockZ() == loc2.getBlockZ() + 1) {
          vec.setZ(0);
        } else if (loc1.getBlockZ() == loc2.getBlockZ() - 1) {
          vec.setZ(0);
        } else {
          vec.setZ(-1);
        }
      }
    } else if (loc1.getBlockZ() > loc2.getBlockZ()) {
      if (loc1.getBlockZ() == loc2.getBlockZ()) {
        vec.setZ(0);
      } else {
        if (loc1.getBlockZ() == loc2.getBlockZ() + 1) {
          vec.setZ(0);
        } else if (loc1.getBlockZ() == loc2.getBlockZ() - 1) {
          vec.setZ(0);
        } else {
          vec.setZ(1);
        }
      }
    }
    return loc1.add(vec).getBlock();
  }

  public static List<ItemStack> getDrops(final Block block, final ItemStack item) {
    final List<ItemStack> items = new ArrayList<>();
    final Material type = block.getType();
    int amount = 1;
    final short data;
    switch (type) {
      case SNOW:
        if (item.containsEnchantment(Enchantment.SILK_TOUCH)) {
          items.add(block.getState().getData().toItemStack(1));
          break;
        }
        switch (item.getType()) {
          case STONE_SPADE:
          case DIAMOND_SPADE:
          case GOLD_SPADE:
          case IRON_SPADE:
          case WOOD_SPADE:
            items.add(new ItemStack(Material.SNOW_BALL, 1));
            break;
        }
        break;
      case SIGN:
      case SIGN_POST:
      case WALL_SIGN:
        items.add(new ItemStack(Material.SIGN, 1));
        break;
      case ICE:
        block.setType(Material.WATER);
        break;
      case BANNER:
      case STANDING_BANNER:
      case WALL_BANNER:
        Banner banner1 = (Banner) block.getState();
        ItemStack banner = new ItemStack(Material.BANNER, 1);
        BannerMeta bannerMeta = (BannerMeta) banner.getItemMeta();
        bannerMeta.setPatterns(banner1.getPatterns());
        bannerMeta.setBaseColor(banner1.getBaseColor());
        banner.setItemMeta(bannerMeta);
        items.add(banner);
        break;
      case BLACK_SHULKER_BOX:
      case SILVER_SHULKER_BOX:
      case BLUE_SHULKER_BOX:
      case WHITE_SHULKER_BOX:
      case YELLOW_SHULKER_BOX:
      case GREEN_SHULKER_BOX:
      case GRAY_SHULKER_BOX:
      case RED_SHULKER_BOX:
      case MAGENTA_SHULKER_BOX:
      case PINK_SHULKER_BOX:
      case CYAN_SHULKER_BOX:
      case BROWN_SHULKER_BOX:
      case ORANGE_SHULKER_BOX:
      case PURPLE_SHULKER_BOX:
      case LIGHT_BLUE_SHULKER_BOX:
      case LIME_SHULKER_BOX:
        ShulkerBox shulkerBox = (ShulkerBox) block.getState();
        items.add(shulkerBox.getData().toItemStack(1));
        for (ItemStack itemStack : shulkerBox.getInventory()) {
          if (itemStack == null) {
            continue;
          }
          if (itemStack.getType() == Material.AIR) {
            continue;
          }
          items.add(itemStack);
        }
        break;
      case SKULL:
        Skull skull = (Skull) block.getState();
        ItemStack itemStack1 = new ItemStack(Material.SKULL_ITEM, 1,
            (short) skull.getSkullType().ordinal());
        if (skull.getSkullType() == SkullType.PLAYER) {
          SkullMeta itemMeta = (SkullMeta) itemStack1.getItemMeta();
          itemStack1.setItemMeta(itemMeta);
        }
        items.add(itemStack1);
        break;
      case MOB_SPAWNER:
        break;
      case NETHER_WARTS: {
        final NetherWarts warts = (NetherWarts) block.getState().getData();
        amount = (warts.getState() == NetherWartsState.RIPE ? (RandomUtil.getIntBetween(0, 2) + 2)
            : 1);
        items.add(new ItemStack(Material.NETHER_STALK, amount));
        break;
      }
      case COCOA: {
        final CocoaPlant plant = (CocoaPlant) block.getState().getData();
        amount = (plant.getSize() == CocoaPlant.CocoaPlantSize.LARGE ? 3 : 1);
        items.add(new ItemStack(Material.INK_SACK, amount, (short) 3));
        break;
      }
      case PUMPKIN_STEM: {
        items.add(new ItemStack(Material.PUMPKIN_SEEDS, 1));
        break;
      }
      case VINE:
        if (item.containsEnchantment(Enchantment.SILK_TOUCH) || item.getType() == Material.SHEARS) {
          items.add(new ItemStack(Material.VINE, 1));
        }
        break;
      case MELON_STEM: {
        items.add(new ItemStack(Material.MELON_SEEDS, 1));
        break;
      }
      case CARROT: {
        data = block.getState().getData().getData();
        switch (data) {
          case 0:
          case 1:
          case 2:
          case 3:
          case 4:
          case 5:
          case 6: {
            amount = 1;
            break;
          }
          case 7: {
            amount = RandomUtil.getIntBetween(1, 3);
            break;
          }
        }
        items.add(new ItemStack(Material.CARROT_ITEM, amount));
        break;
      }
      case POTATO: {
        data = block.getState().getData().getData();
        switch (data) {
          case 0:
          case 1:
          case 2:
          case 3:
          case 4:
          case 5:
          case 6: {
            amount = 1;
            break;
          }
          case 7: {
            amount = RandomUtil.getIntBetween(1, 3);
            break;
          }
        }
        items.add(new ItemStack(Material.POTATO_ITEM, amount));
        break;
      }
      case CROPS: {
        final Crops wheat = (Crops) block.getState().getData();
        int seedAmount = 1;
        if (wheat.getState() == CropState.RIPE) {
          items.add(new ItemStack(Material.WHEAT, RandomUtil.getIntBetween(1, 2)));
          seedAmount = 1 + RandomUtil.getIntBetween(0, 2);
        }
        items.add(new ItemStack(Material.SEEDS, seedAmount));
        break;
      }
      case SUGAR_CANE_BLOCK: {
        amount = 1;
        items.add(new ItemStack(Material.SUGAR_CANE, amount));
        break;
      }
      case LEAVES:
      case LEAVES_2:
        if (item.getType() == Material.SHEARS || item.containsEnchantment(Enchantment.SILK_TOUCH)) {
          items.add(new ItemStack(block.getType(), 1,
              (short) (((Leaves) block.getState().getData()).getSpecies().getData() - (
                  block.getType() == Material.LEAVES_2 ? 4 : 0))));
          break;
        }
        if (RandomUtil.getChance(2)) {
          items.add(new ItemStack(Material.APPLE, 1));
          break;
        }
        if (RandomUtil.getChance(8)) {
          items.add(new ItemStack(Material.SAPLING, 1,
              ((Leaves) block.getState().getData()).getSpecies().getData()));
        }
        break;
      case DOUBLE_PLANT:
      case REDSTONE_WIRE:
      case WOODEN_DOOR:
      case IRON_DOOR:
      case DARK_OAK_DOOR:
      case ACACIA_DOOR:
      case BIRCH_DOOR:
      case JUNGLE_DOOR:
      case TRAP_DOOR:
      case SPRUCE_DOOR:
      case WOOD_DOOR:
      case TRIPWIRE:
      case LEVER:
      case WOOD_BUTTON:
      case STONE_BUTTON:
      case DIODE_BLOCK_ON:
      case DIODE_BLOCK_OFF:
      case REDSTONE_COMPARATOR_OFF:
      case REDSTONE_COMPARATOR_ON:
      case DAYLIGHT_DETECTOR:
      case ANVIL:
      case REDSTONE_ORE: {
        items.addAll(block.getDrops(item));
        break;
      }
      case ENDER_CHEST:
        items.add(new ItemStack(Material.ENDER_CHEST));
        break;
      default: {
        if (item.containsEnchantment(Enchantment.SILK_TOUCH) && block.getType().isBlock()) {
          items.add(block.getState().getData().toItemStack(1));
          break;
        }
        items.addAll(block.getDrops(item));
        break;
      }

    }
    return items;
  }

  public static void removeBox(final Location loc, int radius) {
    radius = (radius + 1) / 2;
    for (int X = loc.getBlockX() - radius; X <= loc.getBlockX() + radius; X++) {
      for (int Y = loc.getBlockY() - radius; Y <= loc.getBlockY() + radius; Y++) {
        for (int Z = loc.getBlockZ() - radius; Z <= loc.getBlockZ() + radius; Z++) {
          loc.getWorld().getBlockAt(X, Y, Z).setType(Material.AIR);
        }
      }
    }
  }

  public static List<Block> sphere(final Location center, final int radius) {
    final List<Block> sphere = new ArrayList<>();
    for (int Y = -radius; Y < radius; Y++) {
      for (int X = -radius; X < radius; X++) {
        for (int Z = -radius; Z < radius; Z++) {
          if (Math.sqrt((X * X) + (Y * Y) + (Z * Z)) <= radius) {
            sphere.add(center.getWorld().getBlockAt(X + center.getBlockX(), Y + center.getBlockY(),
                Z + center.getBlockZ()));
          }
        }
      }
    }
    return sphere;
  }
}
