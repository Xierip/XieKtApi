package pl.xierip.xieapi.config;

import com.google.common.collect.Maps;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import pl.xierip.xieapi.XieAPI;
import pl.xierip.xieapi.config.exception.ConfigException;
import pl.xierip.xieapi.logging.Logging.LogType;
import pl.xierip.xieapi.items.custom.CustomItemsManager;
import pl.xierip.xieapi.utils.DateUtil;
import pl.xierip.xieapi.enchantments.EnchantmentsUtil;
import pl.xierip.xieapi.items.ItemBuilder;
import pl.xierip.xieapi.utils.NumberUtil;
import pl.xierip.xieapi.parsers.Parsers;
import pl.xierip.xieapi.utils.StringUtil;

public class Config {

  @Getter
  private File file;
  @Getter
  private String id;
  private Map<String, Object> variables;
  @Getter
  private YamlConfiguration yamlConfiguration;

  public Config(final String id, final File file) {
    yamlConfiguration = new YamlConfiguration();
    this.id = id.trim();
    this.file = file;
    this.variables = Maps.newHashMap();
  }


  public Object get(final String path) throws ConfigException {
    if (!yamlConfiguration.isSet(path)) {
      throw new ConfigException("'" + path + "' isn't set");
    } else

    {
      return yamlConfiguration.get(path);
    }
  }

  public boolean getBoolean(final String path) throws ConfigException {
    if (!yamlConfiguration.isSet(path)) {
      throw new ConfigException("'" + path + "' isn't set");
    } else if (!yamlConfiguration.isBoolean(path)) {
      throw new ConfigException("'" + path + "' isn't boolean");
    } else {
      return yamlConfiguration.getBoolean(path);
    }
  }

  public List<Boolean> getBooleanList(final String path) throws ConfigException {
    if (!yamlConfiguration.isSet(path)) {
      throw new ConfigException("'" + path + "' isn't set");
    } else if (!yamlConfiguration.isList(path)) {
      throw new ConfigException("'" + path + "' isn't list");
    } else {
      return yamlConfiguration.getBooleanList(path);
    }
  }

  public List<Byte> getByteList(final String path) throws ConfigException {
    if (!yamlConfiguration.isSet(path)) {
      throw new ConfigException("'" + path + "' isn't set");
    } else if (!yamlConfiguration.isList(path)) {
      throw new ConfigException("'" + path + "' isn't list");
    } else {
      return yamlConfiguration.getByteList(path);
    }
  }

  public String getColoredString(final String path) throws ConfigException {
    return StringUtil.fixColors(getString(path));
  }

  public List<String> getColoredStringList(final String path) throws ConfigException {
    return StringUtil.fixColors(getStringList(path));
  }

  public ConfigurationSection getConfigurationSection(final String path) throws ConfigException {
    if (!yamlConfiguration.isSet(path)) {
      throw new ConfigException("'" + path + "' isn't set");
    } else if (!yamlConfiguration.isConfigurationSection(path)) {
      throw new ConfigException("'" + path + "' isn't configuration section");
    } else {
      return yamlConfiguration.getConfigurationSection(path);
    }
  }

  public double getDouble(final String path) throws ConfigException {
    if (!yamlConfiguration.isSet(path)) {
      throw new ConfigException("'" + path + "' isn't set");
    } else if (!yamlConfiguration.isDouble(path) && !yamlConfiguration.isInt(path)) {
      throw new ConfigException("'" + path + "' isn't double");
    } else {
      return yamlConfiguration.getDouble(path);
    }
  }

  public List<Double> getDoubleList(final String path) throws ConfigException {
    if (!yamlConfiguration.isSet(path)) {
      throw new ConfigException("'" + path + "' isn't set");
    } else if (!yamlConfiguration.isList(path)) {
      throw new ConfigException("'" + path + "' isn't list");
    } else {
      return yamlConfiguration.getDoubleList(path);
    }
  }

  public float getFloat(final String path) throws ConfigException {
    if (!yamlConfiguration.isSet(path)) {
      throw new ConfigException("'" + path + "' isn't set");
    } else if (!yamlConfiguration.isDouble(path) && !yamlConfiguration.isInt(path)) {
      throw new ConfigException("'" + path + "' isn't double");
    } else {
      return (float) yamlConfiguration.getDouble(path);
    }
  }

