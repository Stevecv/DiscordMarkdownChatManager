package org.stevecv.chatmanager;

import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class ChatManager extends JavaPlugin {
    public static Chat chat = null;

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> chatProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
        if (chatProvider != null) {
            chat = chatProvider.getProvider();
        }

        return (chat != null);
    }

    @Override
    public void onEnable() {
        setupChat();
        Formatter formatter = new Formatter();

        formatter.registerFormatter("\\*\\*", "§l");
        formatter.registerFormatter("__", "§n");

        formatter.registerFormatter("\\*", "§o");
        formatter.registerFormatter("_", "§o");

        formatter.registerFormatter("~~", "§m");


        getServer().getPluginManager().registerEvents(new OnChat(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
