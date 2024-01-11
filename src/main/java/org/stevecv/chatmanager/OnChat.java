package org.stevecv.chatmanager;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class OnChat implements Listener {
    @EventHandler
    public void onChat(PlayerChatEvent e) {
        Player p = e.getPlayer();

        TextComponent nameInfo = new TextComponent(Formatter.getPrefix(p) + p.getDisplayName() + "§8 » §f");
        nameInfo.addExtra(Formatter.format(e.getMessage()));

        Bukkit.broadcast(nameInfo);
    }
}