  public List<Float> getFloatList(final String path) throws ConfigException {
    if (!yamlConfiguration.isSet(path)) {
      throw new ConfigException("'" + path + "' isn't set");
    } else if (!yamlConfiguration.isList(path)) {
      throw new ConfigException("'" + path + "' isn't list");
    } else {
      return yamlConfiguration.getFloatList(path);
    }
  }

  public int getInt(final String path) throws ConfigException {
    if (!yamlConfiguration.isSet(path)) {
      throw new ConfigException("'" + path + "' isn't set");
    } else if (!yamlConfiguration.isInt(path)) {
      throw new ConfigException("'" + path + "' isn't int");
    } else {
      return yamlConfiguration.getInt(path);
    }
  }

  public List<Integer> getIntegerList(final String path) throws ConfigException {
    if (!yamlConfiguration.isSet(path)) {
      throw new ConfigException("'" + path + "' isn't set");
    } else if (!yamlConfiguration.isList(path)) {
      throw new ConfigException("'" + path + "' isn't list");
    } else {
      return yamlConfiguration.getIntegerList(path);
    }
  }

  public List<ItemStack> getItemStackList(final String path) throws ConfigException {
    if (!yamlConfiguration.isSet(path)) {
      throw new ConfigException("'" + path + "' isn't set");
    } else if (!yamlConfiguration.isList(path)) {
      throw new ConfigException("'" + path + "' isn't list");
    } else {
      return (List<ItemStack>) yamlConfiguration.getList(path);
    }
  }

  public Set<String> getKeys(final String path) throws ConfigException {
    return getConfigurationSection(path).getKeys(false);
  }

  public List<?> getList(final String path) throws ConfigException {
    if (!yamlConfiguration.isSet(path)) {
      throw new ConfigException("'" + path + "' isn't set");
    } else if (!yamlConfiguration.isList(path)) {
      throw new ConfigException("'" + path + "' isn't list");
    } else {
      return yamlConfiguration.getList(path);
    }
  }

  public long getLong(final String path) throws ConfigException {
    if (!yamlConfiguration.isSet(path)) {
      throw new ConfigException("'" + path + "' isn't set");
    } else if (!yamlConfiguration.isLong(path) && !yamlConfiguration.isInt(path)) {
      throw new ConfigException("'" + path + "' isn't long");
    } else {
      return yamlConfiguration.getLong(path);
    }
  }

  public List<Long> getLongList(final String path) throws ConfigException {
    if (!yamlConfiguration.isSet(path)) {
      throw new ConfigException("'" + path + "' isn't set");
    } else if (!yamlConfiguration.isList(path)) {
      throw new ConfigException("'" + path + "' isn't list");
    } else {
      return yamlConfiguration.getLongList(path);
    }
  }

  public Material getMaterial(final String path) throws ConfigException {
    final Object o = get(path);
    if (o instanceof String) {
      return Material.getMaterial((String) o);
    } else if (o instanceof Integer) {
      return Material.getMaterial((Integer) o);
    }
    return null;
  }

  public List<Material> getMaterialList(final String path) throws ConfigException {
    final List<Material> list = new ArrayList<>();
    for (final Object o : getList(path)) {
      if (o instanceof String) {
        list.add(Material.getMaterial((String) o));
      } else if (o instanceof Integer) {
        list.add(Material.getMaterial((Integer) o));
      }
    }
    return list;
  }

  public ConfigurationSection getSafeConfigurationSection(final String path) {
    if (!yamlConfiguration.isSet(path)) {
      return null;
    } else if (!yamlConfiguration.isConfigurationSection(path)) {
      return null;
    } else {
      return yamlConfiguration.getConfigurationSection(path);
    }
  }

  public Set<String> getSafeKeys(final String path) {
    if (!yamlConfiguration.isSet(path)) {
      return new HashSet<>();
    } else if (!yamlConfiguration.isConfigurationSection(path)) {
      return new HashSet<>();
    } else {
      return yamlConfiguration.getConfigurationSection(path).getKeys(false);
    }
  }

