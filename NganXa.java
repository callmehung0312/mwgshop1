package mwg.plugin.mwgshop;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class NganXa {
    public static ItemStack giveNganXa(String name){
        ItemStack emerald = new ItemStack(Material.EMERALD, 1);
        ItemMeta Emerald = emerald.getItemMeta();
        Emerald.setDisplayName(ChatColor.GREEN + "Crystal");
        Emerald.addEnchant(Enchantment.DURABILITY, 1,true);
        Emerald.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        List<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GRAY + " ");
        lore.add(ChatColor.GRAY + "Đơn vị tiền tệ");
        Emerald.setLore(lore);
        emerald.setItemMeta(Emerald);

        return emerald;
    }
}
