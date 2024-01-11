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

public class Formatter {
    static ArrayList<FormatElement> formattingElements = new ArrayList<>();
    public void registerFormatter(String character, String replace) {
        formattingElements.add(new FormatElement(character, replace));
    }

    public static String color(String text) {
        char[] codes = "0123456789abcdekmnolr".toCharArray();
        for (char code: codes) {
            text = text.replaceAll("&" + code, "§" + code);
        }
        return text;
    }

    public static TextComponent formatNoSpoiler(String text) {
        text = color(text);

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

    public static TextComponent format(String text) {
        text = color(text);
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
                String clearedText = color(textPiece).replaceAll("§(.)", "");
                showText = clearedText.replaceAll("(.)", ChatColor.DARK_GRAY + "▋");
                showText += ChatColor.RESET;
            } else if (spoiler) {
                showText = "||" + showText;
            }

            TextComponent addComponent = formatNoSpoiler(showText);
            if (spoil) {
                addComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GRAY + "Spoilered text: " + ChatColor.RESET + color(textPiece))));
            }

            retText.addExtra(addComponent);
            spoiler = !spoiler;
            count++;
        }

        return retText;
    }

    public static String getPrefix(Player p) {
        if (ChatManager.chat == null) return "";
        return color(ChatManager.chat.getPlayerPrefix(p));
    }
}
