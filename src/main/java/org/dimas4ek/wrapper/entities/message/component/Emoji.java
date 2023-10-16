package org.dimas4ek.wrapper.entities.message.component;

import org.json.JSONObject;

public class Emoji {
    private final JSONObject emoji;

    public Emoji(JSONObject emoji) {
        this.emoji = emoji;
    }

    public String getId() {
        return emoji.getString("id");
    }

    public String getName() {
        return emoji.getString("name");
    }

    public boolean isAnimated() {
        return emoji.getBoolean("animated");
    }
}
