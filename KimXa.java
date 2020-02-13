package mwg.plugin.mwgshop;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class KimXa {
    public static ItemStack giveKimXa(String name){
        ItemStack emeraldBlock = new ItemStack(Material.EMERALD_BLOCK);
        ItemMeta Block = emeraldBlock.getItemMeta();
        Block.addEnchant(Enchantment.DURABILITY, 1,true);
        Block.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        Block.setDisplayName(ChatColor.YELLOW + "Block Of Crystal");
        List<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GRAY + " ");
        lore.add(ChatColor.GRAY + "Đơn vị tiền tệ");
        Block.setLore(lore);
        emeraldBlock.setItemMeta(Block);

        return emeraldBlock;
    }
}
