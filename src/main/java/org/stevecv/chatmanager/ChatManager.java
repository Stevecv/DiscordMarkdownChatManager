package org.stevecv.chatmanager;

import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

import static org.stevecv.chatmanager.Formatter.formattingElements;

public final class ChatManager extends JavaPlugin implements @NotNull Listener {
    public static Chat chat = null;

    public static ChatManager plugin = null;
    public ChatManager() {
        if(plugin != null) throw new RuntimeException("An instance of Main already exists!");
        plugin = this;
    }
    public static ChatManager getInstance() { return plugin; }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> chatProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
        if (chatProvider != null) {
            chat = chatProvider.getProvider();
        }
        return (chat != null);
    }

    File configFile = new File(getDataFolder() + File.separator + "config.yml");
    public static FileConfiguration config;

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(configFile);
        reloadRegistries();
    }


    public static void reloadRegistries() {
        formattingElements.clear();

        Formatter formatter = new Formatter();

        if (config.getBoolean("allowBold")) {
            formatter.registerFormatter("\\*\\*", "§l");
        }
        if (config.getBoolean("allowUnderline")) {
            formatter.registerFormatter("__", "§n");
        }

        if (config.getBoolean("allowItalics")) {
            formatter.registerFormatter("\\*", "§o");
            formatter.registerFormatter("_", "§o");
        }

        if (config.getBoolean("allowStrikeThrough")) {
            formatter.registerFormatter("~~", "§m");
        }
    }

    @Override
    public void onEnable() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            Bukkit.getPluginManager().registerEvents(this, this);
        } else {
            getLogger().warning("Could not find PlaceholderAPI! This plugin is required.");
            Bukkit.getPluginManager().disablePlugin(this);
        }


        setupChat();


        getServer().getPluginManager().registerEvents(new OnChat(), this);


        config = YamlConfiguration.loadConfiguration(configFile);
        if (!configFile.exists()) {
            try {
                configFile.createNewFile(); //This needs a try catch
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        if (!config.isSet("chatFormat")) config.set("chatFormat", "%essentials_nickname% §8 » §f %chat_message%");
        if (!config.isSet("consoleFormat")) config.set("consoleFormat", "%essentials_nickname% §8 » §f %chat_message%");

        if (!config.isSet("allowSpoilers")) config.set("allowSpoilers", true);
        if (!config.isSet("allowEssentialsColors")) config.set("allowEssentialsColors", false);
        if (!config.isSet("allowBold")) config.set("allowBold", true);
        if (!config.isSet("allowUnderline")) config.set("allowUnderline", true);
        if (!config.isSet("allowItalics")) config.set("allowItalics", true);
        if (!config.isSet("allowStrikeThrough")) config.set("allowStrikeThrough", true);

        reloadRegistries();

        this.getCommand("discordmarkdownchatmanager").setExecutor(new ChatManagerCommand());
    }

    @Override
    public @NotNull ComponentLogger getComponentLogger() {
        return super.getComponentLogger();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
