package mwg.plugin.mwgshop;

import com.codingforcookies.armorequip.ArmorEquipEvent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Random;

public class Shop implements CommandExecutor, Listener {
    private final Plugin plugin;
    private NganXa nx = new NganXa();
    private KimXa kx = new KimXa();

    public Shop(Plugin plugin){
        this.plugin = plugin;
    }
    // command thuc thi
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            //cmd đổi tiền lớn thành tiền bé
        if(cmd.getName().equals("doitien")){
            if(sender instanceof Player){
                Player player = (Player) sender;
                if(player.hasPermission("mwg.doitien")){
                    if (args.length > 0) {
                        if (args[0].equalsIgnoreCase("crystal")) {
                            if (player.getInventory().containsAtLeast(nx.giveNganXa(player.getName()), 64)) {
                                for (int i = 0; i < 64; i++) {
                                    player.getInventory().removeItem(nx.giveNganXa(player.getName()));
                                }
                                player.getInventory().addItem(kx.giveKimXa(player.getName()));
                                player.sendMessage(ChatColor.GREEN + "Đổi tiền thành công!");
                            }else {
                                player.sendMessage(ChatColor.RED+"Bạn không có đủ số lượng tiền tối thiểu để thực hiện lệnh này!");
                            }
                        } else if (args[0].equalsIgnoreCase("crystalblock")) {
                            if(player.getInventory().containsAtLeast(kx.giveKimXa(player.getName()), 1)) {
                                player.getInventory().removeItem(kx.giveKimXa(player.getName()));
                                for (int i = 0; i < 64; i++) {
                                    player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                }
                                player.sendMessage(ChatColor.GREEN + "Đổi tiền thành công!");
                            }else{
                                player.sendMessage(ChatColor.RED+"Bạn không có đủ số lượng tiền tối thiểu để thực hiện lệnh này!");
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "Bạn nhập sai cú pháp hoặc loại tiền đó không tồn tại!");
                            player.sendMessage(ChatColor.GREEN + "Các lệnh có thể dùng: ");
                            player.sendMessage(ChatColor.AQUA + "/doitien crystal");
                            player.sendMessage(ChatColor.AQUA + "/doitien crystalblock");
                        }
                    }else{
                        player.sendMessage(ChatColor.GREEN + "Các lệnh có thể dùng: ");
                        player.sendMessage(ChatColor.AQUA + "/doitien crystal");
                        player.sendMessage(ChatColor.AQUA + "/doitien crystalblock");
                    }
                }else{
                    player.sendMessage(ChatColor.RED+"Bạn không có quyền dùng lệnh này!");
                }
            }else{
                System.out.println("Bạn phải là người chơi để thực hiện lệnh này!");
            }
        }


