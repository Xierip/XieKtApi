package pl.xierip.xieapi.items.crafting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;
import pl.xierip.xieapi.XieAPI;
import pl.xierip.xieapi.config.Config;
import pl.xierip.xieapi.config.exception.ConfigException;
import pl.xierip.xieapi.items.custom.CustomItemsManager;
import pl.xierip.xieapi.logging.Logging.LogType;
import pl.xierip.xieapi.parsers.Parsers;

/**
 * @author Xieirp.
 */
public class CraftingManager {

  private static final String numbers = "0123456789";
  @Getter
  private static List<ItemStack> results = new ArrayList<>();

  public static ItemStack getResult(final String name) {
    return CustomItemsManager.getCustomItem(name);
  }

  public static void registerCrafting(final Config config, final String path, final String name) {
    ItemStack item = null;
    try {
      item = config.parseItemStack(path + ".item");
    } catch (final ConfigException e) {
      XieAPI.getLogging().log(LogType.ERROR, "[CONFIG] Error with loading crafting itemstack:");
      XieAPI.getLogging().log(LogType.ERROR, "[CONFIG] " + e.getMessage());
      return;
    }
    registerCrafting(config, path, name, item);
  }

  public static void registerCrafting(final Config config, final String path, final String name,
      ItemStack item) {
    final ShapedRecipe shape = new ShapedRecipe(
        new NamespacedKey(XieAPI.getInstance(), "XieAPI" + name), item);
    StringBuilder shapeOfShape = new StringBuilder();
    final Map<Character, Material> ingredients = new HashMap<>();
    final Map<Character, MaterialData> ingredientsData = new HashMap<>();
    int i = 0;
    try {
      final List<String> list = config.getStringList(path + ".crafting");
      if (list.size() != 3) {
        throw new ConfigException("'" + path + "' size must be 3");
      }
      for (final String s : list) {
        for (final String st : s.split("\\|")) {
          i++;
          if (st.equals(" ")) {
            shapeOfShape.append(" |");
          } else {
            shapeOfShape.append(numbers.charAt(i)).append("|");
            final ItemStack itemStack = Parsers.parseItemStack(st);
            ingredients.put(numbers.charAt(i), itemStack.getType());
            ingredientsData.put(numbers.charAt(i), itemStack.getData());
          }
        }
      }
    } catch (final ConfigException e) {
      XieAPI.getLogging().log(LogType.ERROR, "[CONFIG] Error with loading crafting recipe:");
      XieAPI.getLogging().log(LogType.ERROR, "[CONFIG] " + e.getMessage());
      return;
    }
    final String[] stringShape = shapeOfShape.toString().split("\\|");
    shape.shape(stringShape[0] + stringShape[1] + stringShape[2],
        stringShape[3] + stringShape[4] + stringShape[5],
        stringShape[6] + stringShape[7] + stringShape[8]);
    ingredients.forEach(shape::setIngredient);
    ingredientsData.forEach(shape::setIngredient);
    Bukkit.addRecipe(shape);
    results.add(item);
    CustomItemsManager.addCustomItem(name, item);
  }
}
