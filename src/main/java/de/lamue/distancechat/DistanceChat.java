package de.lamue.distancechat;

import de.lamue.distancechat.listeners.AsyncPlayerChat;
import de.lamue.distancechat.utils.ConfigurationManager;
import de.lamue.distancechat.utils.StringContent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class DistanceChat extends JavaPlugin {

    public static DistanceChat DISTANCECHAT;
    @Override
    public void onLoad() {
        Bukkit.getConsoleSender().sendMessage(StringContent.PREFIX+"Loading plugin...");
        ConfigurationManager.loadConfiguration();
    }

    @Override
    public void onEnable() {
        DISTANCECHAT = this;
        Bukkit.getPluginManager().registerEvents(new AsyncPlayerChat(), this);
        Bukkit.getConsoleSender().sendMessage(StringContent.PREFIX+"Plugin enabled!");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(StringContent.PREFIX+"Plugin disabled!");
    }
}
