package io.github.dawncord.api.entities.message.component;

import io.github.dawncord.api.entities.Emoji;
import io.github.dawncord.api.types.ButtonStyle;

/**
 * Represents the data of a button component.
 */
public record ButtonData(String customId, String url, ButtonStyle style,
                         String label, boolean disabled, Emoji emoji) {
}
