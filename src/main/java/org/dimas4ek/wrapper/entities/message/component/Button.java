package org.dimas4ek.wrapper.entities.message.component;

public class Button {
    public static ButtonBuilder primary(String customId, String label) {
        return new ButtonBuilder(1, customId, label);
    }

    public static ButtonBuilder secondary(String customId, String label) {
        return new ButtonBuilder(2, customId, label);
    }

    public static ButtonBuilder success(String customId, String label) {
        return new ButtonBuilder(3, customId, label);
    }

    public static ButtonBuilder danger(String customId, String label) {
        return new ButtonBuilder(4, customId, label);
    }

    public static ButtonBuilder link(String url, String label) {
        return new ButtonBuilder(5, url, label);
    }
}
