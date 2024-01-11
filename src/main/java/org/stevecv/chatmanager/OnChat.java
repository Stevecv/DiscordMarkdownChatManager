package org.stevecv.chatmanager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class OnChat implements Listener {
    @EventHandler
    public void onChat(PlayerChatEvent e) {
        Player p = e.getPlayer();

        if (p.hasPermission("essentials.colour")) {
            e.setFormat(Formatter.getPrefix(p) + p.getDisplayName() + "§8 » §f" + Formatter.format(e.getMessage()));
        } else {
            e.setFormat(Formatter.getPrefix(p) + p.getDisplayName() + "§8 » §f" + Formatter.format(e.getMessage()));
        }
    }
}