  public String getString(final String path) throws ConfigException {
    if (!yamlConfiguration.isSet(path)) {
      throw new ConfigException("'" + path + "' isn't set");
    } else if (!yamlConfiguration.isString(path)) {
      throw new ConfigException("'" + path + "' isn't string");
    } else {
      return yamlConfiguration.getString(path);
    }
  }

  public String getString(final String path, final String def) {
    if (!yamlConfiguration.isSet(path)) {
      return def;
    } else if (!yamlConfiguration.isString(path)) {
      return def;
    } else {
      return yamlConfiguration.getString(path);
    }
  }

  public List<String> getStringList(final String path) throws ConfigException {
    if (!yamlConfiguration.isSet(path)) {
      throw new ConfigException("'" + path + "' isn't set");
    } else if (!yamlConfiguration.isList(path)) {
      throw new ConfigException("'" + path + "' isn't list");
    } else {
      return yamlConfiguration.getStringList(path);
    }
  }

  public long getTimeMillis(final String path) throws ConfigException {
    if (!yamlConfiguration.isSet(path)) {
      throw new ConfigException("'" + path + "' isn't set");
    } else if (!yamlConfiguration.isInt(path) && !yamlConfiguration.isString(path)) {
      throw new ConfigException("'" + path + "' isn't time");
    } else {
      try {
        return DateUtil.formatToMillis(yamlConfiguration.getString(path));
      } catch (final Exception ignore) {
        throw new ConfigException("'" + path + "' isn't time");
      }
    }
  }

  public long getTimeSecounds(final String path) throws ConfigException {
    if (!yamlConfiguration.isSet(path)) {
      throw new ConfigException("'" + path + "' isn't set");
    } else if (!yamlConfiguration.isString(path)) {
      throw new ConfigException("'" + path + "' isn't time");
    } else {
      try {
        return DateUtil.formatToMillis(yamlConfiguration.getString(path)) / 1000;
      } catch (final Exception ignore) {
        throw new ConfigException("'" + path + "' isn't time");
      }
    }
  }

  public long getTimeTicks(final String path) throws ConfigException {
    if (!yamlConfiguration.isSet(path)) {
      throw new ConfigException("'" + path + "' isn't set");
    } else if (!yamlConfiguration.isString(path)) {
      throw new ConfigException("'" + path + "' isn't time");
    } else {
      try {
        return DateUtil.formatToMillis(yamlConfiguration.getString(path)) / 50;
      } catch (final Exception ignore) {
        throw new ConfigException("'" + path + "' isn't time");
      }
    }
  }

  @SuppressWarnings("unchecked")
  public <T> T getVariable(final String path) {
    return (T) variables.get(path);
  }

  public Map<String, Object> getVariables() {
    return variables;
  }

  public boolean isConfigurationSection(final String path) {
    return yamlConfiguration.isConfigurationSection(path);
  }

  public boolean isInstanceOf(final String path, final Class<?> c) {
    return yamlConfiguration.isSet(path) && c.isInstance(yamlConfiguration.get(path));
  }

  public boolean isSet(final String path) {
    return yamlConfiguration.isSet(path);
  }

  public void load() {
    try {
      yamlConfiguration.load(file);
    } catch (IOException | InvalidConfigurationException e) {
      XieAPI.getLogging().log(LogType.EXCEPTION, "Error with load file id: " + id, e);
    }
    loadVariables();
  }

  private void loadVariables() {
    for (final String key : yamlConfiguration.getKeys(true)) {
      variables.put(key, yamlConfiguration.get(key));
    }
  }

