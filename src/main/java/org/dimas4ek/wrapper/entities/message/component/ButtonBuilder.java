package org.dimas4ek.wrapper.entities.message.component;

import org.dimas4ek.wrapper.types.ButtonStyle;

public class ButtonBuilder implements ComponentBuilder {
    private final int style;
    private final String customId;
    private final String url;
    private final String label;
    private boolean isDisabled;
    private String emoji;

    public ButtonBuilder(int style, String customIdOrUrl, String label) {
        this.style = style;
        this.customId = style != ButtonStyle.Link.getValue() ? customIdOrUrl : null;
        this.url = style == ButtonStyle.Link.getValue() ? customIdOrUrl : null;
        this.label = label;
    }

    @Override
    public int getType() {
        return 2;
    }

    public String getCustomId() {
        return customId;
    }

    public String getUrl() {
        return url;
    }

    public int getStyle() {
        return style;
    }

    public String getLabel() {
        return label;
    }

    public boolean isDisabled() {
        return isDisabled;
    }

    public String getEmoji() {
        return emoji;
    }

    public ButtonBuilder withEmoji(String emojiIdOrName) {
        this.emoji = emojiIdOrName;
        return this;
    }

    public ButtonBuilder enabled() {
        this.isDisabled = false;
        return this;
    }

    public ButtonBuilder disabled() {
        this.isDisabled = true;
        return this;
    }
}
