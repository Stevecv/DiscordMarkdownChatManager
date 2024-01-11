package org.stevecv.chatmanager;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ChatManager extends JavaPlugin {

    @Override
    public void onEnable() {
        Formatter formatter = new Formatter();

        formatter.registerFormatter("\\*", "§o");
        formatter.registerFormatter("\\_", "§o");

        formatter.registerFormatter("\\*\\*", "§l");

        formatter.registerFormatter("\\~~", "§m");
        formatter.registerFormatter("\\__", "§n");


        getServer().getPluginManager().registerEvents(new OnChat(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
