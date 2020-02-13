package mwg.plugin.mwgshop;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class GUIShop implements Listener {
    private Plugin plugin = MWGSHOP.getPlugin(MWGSHOP.class);

    public void newInventory (Player player){
        Inventory i = plugin.getServer().createInventory(null, 9, "§8§lShop Bán Item");

        ItemStack empty = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15);
        ItemMeta emptyMeta = empty.getItemMeta();
        emptyMeta.setDisplayName(" ");
        empty.setItemMeta(emptyMeta);

        ItemStack tutorial = new ItemStack(Material.BOOK);
        ItemMeta tutorialMeta = tutorial.getItemMeta();
        tutorialMeta.setDisplayName(ChatColor.GOLD+"Hướng dẫn bán đồ");
        List<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GRAY + "> Bỏ item vào ô trống rồi click để bán!");
        lore.add(ChatColor.GRAY + "> "+ChatColor.RED+ "Cân nhắc kỹ trước khi bán vì 1 khi bán là không hoàn trả!");
        tutorialMeta.setLore(lore);
        tutorial.setItemMeta(tutorialMeta);

        ItemStack cancel = new ItemStack(Material.BARRIER);
        ItemMeta cancelMeta = cancel.getItemMeta();
        cancelMeta.setDisplayName(ChatColor.DARK_RED+"Thoát khỏi menu");
        cancel.setItemMeta(cancelMeta);

        i.setItem(0, tutorial);
        i.setItem(1, empty);
        i.setItem(2, empty);
        i.setItem(3, empty);
        i.setItem(5, empty);
        i.setItem(6, empty);
        i.setItem(7, empty);
        i.setItem(8, cancel);
        player.openInventory(i);
    }
}
