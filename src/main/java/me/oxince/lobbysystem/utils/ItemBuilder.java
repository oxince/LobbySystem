package me.oxince.lobbysystem.utils;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemBuilder {
    private final ItemStack itemStack;

    public ItemBuilder(Material material) {
        itemStack = new ItemStack(material, 1);
    }

    public ItemBuilder(Material material, int amount) {
        itemStack = new ItemStack(material, amount);
    }

    public ItemBuilder(Material material, int amount, short damage) {
        itemStack = new ItemStack(material, amount, damage);
    }

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemBuilder setName(String name, boolean... usePrefix) {
        name = (usePrefix != null && usePrefix.length >= 1) ? String.format("§8•§7● §6%s §7●§8•", name) : name;

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setDurability(short durability) {
        itemStack.setDurability(durability);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        itemStack.addEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder removeEnchantment(Enchantment enchantment) {
        itemStack.removeEnchantment(enchantment);
        return this;
    }

    public ItemBuilder setSkullOwner(String skullOwnerName) {
        try {
            SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
            skullMeta.setOwner(skullOwnerName);
            itemStack.setItemMeta(skullMeta);
        } catch (ClassCastException ignored) {
        }
        return this;
    }

    public ItemBuilder setInfiniteDurability() {
        itemStack.setDurability(Short.MAX_VALUE);
        return this;
    }

    public ItemBuilder setLeatherArmorColor(Color color) {
        try {
            LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemStack.getItemMeta();
            leatherArmorMeta.setColor(color);
            itemStack.setItemMeta(leatherArmorMeta);
        } catch (ClassCastException ignored) {
        }
        return this;
    }

    public static String getOriginItemName(String name) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            if (!Character.toString(name.charAt(i)).equals(String.valueOf('§'))) {
                result.append(name.charAt(i));
            } else {
                i++;
            }
        }
        String finalResult = result.toString().replaceAll("[^A-Za-z0-9 ]", "");
        return finalResult.substring(1, finalResult.length() - 1);
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }
}
