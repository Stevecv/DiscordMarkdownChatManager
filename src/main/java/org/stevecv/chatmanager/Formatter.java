package org.stevecv.chatmanager;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.stevecv.chatmanager.ChatManager.config;

public class Formatter {
    static ArrayList<FormatElement> formattingElements = new ArrayList<>();
    public void registerFormatter(String character, String replace) {
        formattingElements.add(new FormatElement(character, replace));
    }

    public static String color(Player player, String text) {
        String codesString = "";

        if (player.hasPermission("dmcm.colors")) {
            codesString += "0123456789abcdef";
        }
        if (player.hasPermission("dmcm.magic")) {
            codesString += "k";
        }
        if (player.hasPermission("dmcm.strikethrough")) {
            codesString += "m";
        }
        if (player.hasPermission("dmcm.underline")) {
            codesString += "n";
        }
        if (player.hasPermission("dmcm.italic")) {
            codesString += "o";
        }
        if (player.hasPermission("dmcm.reset")) {
            codesString += "r";
        }

        if (config.getBoolean("allowEssentialsColors")) {
            char[] codes = codesString.toCharArray();
            for (char code : codes) {
                text = text.replaceAll("&" + code, "§" + code);
            }
        }
        return text;
    }

    public static TextComponent formatNoSpoiler(Player player, String text) {
        text = color(player, text);

        for (FormatElement formatElement : formattingElements) {
            final Pattern pattern = Pattern.compile(formatElement.regex, Pattern.MULTILINE);
            final Matcher matcher = pattern.matcher(text);

            while (matcher.find()) {
                text = text.replace(matcher.group(0), formatElement.replace + matcher.group(1) + "§r");
            }
        }

        TextComponent retText = new TextComponent(text);
        return retText;
    }

    public static TextComponent format(Player player, String text) {
        if (!config.getBoolean("allowSpoilers")) {
            return formatNoSpoiler(player, text);
        }

        text = color(player, text);
        TextComponent retText = new TextComponent();

        // Spoilers
        text = text + " ";
        String[] splitText = text.split("\\|\\|");
        boolean spoiler = false;
        int count = 0;
        for (String textPiece: splitText) {
            String showText = textPiece;
            boolean spoil = spoiler && !(count % 2 == 0) && (count + 1 != splitText.length); //  && splitText.length != 1)
            if (spoil) {
                String clearedText = color(player, textPiece).replaceAll("§(.)", "");
                showText = clearedText.replaceAll("(.)", ChatColor.DARK_GRAY + "▋");
                showText += ChatColor.RESET;
            } else if (spoiler) {
                showText = "||" + showText;
            }

            TextComponent addComponent = formatNoSpoiler(player, showText);
            if (spoil) {
                addComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GRAY + "Spoilered text: " + ChatColor.RESET + color(player, text))));
            }

            retText.addExtra(addComponent);
            spoiler = !spoiler;
            count++;
        }

        return retText;
    }
}
