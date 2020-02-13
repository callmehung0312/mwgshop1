package mwg.plugin.mwgshop;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class APICrystal extends PlaceholderExpansion {
    private NganXa nx = new NganXa();
    private KimXa kx = new KimXa();


    @Override
    public boolean canRegister(){
        return true;
    }


    @Override
    public String getAuthor(){
        return "HungDZ";
    }

    @Override
    public String getIdentifier(){
        return "mwgshop";
    }

    @Override
    public String getVersion(){
        return "1.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, String identifier){
        Player p = Bukkit.getPlayer(player.getName());
        // %mwgshop_totalcrystal%
        if(identifier.equals("totalcrystal")){
            ItemStack[] item = p.getInventory().getStorageContents();
            int amount = 0;
            for(int i = 0; i < item.length; i++){
                if(item[i].getType() != Material.AIR && item[i].isSimilar(nx.giveNganXa(p.getName()))){
                    amount += item[i].getAmount();
                }
            }
            String stringAmount = String.valueOf(amount);
            return stringAmount;
        }

        // %mwgshop_totalcrystalblock%
        if(identifier.equals("totalcrystalblock")){
            ItemStack[] item = p.getInventory().getStorageContents();
            int amount = 0;
           // for(int i = 0; i < item.length; i++){
              //  if(item[i].getType() != Material.AIR && item[i].isSimilar(kx.giveKimXa(p.getName()))){
                //    amount += item[i].getAmount();
               // }
           // }
            String stringAmount = String.valueOf(amount);
            return "thot";
        }

        return null;
    }
}