  public ItemStack parseItemStack(final String path) throws ConfigException {
    if (!yamlConfiguration.isSet(path)) {
      throw new ConfigException("'" + path + "' isn't set");
    } else if (!yamlConfiguration.isString(path)) {
      if (!yamlConfiguration.isSet(path + ".id")) {
        throw new ConfigException("'" + path + "' isn't set");
      } else if (!yamlConfiguration.isInt(path + ".id")) {
        throw new ConfigException("'" + path + "' isn't itemstack");
      } else {
        try {
          final Material material = Material.getMaterial(yamlConfiguration.getInt(path + ".id"));
          if (material == null) {
            throw new ConfigException("'" + path + "' id is invalid");
          }
          int amount = 1;
          if (yamlConfiguration.isSet(path + ".amount") && yamlConfiguration
              .isInt(path + ".amount")) {
            amount = yamlConfiguration.getInt(path + ".amount");
          }
          short data = 0;
          if (yamlConfiguration.isSet(path + ".data") && yamlConfiguration.isInt(path + ".data")) {
            data = (short) yamlConfiguration.getInt(path + ".data");
          }
          List<String> lore = new ArrayList<>();
          if (yamlConfiguration.isSet(path + ".lore") && yamlConfiguration.isList(path + ".lore")) {
            lore = this.getColoredStringList(path + ".lore");
          }
          String name = null;
          if (yamlConfiguration.isSet(path + ".name") && yamlConfiguration
              .isString(path + ".name")) {
            name = this.getColoredString(path + ".name");
          }
          final Map<Enchantment, Integer> enchantments = new HashMap<>();
          if (yamlConfiguration.isSet(path + ".enchants") && yamlConfiguration
              .isConfigurationSection(path + ".enchants")) {
            for (final String s : this.getSafeKeys(path + ".enchants")) {
              final Enchantment enchantment = EnchantmentsUtil.getEnchantment(s);
              if (enchantment == null) {
                continue;
              }
              enchantments.put(enchantment, this.getInt(path + ".enchants." + s));
            }
          }

          return new ItemBuilder(material).setAmount(amount).setDurability(data).setLore(lore)
              .setName(name).addUnsafeEnchantments(enchantments).toItemStack();
        } catch (final Exception ignore) {
          throw new ConfigException("'" + path + "' isn't itemstack");
        }
      }
    } else {
      try {
        final String string = yamlConfiguration.getString(path);
        final ItemStack customItem = CustomItemsManager.getCustomItem(string);
        if (customItem != null) {
          return customItem.clone();
        }
        return Parsers.parseItemStack(string);
      } catch (final Exception ignore) {
        throw new ConfigException("'" + path + "' isn't itemstack");
      }
    }
  }

  public List<ItemStack> parseItemStackList(final String path) throws ConfigException {
    if (!yamlConfiguration.isSet(path)) {
      throw new ConfigException("'" + path + "' isn't set");
    } else if (!yamlConfiguration.isList(path)) {
      throw new ConfigException("'" + path + "' isn't list");
    } else {
      final List<ItemStack> items = new ArrayList<>();
      for (final String item : yamlConfiguration.getStringList(path)) {
        try {
          items.add(Parsers.parseItemStack(item));
        } catch (final Exception ignore) {
          throw new ConfigException("'" + path + "' isn't itemstack");
        }
      }
      return items;
    }
  }

  public PotionEffect parsePotionEffect(final String path) throws ConfigException {
    return Parsers.parsePotionEffect(getString(path));
  }

  public void save() {
    try {
      yamlConfiguration.save(file);
    } catch (final IOException e) {
      XieAPI.getLogging().log(LogType.EXCEPTION, "Error with save file id: " + id, e);
    }
  }

  public void saveVariables() {
    for (final Map.Entry<String, Object> entry : variables.entrySet()) {

      yamlConfiguration.set(entry.getKey(), entry.getValue());
    }
  }

  public void set(final String path, final Object o) {
    yamlConfiguration.set(path, o);
  }

  public void setDefault(final String path, final Object object) {
    if (!yamlConfiguration.isSet(path)) {
      yamlConfiguration.set(path, object);
    }
  }

  public void setVariable(final String path, final Object obj) {
    variables.put(path, obj);
  }

  public Map<Integer, ItemStack> getItemsWithSlots(final String path) throws ConfigException {
    final Map<Integer, ItemStack> map = new HashMap<>();
    for (final String s : getKeys(path)) {
      final Optional<Integer> integer = NumberUtil.parseInt(s);
      if (!integer.isPresent()) {
        throw new ConfigException("'" + path + "'->'" + s + "' isn't number!");
      }
      map.put(integer.get(), parseItemStack(path + "." + s));
    }
    return map;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    final Config config = (Config) o;

    return id.equals(config.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }
}
