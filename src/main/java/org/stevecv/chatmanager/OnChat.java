package org.stevecv.chatmanager;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.TextComponent;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.text.Format;

import static org.stevecv.chatmanager.ChatManager.config;

public class OnChat implements Listener {
    @EventHandler
    public void onChat(PlayerChatEvent e) {
        String messageString = PlaceholderAPI.setPlaceholders(e.getPlayer(), String.valueOf(config.get("chatFormat")));
        messageString.replace("%chat_message%", Formatter.format(e.getPlayer(), e.getMessage()).toPlainText());
        e.setMessage(messageString);

        String consoleMessageString = PlaceholderAPI.setPlaceholders(e.getPlayer(), String.valueOf(config.get("chatFormat")));
        consoleMessageString.replace("%chat_message%", Formatter.formatNoSpoiler(e.getPlayer(), e.getMessage()).toPlainText());
        Bukkit.getConsoleSender().sendMessage(consoleMessageString);
    }
}