        //cmd lấy money
        if(cmd.getName().equals("getmoney")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (player.hasPermission("mwg.admin.getmoney")) {
                    if (args.length > 0) {
                        if(args[0].equalsIgnoreCase("crystal")){
                            try {
                                if (!args[1].isEmpty()) {
                                    try {
                                        int amount = Integer.parseInt(args[1]);
                                        if (amount <= 999) {
                                            for (int i = 1; i <= amount; i++) {
                                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                            }
                                            player.sendMessage(ChatColor.GREEN + "+" + amount + " Crystal");
                                        } else {
                                            player.sendMessage(ChatColor.RED + "Bạn nhập số quá lớn, só lượng tiền tối đa là 999");
                                        }
                                    } catch (Exception exception) {
                                        player.sendMessage(ChatColor.RED + "Bạn nhập sai cú pháp, số lượng phải là 1 số nguyên");
                                    }
                                }
                            }catch (ArrayIndexOutOfBoundsException a){
                                player.sendMessage(ChatColor.RED + "Bạn nhập sai cú pháp! /getmoney <loại tiền> <số lượng>");
                            }
                        }else if(args[0].equalsIgnoreCase("crystalblock")){
                            try {

                                if (!args[1].isEmpty()) {
                                    try {
                                        int amount = Integer.parseInt(args[1]);
                                        if (amount <= 999) {
                                            for (int i = 1; i <= amount; i++) {
                                                player.getInventory().addItem(kx.giveKimXa(player.getName()));
                                            }
                                            player.sendMessage(ChatColor.GREEN + "+" + amount + " Block Of Crystal");
                                        } else {
                                            player.sendMessage(ChatColor.RED + "Bạn nhập số quá lớn, só lượng tiền tối đa là 999");
                                        }
                                    } catch (Exception exception) {
                                        player.sendMessage(ChatColor.RED + "Bạn nhập sai cú pháp, số lượng phải là 1 số nguyên");
                                    }
                                }
                            }catch (ArrayIndexOutOfBoundsException a){
                                player.sendMessage(ChatColor.RED + "Bạn nhập sai cú pháp! /getmoney <loại tiền> <số lượng>");
                            }
                        }else{
                            player.sendMessage(ChatColor.RED+"Bạn nhập sai cú pháp hoặc loại tiền đó không tồn tại!");
                            player.sendMessage(ChatColor.GREEN + "Các lệnh có thể dùng: ");
                            player.sendMessage(ChatColor.AQUA + "/getmoney crystal <số lượng>");
                            player.sendMessage(ChatColor.AQUA + "/getmoney crystalblock <số lượng>");
                        }
                    } else {
                        player.sendMessage(ChatColor.GREEN + "Các lệnh có thể dùng: ");
                        player.sendMessage(ChatColor.AQUA + "/getmoney crystal <số lượng");
                        player.sendMessage(ChatColor.AQUA + "/getmoney crystalblock <số lượng>");
                    }
                }else {
                    player.sendMessage(ChatColor.RED+"Bạn không có quyền dùng lệnh này!");
                }
            } else {
                    System.out.println("Bạn phải là người chơi để thực hiện lệnh này!");
            }
        }



        //cmd bán item
        if(cmd.getName().equals("banitem")){
            if(sender instanceof Player){
                Player player = (Player) sender;
                if(player.hasPermission("mwg.banitem")){
                    GUIShop inv = new GUIShop();
                    inv.newInventory(player);
                }else{
                    player.sendMessage(ChatColor.RED+"Bạn không có quyền dùng lệnh này!");
                }
            }else{
                System.out.println("Bạn phải là người chơi để thực hiện lệnh này!");
            }
        }
        return false;

    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Player p = event.getEntity().getKiller();
        if (event.getEntity().getKiller() instanceof Player) {
            Random random = new Random();
            int chance = random.nextInt(100) + 1;

            if(chance < 70 && chance >= 30){
                p.getInventory().addItem(nx.giveNganXa(p.getName()));
                p.sendMessage(ChatColor.GREEN+"+1 Crystal");
            }else if(chance < 30 && chance >= 25) {
                for (int i = 0; i < 2; i++) {
                    p.getInventory().addItem(nx.giveNganXa(p.getName()));
                }
                p.sendMessage(ChatColor.GREEN + "+2 Crystal");
            }
        }
    }


    @EventHandler
    public void onHit(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof Player){
            Player p = (Player) e.getDamager();
            ItemStack handItem = p.getInventory().getItemInMainHand();
            if(handItem.hasItemMeta()) {
                List<String> lore = handItem.getItemMeta().getLore();
                if (lore.contains("§8§l?")) {
                    e.setCancelled(true);
                    p.sendMessage(ChatColor.RED + "Bạn cần giám định item mới có thể sử dụng!");
                }
            }
        }
    }

    @EventHandler
    public void onUse(PlayerInteractEvent e){
        Player p = e.getPlayer();
        try {
            if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.LEFT_CLICK_BLOCK) ||
                    e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                ItemStack offHandItem = p.getInventory().getItemInOffHand();
                ItemStack mainHandItem = p.getInventory().getItemInMainHand();
                if (offHandItem.hasItemMeta() && !offHandItem.equals(null)) {
                    List<String> lore = offHandItem.getItemMeta().getLore();
                    if (lore.contains("§8§l?")) {
                        e.setCancelled(true);
                        p.sendMessage(ChatColor.RED + "Bạn cần giám định item mới có thể sử dụng!");
                    }
                } else if (mainHandItem.hasItemMeta() && !mainHandItem.equals(null)) {
                    List<String> lore = mainHandItem.getItemMeta().getLore();
                    if (lore.contains("§8§l?")) {
                        e.setCancelled(true);
                        p.sendMessage(ChatColor.RED + "Bạn cần giám định item mới có thể sử dụng!");
                    }
                }
            }
        }catch (Exception exception){
            return;
        }
    }

    @EventHandler
    public void onArmorEquip (ArmorEquipEvent e){
        Player p = e.getPlayer();
        try {
            ItemStack armorPiece = e.getNewArmorPiece();
            if (armorPiece.hasItemMeta()) {
                List<String> lore = armorPiece.getItemMeta().getLore();
                if (lore.contains("§8§l?")) {
                    e.setCancelled(true);
                    p.sendMessage(ChatColor.RED + "Bạn cần giám định item mới có thể sử dụng!");
                }
            }
        }catch (Exception ex){
            return;
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();
        Inventory openInventory = e.getClickedInventory();
        if(openInventory == null){
            return;
        }
        ItemStack item = e.getCurrentItem();

        if(openInventory.getName().equals("§8§lShop Bán Item")){
            if(item.equals(null) || !item.hasItemMeta()){
                return;
            }
            if(e.getSlot() == 8){
                e.setCancelled(true);
                player.closeInventory();
            }else if (e.getSlot() == 4){
                ItemStack slotItem = e.getClickedInventory().getItem(4);
                try {
                    List<String> lore = slotItem.getItemMeta().getLore();
                    if(lore.contains("§b§lCOMMON ITEM")) {
                        if (lore.contains("§fCấp: 1") || lore.contains("§fCấp: 2")) {
                            player.getInventory().addItem(nx.giveNganXa(player.getName()));
                            
                        } else if (lore.contains("§fCấp: 3") || lore.contains("§fCấp: 4")) {
                            for (int i = 0; i < 2; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        } else if (lore.contains("§fCấp: 5") || lore.contains("§fCấp: 6")) {
                            for (int i = 0; i < 3; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        } else if (lore.contains("§fCấp: 7") || lore.contains("§fCấp: 8")) {
                            for (int i = 0; i < 4; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        } else if (lore.contains("§fCấp: 9") || lore.contains("§fCấp: 10")) {
                            for (int i = 0; i < 5; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        } else if (lore.contains("§fCấp: 11") || lore.contains("§fCấp: 12")) {
                            for (int i = 0; i < 6; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        } else if (lore.contains("§fCấp: 13") || lore.contains("§fCấp: 14")) {
                            for (int i = 0; i < 7; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        } else if (lore.contains("§fCấp: 15") || lore.contains("§fCấp: 16")) {
                            for (int i = 0; i < 8; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        } else if (lore.contains("§fCấp: 17") || lore.contains("§fCấp: 18")) {
                            for (int i = 0; i < 9; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        } else if (lore.contains("§fCấp: 19") || lore.contains("§fCấp: 20")) {
                            for (int i = 0; i < 10; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        } else if (lore.contains("§fCấp: 21") || lore.contains("§fCấp: 22")) {
                            for (int i = 0; i < 11; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        } else if (lore.contains("§fCấp: 23") || lore.contains("§fCấp: 24")) {
                            for (int i = 0; i < 12; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        } else if (lore.contains("§fCấp: 25") || lore.contains("§fCấp: 26")) {
                            for (int i = 0; i < 13; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        } else if (lore.contains("§fCấp: 27") || lore.contains("§fCấp: 28")) {
                            for (int i = 0; i < 14; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        } else if (lore.contains("§fCấp: 29") || lore.contains("§fCấp: 30")) {
                            for (int i = 0; i < 15; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        } else if (lore.contains("§fCấp: 31") || lore.contains("§fCấp: 32")) {
                            for (int i = 0; i < 16; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        } else if (lore.contains("§fCấp: 33") || lore.contains("§fCấp: 34")) {
                            for (int i = 0; i < 17; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        } else if (lore.contains("§fCấp: 35") || lore.contains("§fCấp: 36")) {
                            for (int i = 0; i < 18; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        } else if (lore.contains("§fCấp: 37") || lore.contains("§fCấp: 38")) {
                            for (int i = 0; i < 19; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        } else if (lore.contains("§fCấp: 39") || lore.contains("§fCấp: 40")) {
                            for (int i = 0; i < 20; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        } else if (lore.contains("§fCấp: 41") || lore.contains("§fCấp: 42")) {
                            for (int i = 0; i < 21; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        } else if (lore.contains("§fCấp: 43") || lore.contains("§fCấp: 44")) {
                            for (int i = 0; i < 22; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        } else if (lore.contains("§fCấp: 45") || lore.contains("§fCấp: 46")) {
                            for (int i = 0; i < 23; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        } else if (lore.contains("§fCấp: 47") || lore.contains("§fCấp: 48")) {
                            for (int i = 0; i < 24; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        } else if (lore.contains("§fCấp: 49") || lore.contains("§fCấp: 50")) {
                            for (int i = 0; i < 25; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        } else if (lore.contains("§fCấp: 51") || lore.contains("§fCấp: 52")) {
                            for (int i = 0; i < 26; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        } else if (lore.contains("§fCấp: 53") || lore.contains("§fCấp: 54")) {
                            for (int i = 0; i < 27; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        } else if (lore.contains("§fCấp: 55") || lore.contains("§fCấp: 56")) {
                            for (int i = 0; i < 28; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        } else if (lore.contains("§fCấp: 57") || lore.contains("§fCấp: 58")) {
                            for (int i = 0; i < 29; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        } else if (lore.contains("§fCấp: 59") || lore.contains("§fCấp: 60")) {
                            for (int i = 0; i < 30; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        } else if (lore.contains("§fCấp: 61") || lore.contains("§fCấp: 62")) {
                            for (int i = 0; i < 31; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        } else if (lore.contains("§fCấp: 63") || lore.contains("§fCấp: 64")) {
                            for (int i = 0; i < 32; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        } else if (lore.contains("§fCấp: 65") || lore.contains("§fCấp: 66")) {
                            for (int i = 0; i < 33; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        } else if (lore.contains("§fCấp: 67") || lore.contains("§fCấp: 68")) {
                            for (int i = 0; i < 34; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        } else if (lore.contains("§fCấp: 69") || lore.contains("§fCấp: 70")) {
                            for (int i = 0; i < 35; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        } else if (lore.contains("§fCấp: 71") || lore.contains("§fCấp: 72")) {
                            for (int i = 0; i < 36; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        } else if (lore.contains("§fCấp: 73") || lore.contains("§fCấp: 74")) {
                            for (int i = 0; i < 37; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        } else if (lore.contains("§fCấp: 75") || lore.contains("§fCấp: 76")) {
                            for (int i = 0; i < 38; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        } else if (lore.contains("§fCấp: 77") || lore.contains("§fCấp: 78")) {
                            for (int i = 0; i < 39; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        } else if (lore.contains("§fCấp: 79") || lore.contains("§fCấp: 80")) {
                            for (int i = 0; i < 40; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        } else if (lore.contains("§fCấp: 81") || lore.contains("§fCấp: 82")) {
                            for (int i = 0; i < 41; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        } else if (lore.contains("§fCấp: 83") || lore.contains("§fCấp: 84")) {
                            for (int i = 0; i < 42; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        } else if (lore.contains("§fCấp: 85") || lore.contains("§fCấp: 86")) {
                            for (int i = 0; i < 43; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        } else if (lore.contains("§fCấp: 87") || lore.contains("§fCấp: 88")) {
                            for (int i = 0; i < 44; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        } else if (lore.contains("§fCấp: 89") || lore.contains("§fCấp: 90")) {
                            for (int i = 0; i < 45; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        } else if (lore.contains("§fCấp: 91") || lore.contains("§fCấp: 92")) {
                            for (int i = 0; i < 46; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        } else if (lore.contains("§fCấp: 93") || lore.contains("§fCấp: 94")) {
                            for (int i = 0; i < 47; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        } else if (lore.contains("§fCấp: 95") || lore.contains("§fCấp: 96")) {
                            for (int i = 0; i < 48; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        } else if (lore.contains("§fCấp: 97") || lore.contains("§fCấp: 98")) {
                            for (int i = 0; i < 49; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        } else if (lore.contains("§fCấp: 99") || lore.contains("§fCấp: 100")) {
                            for (int i = 0; i < 50; i++) {
                                player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                
                            }
                        }
                        player.sendMessage(ChatColor.GREEN+"Bán thành vật phẩm thành công!");
                        player.updateInventory();
                        player.closeInventory();
                        GUIShop inv = new GUIShop();
                        inv.newInventory(player);
                    }else if(lore.contains("§a§lUNCOMMON ITEM")) {
                                if (lore.contains("§fCấp: 1") || lore.contains("§fCấp: 2")) {
                                    for (int i = 0; i < 3; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 3") || lore.contains("§fCấp: 4")) {
                                    for (int i = 0; i < 4; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 5") || lore.contains("§fCấp: 6")) {
                                    for (int i = 0; i < 5; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 7") || lore.contains("§fCấp: 8")) {
                                    for (int i = 0; i < 6; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 9") || lore.contains("§fCấp: 10")) {
                                    for (int i = 0; i < 7; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 11") || lore.contains("§fCấp: 12")) {
                                    for (int i = 0; i < 8; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 13") || lore.contains("§fCấp: 14")) {
                                    for (int i = 0; i < 9; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 15") || lore.contains("§fCấp: 16")) {
                                    for (int i = 0; i < 10; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 17") || lore.contains("§fCấp: 18")) {
                                    for (int i = 0; i < 11; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 19") || lore.contains("§fCấp: 20")) {
                                    for (int i = 0; i < 12; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 21") || lore.contains("§fCấp: 22")) {
                                    for (int i = 0; i < 13; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 23") || lore.contains("§fCấp: 24")) {

                                    for (int i = 0; i < 14; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 25") || lore.contains("§fCấp: 26")) {

                                    for (int i = 0; i < 15; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 27") || lore.contains("§fCấp: 28")) {
                                    for (int i = 0; i < 16; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 29") || lore.contains("§fCấp: 30")) {
                                    for (int i = 0; i < 17; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 31") || lore.contains("§fCấp: 32")) {
                                    for (int i = 0; i < 18; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 33") || lore.contains("§fCấp: 34")) {
                                    for (int i = 0; i < 19; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 35") || lore.contains("§fCấp: 36")) {
                                    for (int i = 0; i < 20; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 37") || lore.contains("§fCấp: 38")) {
                                    for (int i = 0; i < 21; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 39") || lore.contains("§fCấp: 40")) {
                                    for (int i = 0; i < 22; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 41") || lore.contains("§fCấp: 42")) {
                                    for (int i = 0; i < 23; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 43") || lore.contains("§fCấp: 44")) {
                                    for (int i = 0; i < 24; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 45") || lore.contains("§fCấp: 46")) {
                                    for (int i = 0; i < 25; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 47") || lore.contains("§fCấp: 48")) {
                                    for (int i = 0; i < 26; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 49") || lore.contains("§fCấp: 50")) {
                                    for (int i = 0; i < 27; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 51") || lore.contains("§fCấp: 52")) {
                                    for (int i = 0; i < 28; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 53") || lore.contains("§fCấp: 54")) {
                                    for (int i = 0; i < 29; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 55") || lore.contains("§fCấp: 56")) {
                                    for (int i = 0; i < 30; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 57") || lore.contains("§fCấp: 58")) {
                                    for (int i = 0; i < 31; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 59") || lore.contains("§fCấp: 60")) {
                                    for (int i = 0; i < 32; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 61") || lore.contains("§fCấp: 62")) {
                                    for (int i = 0; i < 33; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 63") || lore.contains("§fCấp: 64")) {
                                    for (int i = 0; i < 34; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 65") || lore.contains("§fCấp: 66")) {
                                    for (int i = 0; i < 35; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 67") || lore.contains("§fCấp: 68")) {
                                    for (int i = 0; i < 36; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 69") || lore.contains("§fCấp: 70")) {
                                    for (int i = 0; i < 37; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 71") || lore.contains("§fCấp: 72")) {
                                    for (int i = 0; i < 38; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 73") || lore.contains("§fCấp: 74")) {
                                    for (int i = 0; i < 39; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 75") || lore.contains("§fCấp: 76")) {
                                    for (int i = 0; i < 40; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 77") || lore.contains("§fCấp: 78")) {
                                    for (int i = 0; i < 41; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 79") || lore.contains("§fCấp: 80")) {
                                    for (int i = 0; i < 42; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 81") || lore.contains("§fCấp: 82")) {
                                    for (int i = 0; i < 43; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 83") || lore.contains("§fCấp: 84")) {
                                    for (int i = 0; i < 44; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 85") || lore.contains("§fCấp: 86")) {
                                    for (int i = 0; i < 45; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 87") || lore.contains("§fCấp: 88")) {
                                    for (int i = 0; i < 46; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 89") || lore.contains("§fCấp: 90")) {
                                    for (int i = 0; i < 47; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 91") || lore.contains("§fCấp: 92")) {
                                    for (int i = 0; i < 48; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 93") || lore.contains("§fCấp: 94")) {
                                    for (int i = 0; i < 49; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 95") || lore.contains("§fCấp: 96")) {
                                    for (int i = 0; i < 50; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 97") || lore.contains("§fCấp: 98")) {
                                    for (int i = 0; i < 51; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 99") || lore.contains("§fCấp: 100")) {
                                    for (int i = 0; i < 52; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                }
                                player.sendMessage(ChatColor.GREEN + "Bán vật phẩm thành công!");
                                player.updateInventory();
                        player.closeInventory();
                        GUIShop inv = new GUIShop();
                        inv.newInventory(player);
                    }else if(lore.contains("§9§lRARE ITEM")) {
                                player.getInventory().removeItem(player.getInventory().getItemInMainHand());
                                if (lore.contains("§fCấp: 1") || lore.contains("§fCấp: 2")) {
                                    for (int i = 0; i < 5; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 3") || lore.contains("§fCấp: 4")) {
                                    for (int i = 0; i < 6; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 5") || lore.contains("§fCấp: 6")) {
                                    for (int i = 0; i < 7; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 7") || lore.contains("§fCấp: 8")) {
                                    for (int i = 0; i < 8; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 9") || lore.contains("§fCấp: 10")) {
                                    for (int i = 0; i < 9; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 11") || lore.contains("§fCấp: 12")) {
                                    for (int i = 0; i < 10; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 13") || lore.contains("§fCấp: 14")) {
                                    for (int i = 0; i < 11; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 15") || lore.contains("§fCấp: 16")) {
                                    for (int i = 0; i < 12; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 17") || lore.contains("§fCấp: 18")) {
                                    for (int i = 0; i < 13; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 19") || lore.contains("§fCấp: 20")) {
                                    for (int i = 0; i < 14; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 21") || lore.contains("§fCấp: 22")) {
                                    for (int i = 0; i < 15; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 23") || lore.contains("§fCấp: 24")) {

                                    for (int i = 0; i < 16; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 25") || lore.contains("§fCấp: 26")) {

                                    for (int i = 0; i < 17; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 27") || lore.contains("§fCấp: 28")) {
                                    for (int i = 0; i < 18; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 29") || lore.contains("§fCấp: 30")) {
                                    for (int i = 0; i < 19; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 31") || lore.contains("§fCấp: 32")) {
                                    for (int i = 0; i < 20; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 33") || lore.contains("§fCấp: 34")) {
                                    for (int i = 0; i < 21; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 35") || lore.contains("§fCấp: 36")) {
                                    for (int i = 0; i < 22; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 37") || lore.contains("§fCấp: 38")) {
                                    for (int i = 0; i < 23; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 39") || lore.contains("§fCấp: 40")) {
                                    for (int i = 0; i < 24; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 41") || lore.contains("§fCấp: 42")) {
                                    for (int i = 0; i < 25; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 43") || lore.contains("§fCấp: 44")) {
                                    for (int i = 0; i < 26; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 45") || lore.contains("§fCấp: 46")) {
                                    for (int i = 0; i < 27; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 47") || lore.contains("§fCấp: 48")) {
                                    for (int i = 0; i < 28; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 49") || lore.contains("§fCấp: 50")) {
                                    for (int i = 0; i < 29; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 51") || lore.contains("§fCấp: 52")) {
                                    for (int i = 0; i < 30; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 53") || lore.contains("§fCấp: 54")) {
                                    for (int i = 0; i < 31; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 55") || lore.contains("§fCấp: 56")) {
                                    for (int i = 0; i < 32; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 57") || lore.contains("§fCấp: 58")) {
                                    for (int i = 0; i < 33; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 59") || lore.contains("§fCấp: 60")) {
                                    for (int i = 0; i < 34; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 61") || lore.contains("§fCấp: 62")) {
                                    for (int i = 0; i < 35; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 63") || lore.contains("§fCấp: 64")) {
                                    for (int i = 0; i < 36; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 65") || lore.contains("§fCấp: 66")) {
                                    for (int i = 0; i < 37; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 67") || lore.contains("§fCấp: 68")) {
                                    for (int i = 0; i < 38; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 69") || lore.contains("§fCấp: 70")) {
                                    for (int i = 0; i < 39; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 71") || lore.contains("§fCấp: 72")) {
                                    for (int i = 0; i < 40; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 73") || lore.contains("§fCấp: 74")) {
                                    for (int i = 0; i < 41; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 75") || lore.contains("§fCấp: 76")) {
                                    for (int i = 0; i < 42; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 77") || lore.contains("§fCấp: 78")) {
                                    for (int i = 0; i < 43; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 79") || lore.contains("§fCấp: 80")) {
                                    for (int i = 0; i < 44; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 81") || lore.contains("§fCấp: 82")) {
                                    for (int i = 0; i < 45; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 83") || lore.contains("§fCấp: 84")) {
                                    for (int i = 0; i < 46; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 85") || lore.contains("§fCấp: 86")) {
                                    for (int i = 0; i < 47; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 87") || lore.contains("§fCấp: 88")) {
                                    for (int i = 0; i < 48; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 89") || lore.contains("§fCấp: 90")) {
                                    for (int i = 0; i < 49; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 91") || lore.contains("§fCấp: 92")) {
                                    for (int i = 0; i < 50; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 93") || lore.contains("§fCấp: 94")) {
                                    for (int i = 0; i < 51; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 95") || lore.contains("§fCấp: 96")) {
                                    for (int i = 0; i < 52; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 97") || lore.contains("§fCấp: 98")) {
                                    for (int i = 0; i < 53; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 99") || lore.contains("§fCấp: 100")) {
                                    for (int i = 0; i < 54; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                }
                                player.sendMessage(ChatColor.GREEN + "Bán vật phẩm thành công!");
                                player.updateInventory();
                        player.closeInventory();
                        GUIShop inv = new GUIShop();
                        inv.newInventory(player);
                    }else if(lore.contains("§1§lEPIC ITEM")) {
                                player.getInventory().removeItem(player.getInventory().getItemInMainHand());
                                if (lore.contains("§fCấp: 1") || lore.contains("§fCấp: 2")) {
                                    for (int i = 0; i < 7; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 3") || lore.contains("§fCấp: 4")) {
                                    for (int i = 0; i < 8; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 5") || lore.contains("§fCấp: 6")) {
                                    for (int i = 0; i < 9; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 7") || lore.contains("§fCấp: 8")) {
                                    for (int i = 0; i < 10; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 9") || lore.contains("§fCấp: 10")) {
                                    for (int i = 0; i < 11; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 11") || lore.contains("§fCấp: 12")) {
                                    for (int i = 0; i < 12; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 13") || lore.contains("§fCấp: 14")) {
                                    for (int i = 0; i < 13; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 15") || lore.contains("§fCấp: 16")) {
                                    for (int i = 0; i < 14; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 17") || lore.contains("§fCấp: 18")) {
                                    for (int i = 0; i < 15; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 19") || lore.contains("§fCấp: 20")) {
                                    for (int i = 0; i < 16; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 21") || lore.contains("§fCấp: 22")) {
                                    for (int i = 0; i < 17; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 23") || lore.contains("§fCấp: 24")) {
                                    for (int i = 0; i < 18; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 25") || lore.contains("§fCấp: 26")) {
                                    for (int i = 0; i < 19; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 27") || lore.contains("§fCấp: 28")) {
                                    for (int i = 0; i < 20; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 29") || lore.contains("§fCấp: 30")) {
                                    for (int i = 0; i < 21; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 31") || lore.contains("§fCấp: 32")) {
                                    for (int i = 0; i < 22; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 33") || lore.contains("§fCấp: 34")) {
                                    for (int i = 0; i < 23; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 35") || lore.contains("§fCấp: 36")) {
                                    for (int i = 0; i < 24; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 37") || lore.contains("§fCấp: 38")) {
                                    for (int i = 0; i < 25; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 39") || lore.contains("§fCấp: 40")) {
                                    for (int i = 0; i < 26; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 41") || lore.contains("§fCấp: 42")) {
                                    for (int i = 0; i < 27; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 43") || lore.contains("§fCấp: 44")) {
                                    for (int i = 0; i < 28; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 45") || lore.contains("§fCấp: 46")) {
                                    for (int i = 0; i < 29; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 47") || lore.contains("§fCấp: 48")) {
                                    for (int i = 0; i < 30; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 49") || lore.contains("§fCấp: 50")) {
                                    for (int i = 0; i < 31; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 51") || lore.contains("§fCấp: 52")) {
                                    for (int i = 0; i < 32; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 53") || lore.contains("§fCấp: 54")) {
                                    for (int i = 0; i < 33; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 55") || lore.contains("§fCấp: 56")) {
                                    for (int i = 0; i < 34; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 57") || lore.contains("§fCấp: 58")) {
                                    for (int i = 0; i < 35; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 59") || lore.contains("§fCấp: 60")) {
                                    for (int i = 0; i < 36; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 61") || lore.contains("§fCấp: 62")) {
                                    for (int i = 0; i < 37; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 63") || lore.contains("§fCấp: 64")) {
                                    for (int i = 0; i < 38; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 65") || lore.contains("§fCấp: 66")) {
                                    for (int i = 0; i < 39; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 67") || lore.contains("§fCấp: 68")) {
                                    for (int i = 0; i < 40; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 69") || lore.contains("§fCấp: 70")) {
                                    for (int i = 0; i < 41; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 71") || lore.contains("§fCấp: 72")) {
                                    for (int i = 0; i < 42; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 73") || lore.contains("§fCấp: 74")) {
                                    for (int i = 0; i < 43; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 75") || lore.contains("§fCấp: 76")) {
                                    for (int i = 0; i < 44; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 77") || lore.contains("§fCấp: 78")) {
                                    for (int i = 0; i < 45; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 79") || lore.contains("§fCấp: 80")) {
                                    for (int i = 0; i < 46; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 81") || lore.contains("§fCấp: 82")) {
                                    for (int i = 0; i < 47; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 83") || lore.contains("§fCấp: 84")) {
                                    for (int i = 0; i < 48; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 85") || lore.contains("§fCấp: 86")) {
                                    for (int i = 0; i < 49; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 87") || lore.contains("§fCấp: 88")) {
                                    for (int i = 0; i < 50; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 89") || lore.contains("§fCấp: 90")) {
                                    for (int i = 0; i < 51; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 91") || lore.contains("§fCấp: 92")) {
                                    for (int i = 0; i < 52; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 93") || lore.contains("§fCấp: 94")) {
                                    for (int i = 0; i < 53; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 95") || lore.contains("§fCấp: 96")) {
                                    for (int i = 0; i < 54; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 97") || lore.contains("§fCấp: 98")) {
                                    for (int i = 0; i < 55; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 99") || lore.contains("§fCấp: 100")) {
                                    for (int i = 0; i < 56; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                }
                                player.sendMessage(ChatColor.GREEN+"Bán vật phẩm thành công!");
                                player.updateInventory();
                        player.closeInventory();
                        GUIShop inv = new GUIShop();
                        inv.newInventory(player);
                    }else if(lore.contains("§4§lMYTHICAL ITEM")) {
                                player.getInventory().removeItem(player.getInventory().getItemInMainHand());
                                if (lore.contains("§fCấp: 1") || lore.contains("§fCấp: 2")) {
                                    for (int i = 0; i < 9; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 3") || lore.contains("§fCấp: 4")) {
                                    for (int i = 0; i < 10; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 5") || lore.contains("§fCấp: 6")) {
                                    for (int i = 0; i < 11; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 7") || lore.contains("§fCấp: 8")) {
                                    for (int i = 0; i < 12; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                    }
                                } else if (lore.contains("§fCấp: 9") || lore.contains("§fCấp: 10")) {
                                    for (int i = 0; i < 13; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                    }
                                } else if (lore.contains("§fCấp: 11") || lore.contains("§fCấp: 12")) {
                                    for (int i = 0; i < 14; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 13") || lore.contains("§fCấp: 14")) {
                                    for (int i = 0; i < 15; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 15") || lore.contains("§fCấp: 16")) {
                                    for (int i = 0; i < 16; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 17") || lore.contains("§fCấp: 18")) {
                                    for (int i = 0; i < 17; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 19") || lore.contains("§fCấp: 20")) {
                                    for (int i = 0; i < 18; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 21") || lore.contains("§fCấp: 22")) {
                                    for (int i = 0; i < 19; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 23") || lore.contains("§fCấp: 24")) {

                                    for (int i = 0; i < 20; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 25") || lore.contains("§fCấp: 26")) {

                                    for (int i = 0; i < 21; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 27") || lore.contains("§fCấp: 28")) {
                                    for (int i = 0; i < 22; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 29") || lore.contains("§fCấp: 30")) {
                                    for (int i = 0; i < 23; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 31") || lore.contains("§fCấp: 32")) {
                                    for (int i = 0; i < 24; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 33") || lore.contains("§fCấp: 34")) {
                                    for (int i = 0; i < 25; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 35") || lore.contains("§fCấp: 36")) {
                                    for (int i = 0; i < 26; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 37") || lore.contains("§fCấp: 38")) {
                                    for (int i = 0; i < 27; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 39") || lore.contains("§fCấp: 40")) {
                                    for (int i = 0; i < 28; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 41") || lore.contains("§fCấp: 42")) {
                                    for (int i = 0; i < 29; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 43") || lore.contains("§fCấp: 44")) {
                                    for (int i = 0; i < 30; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 45") || lore.contains("§fCấp: 46")) {
                                    for (int i = 0; i < 31; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 47") || lore.contains("§fCấp: 48")) {
                                    for (int i = 0; i < 32; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 49") || lore.contains("§fCấp: 50")) {
                                    for (int i = 0; i < 33; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 51") || lore.contains("§fCấp: 52")) {
                                    for (int i = 0; i < 34; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 53") || lore.contains("§fCấp: 54")) {
                                    for (int i = 0; i < 35; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 55") || lore.contains("§fCấp: 56")) {
                                    for (int i = 0; i < 36; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 57") || lore.contains("§fCấp: 58")) {
                                    for (int i = 0; i < 37; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 59") || lore.contains("§fCấp: 60")) {
                                    for (int i = 0; i < 38; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 61") || lore.contains("§fCấp: 62")) {
                                    for (int i = 0; i < 39; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 63") || lore.contains("§fCấp: 64")) {
                                    for (int i = 0; i < 40; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 65") || lore.contains("§fCấp: 66")) {
                                    for (int i = 0; i < 41; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 67") || lore.contains("§fCấp: 68")) {
                                    for (int i = 0; i < 42; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 69") || lore.contains("§fCấp: 70")) {
                                    for (int i = 0; i < 43; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 71") || lore.contains("§fCấp: 72")) {
                                    for (int i = 0; i < 44; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 73") || lore.contains("§fCấp: 74")) {
                                    for (int i = 0; i < 45; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 75") || lore.contains("§fCấp: 76")) {
                                    for (int i = 0; i < 46; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 77") || lore.contains("§fCấp: 78")) {
                                    for (int i = 0; i < 47; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 79") || lore.contains("§fCấp: 80")) {
                                    for (int i = 0; i < 48; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 81") || lore.contains("§fCấp: 82")) {
                                    for (int i = 0; i < 49; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 83") || lore.contains("§fCấp: 84")) {
                                    for (int i = 0; i < 50; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 85") || lore.contains("§fCấp: 86")) {
                                    for (int i = 0; i < 51; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 87") || lore.contains("§fCấp: 88")) {
                                    for (int i = 0; i < 52; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 89") || lore.contains("§fCấp: 90")) {
                                    for (int i = 0; i < 53; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 91") || lore.contains("§fCấp: 92")) {
                                    for (int i = 0; i < 54; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 93") || lore.contains("§fCấp: 94")) {
                                    for (int i = 0; i < 55; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 95") || lore.contains("§fCấp: 96")) {
                                    for (int i = 0; i < 56; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 97") || lore.contains("§fCấp: 98")) {
                                    for (int i = 0; i < 57; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));
                                        
                                    }
                                } else if (lore.contains("§fCấp: 99") || lore.contains("§fCấp: 100")) {
                                    for (int i = 0; i < 58; i++) {
                                        player.getInventory().addItem(nx.giveNganXa(player.getName()));

                                    }
                                }
                                player.sendMessage(ChatColor.GREEN + "Bán vật phẩm thành công!");
                                player.updateInventory();
                        player.closeInventory();
                        GUIShop inv = new GUIShop();
                        inv.newInventory(player);
                    }else{
                        ItemStack cantSell = e.getCurrentItem();
                        player.sendMessage(ChatColor.RED+"Bạn không thể bán vật phẩm này!");
                        e.setCancelled(true);
                        player.closeInventory();
                        player.getInventory().addItem(cantSell);
                        GUIShop inv = new GUIShop();
                        inv.newInventory(player);
                    }
                }catch (Exception exception){
                    ItemStack cantSell = e.getCurrentItem();
                    player.sendMessage(ChatColor.RED+"Bạn không thể bán vật phẩm này!");
                    e.setCancelled(true);
                    player.closeInventory();
                    player.getInventory().addItem(cantSell);
                    GUIShop inv = new GUIShop();
                    inv.newInventory(player);
                }
            }else {
                e.setCancelled(true);
            }
        }
    }
}