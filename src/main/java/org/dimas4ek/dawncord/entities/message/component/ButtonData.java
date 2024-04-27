package org.dimas4ek.dawncord.entities.message.component;

import org.dimas4ek.dawncord.entities.Emoji;
import org.dimas4ek.dawncord.types.ButtonStyle;

public class ButtonData {
    private final String customId;
    private final String url;
    private final ButtonStyle style;
    private final String label;
    private final boolean disabled;
    private final Emoji emoji;

    public ButtonData(String customId, String url, ButtonStyle style, String label, boolean disabled, Emoji emoji) {
        this.customId = customId;
        this.url = url;
        this.style = style;
        this.label = label;
        this.disabled = disabled;
        this.emoji = emoji;
    }

    public String getCustomId() {
        return customId;
    }

    public String getUrl() {
        return url;
    }

    public ButtonStyle getStyle() {
        return style;
    }

    public String getLabel() {
        return label;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public Emoji getEmoji() {
        return emoji;
    }
}
