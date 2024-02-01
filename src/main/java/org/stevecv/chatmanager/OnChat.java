package org.stevecv.chatmanager;

import net.md_5.bungee.api.chat.TextComponent;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.text.Format;

public class OnChat implements Listener {
    @EventHandler
    public void onChat(PlayerChatEvent e) {
        Player p = e.getPlayer();

        TextComponent nameInfo = new TextComponent(Formatter.getPrefix(p));
        nameInfo.addExtra(Nickname.getNickname(p));
        nameInfo.addExtra("§8 » §f");
        nameInfo.addExtra(Formatter.format(e.getMessage()));

        TextComponent consoleSend = new TextComponent(Formatter.getPrefix(p));
        consoleSend.addExtra(Nickname.getNickname(p));
        consoleSend.addExtra("§8 » §f");
        consoleSend.addExtra(Formatter.formatNoSpoiler(e.getMessage()));
        Bukkit.getConsoleSender().sendMessage(consoleSend);

        for (Player s: Bukkit.getOnlinePlayers()) {
            s.sendMessage(nameInfo);
        }
        e.setCancelled(true);
    }
}
