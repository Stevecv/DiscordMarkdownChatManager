package org.stevecv.chatmanager;

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

    public static TextComponent format(String text) {
        text = color(text);

        for (FormatElement formatElement: formattingElements) {
            final Pattern pattern = Pattern.compile(formatElement.regex, Pattern.MULTILINE);
            final Matcher matcher = pattern.matcher(text);

            while (matcher.find()) {
                text = text.replace(matcher.group(0), formatElement.replace + matcher.group(1) + "§r");
            }
        }


        TextComponent retText = new TextComponent();

        // Spoilers
        String[] splitText = text.split("\\|\\|(.*?)\\|\\|");
        boolean spoiler = false;
        for (String textPiece: splitText) {
            String showText = textPiece;
            if (spoiler) {
                showText = showText.replaceAll("(.)", "|");
            }

            TextComponent addComponent = new TextComponent(showText);
            if (spoiler) {
                addComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(textPiece)));
            }

            retText.addExtra(addComponent);
            spoiler = !spoiler;
        }

        return retText;
    }

    private static @Nullable RegisteredServiceProvider<Chat> chatInst = Bukkit.getServicesManager().getRegistration(Chat.class);
    private static Chat chat = chatInst.getProvider();

    public static String getPrefix(Player p) {
        return color(chat.getPlayerPrefix(p));
    }
}
