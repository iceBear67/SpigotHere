package io.ib67;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class SpigotHere extends JavaPlugin {
    private boolean enabled;
    private String motd;
    @Override
    public void onEnable() {
        getDataFolder().mkdirs();
        saveDefaultConfig();
        reloadConfig();
        motd = getConfig().getString("motd");
        if(motd.isEmpty()){
            motd = Bukkit.getMotd();
        }
        enabled = getConfig().getBoolean("enable");
        if(!enabled){
            getLogger().info("Not enabled from config.");
            return;
        }
        if(Bukkit.getIp().equals("127.0.0.1")){
            getLogger().warning("It seems that you've set your server's IP to 127.0.0.1, which means other people CAN'T join your server.");
        }
        motd = ChatColor.translateAlternateColorCodes('&', motd);
        DiscoverMe.INSTANCE.removeEntry(new ServerEntry(motd, null, Bukkit.getPort())); // for hot-reloading
        DiscoverMe.INSTANCE.addEntry(motd, Bukkit.getPort());
        getLogger().info("Service is running!");
    }
}
