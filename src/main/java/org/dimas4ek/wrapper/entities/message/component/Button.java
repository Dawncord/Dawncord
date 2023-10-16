package org.dimas4ek.wrapper.entities.message.component;

import org.dimas4ek.wrapper.types.ButtonStyle;
import org.dimas4ek.wrapper.types.ComponentType;
import org.json.JSONObject;

public class Button {
    private final JSONObject button;

    public Button(JSONObject button) {
        this.button = button;
    }

    public String getType() {
        for (ComponentType type : ComponentType.values()) {
            if (button.getInt("type") == type.getValue()) {
                return type.toString();
            }
        }
        return null;
    }

    public String getCustomId() {
        return button.getString("custom_id");
    }

    public String getUrl() {
        return button.getString("url");
    }

    public String getStyle() {
        for (ButtonStyle style : ButtonStyle.values()) {
            if (button.getInt("type") == style.getValue()) {
                return style.toString();
            }
        }
        return null;
    }

    public String getLabel() {
        return button.getString("label");
    }

    public boolean isDisabled() {
        return button.has("disabled") && button.getBoolean("disabled");
    }

    public Emoji getEmoji() {
        return new Emoji(button.getJSONObject("emoji"));
    }
}
