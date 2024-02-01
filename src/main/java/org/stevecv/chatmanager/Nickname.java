package org.stevecv.chatmanager;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class Nickname implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length > 0) {
            if (strings.length == 1 && commandSender instanceof Player) {
                try {
                    setNickname((Player) commandSender, strings[0]);
                    TextComponent msg = new TextComponent(ChatColor.GREEN + "Successfully set nickname to ");
                    msg.addExtra(Formatter.format(strings[0]));
                    commandSender.sendMessage(msg);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else if (strings.length == 2) {
                if (commandSender.hasPermission("chatmanager.changenickname")) {
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(strings[0]);
                    if (offlinePlayer.isOnline()) {
                        try {
                            setNickname((Player) offlinePlayer, strings[1]);
                            TextComponent msg = new TextComponent(ChatColor.GREEN + "Successfully set " + offlinePlayer.getName() + "'s nickname to ");
                            msg.addExtra(Formatter.format(strings[1]));
                            commandSender.sendMessage(msg);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                } else if (strings[1] != commandSender.getName()) {
                    commandSender.sendMessage("You don't have permission to set other players nicknames!");
                }
            } else {
                commandSender.sendMessage(ChatColor.RED + "A user must be specified!");
            }
        } else {
            if (commandSender instanceof Player) {
                Player p = (Player) commandSender;
                try {
                    setNickname(p, p.getName());
                    commandSender.sendMessage(ChatColor.GREEN + "Successfully reset nickname!");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                commandSender.sendMessage(ChatColor.RED + "Arguments must be provided!");
            }
        }
        return true;
    }

    public void setNickname(Player p, String name) {
        NamespacedKey key = new NamespacedKey(ChatManager.getInstance(), "chatmanager-nickname");
        PersistentDataContainer container = p.getPersistentDataContainer();
        container.set(key, PersistentDataType.STRING, name);
    }

    public static TextComponent getNickname(Player p) {
        NamespacedKey key = new NamespacedKey(ChatManager.getInstance(), "chatmanager-nickname");
        PersistentDataContainer container = p.getPersistentDataContainer();
        if(container.has(key, PersistentDataType.STRING)) {
            return Formatter.format(container.get(key, PersistentDataType.STRING));
        } else {
            return Formatter.format(p.getName());
        }
    }
}