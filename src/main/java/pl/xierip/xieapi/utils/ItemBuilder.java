package pl.xierip.xieapi.utils;

/**
 * @author Xierip on 05.11.2015.
 */

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ItemBuilder implements Cloneable {
    private ItemStack is;

    public ItemBuilder(final Material m) {
        this(m, 1);
    }

    public ItemBuilder(final ItemStack is) {
        this.is = is.clone();
    }

    public ItemBuilder(final Material m, final int amount) {
        is = new ItemStack(m, amount);
    }

    public ItemBuilder addEnchant(final Enchantment ench, final int level) {
        final ItemMeta im = is.getItemMeta();
        im.addEnchant(ench, level, true);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addUnsafeEnchantment(final Enchantment ench, final int level) {
        is.addUnsafeEnchantment(ench, level);
        return this;
    }

    public ItemBuilder addUnsafeEnchantments(final Map<Enchantment, Integer> map) {
        for (final Map.Entry<Enchantment, Integer> entry : map.entrySet()) {
            is.addUnsafeEnchantment(entry.getKey(), entry.getValue());
        }
        return this;
    }

    @Override
    public ItemBuilder clone() {
        return new ItemBuilder(is);
    }

    public ItemBuilder removeEnchantment(final Enchantment ench) {
        is.removeEnchantment(ench);
        return this;
    }

    public ItemBuilder setAmount(final int amount) {
        is.setAmount(amount);
        return this;
    }

    public ItemBuilder setDurability(final short dur) {
        is.setDurability(dur);
        return this;
    }

    public ItemBuilder setInfinityDurability() {
        is.setDurability(Short.MAX_VALUE);
        return this;
    }

    public ItemBuilder setLeatherArmorColor(final Color color) {
        try {
            final LeatherArmorMeta im = (LeatherArmorMeta) is.getItemMeta();
            im.setColor(color);
            is.setItemMeta(im);
        } catch (final ClassCastException ignored) {
        }
        return this;
    }

    public ItemBuilder setLore(final String... lore) {
        final ItemMeta im = is.getItemMeta();
        im.setLore(StringUtil.fixColors(Arrays.asList(lore)));
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setLore(final List<String> lore) {
        final ItemMeta im = is.getItemMeta();
        im.setLore(StringUtil.fixColors(lore));
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setName(final String name) {
        final ItemMeta im = is.getItemMeta();
        im.setDisplayName(StringUtil.fixColors(name));
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setSkullOwner(final String owner) {
        try {
            final SkullMeta im = (SkullMeta) is.getItemMeta();
            im.setOwner(owner);
            is.setItemMeta(im);
        } catch (final ClassCastException ignored) {
        }
        return this;
    }

    public ItemStack toItemStack() {
        return is;
    }

}
