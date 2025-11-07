package io.github.dawncord.api.entities.guild.role;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.utils.LazyLoader;

import java.awt.*;

public class RoleColors {
    private final LazyLoader loader;
    private final JsonNode colors;
    private Color primary;
    private Color secondary;
    private Color tertiary;

    public RoleColors(JsonNode colors) {
        this.colors = colors;
        loader = new LazyLoader(colors);
    }

    public Color getPrimary() {
        primary = loader.loadIfExists(primary, "primary", () -> new Color(colors.get("primary_color").asInt()));
        return primary;
    }

    public Color getSecondary() {
        secondary = loader.loadIfExists(secondary, "primary", () -> new Color(colors.get("secondary_color").asInt()));
        return secondary;
    }

    public Color getTertiary() {
        tertiary = loader.loadIfExists(tertiary, "primary", () -> new Color(colors.get("tertiary_color").asInt()));
        return tertiary;
    }
}
