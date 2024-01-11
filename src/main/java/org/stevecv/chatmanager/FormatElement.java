package org.stevecv.chatmanager;

public class FormatElement {
    public String character;
    public String replace;
    public String regex;

    public FormatElement(String character, String replace) {
        this.character = character;
        this.replace = replace;
        this.regex = character + "(.*?)" + character;
    }
}
