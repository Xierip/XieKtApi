package pl.xierip.xieapi.enchantments;

import java.lang.reflect.Field;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemStack;

/**
 * Created by xierip on 5/21/17. Web: http://xierip.pl
 */
public class ProCratesEnchantment extends EnchantmentWrapper {

  private static Enchantment a;

  private ProCratesEnchantment(final int n) {
    super(n);
  }

  public static Enchantment a() {
    if (ProCratesEnchantment.a != null) {
      return ProCratesEnchantment.a;
    }
    try {
      final Field declaredField = Enchantment.class.getDeclaredField("acceptingNew");
      declaredField.setAccessible(true);
      declaredField.set(null, true);
    } catch (Exception ex) {
    }
    ProCratesEnchantment.a = (Enchantment) new ProCratesEnchantment(255);
    if (Enchantment.getByName("PROCrates") == null && Enchantment.isAcceptingRegistrations()) {
      Enchantment.registerEnchantment(ProCratesEnchantment.a);
    }
    return ProCratesEnchantment.a;
  }

  public boolean canEnchantItem(final ItemStack itemStack) {
    return true;
  }

  public int getMaxLevel() {
    return 1;
  }

  public String getName() {
    return "PROCrates";
  }

  public int getStartLevel() {
    return 1;
  }
}