package org.stevecv.chatmanager;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static org.stevecv.chatmanager.ChatManager.config;

public class ChatManagerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length > 0) {
            if (strings[0].equalsIgnoreCase("reload")) {
                ChatManager.getInstance().reloadConfig();
                commandSender.sendMessage(ChatColor.GREEN + "Successfully reloaded the DiscordMarkdownChatManager config");
            } else {
                commandSender.sendMessage(ChatColor.YELLOW + "------------[ " + ChatColor.GOLD + "DiscordMarkdownChatManager Help" + ChatColor.YELLOW + "]------------");
                commandSender.sendMessage(ChatColor.GOLD + "/dmcm reload" + ChatColor.YELLOW + " - Reload the DiscordMarkdownChatManager config");
                TextComponent message = new TextComponent(ChatColor.GOLD + "[Placeholder API list]");
                message.setClickEvent( new ClickEvent( ClickEvent.Action.OPEN_URL, "https://wiki.placeholderapi.com/users/placeholder-list/" ) );
                commandSender.sendMessage( message );
                commandSender.sendMessage(ChatColor.YELLOW + "-------------------------------------------------------------");
            }
        }
        return true;
    }
}
