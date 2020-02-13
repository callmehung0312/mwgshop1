package mwg.plugin.mwgshop;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class MWGSHOP extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {

        this.getServer().getPluginManager().registerEvents(new Shop(this), this);

        //lệnh cho member
        this.getCommand("doitien").setExecutor(new Shop(this));

        //lệnh cho admin
        this.getCommand("getmoney").setExecutor(new Shop(this));

        //bán item
        this.getCommand("banitem").setExecutor(new Shop(this));
        //api mwgshop
        if(Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null){
            Bukkit.getPluginManager().registerEvents(this, this);
        }else{
           throw new RuntimeException("Không thể tìm thấy Plugin PlaceholderAPI, hãy chắc chắn bạn đã cài nó để dùng plugin này!");
        }
        System.out.println("Plugin đã bật!");
        System.out.println("Bản quyền thuộc về HungDZ");
    }

    @Override
    public void onDisable() {
        System.out.println("Plugin đã tắt!");
        System.out.println("Bản quyền thuộc về HungDZ");    }
}
