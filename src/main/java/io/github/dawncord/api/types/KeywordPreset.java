package io.github.dawncord.api.types;

/**
 * Represents keyword presets for content filtering.
 */
public enum KeywordPreset {
    /**
     * Swear words or cursing
     */
    PROFANITY(1),

    /**
     * Sexually explicit language or content
     */
    SEXUAL_CONTENT(2),

    /**
     * Personal insults or hate speech
     */
    SLURS(3);

    private final int value;

    KeywordPreset(int value) {
        this.value = value;
    }

    /**
     * Gets the integer value representing the keyword preset.
     *
     * @return The integer value.
     */
    public int getValue() {
        return value;
    }
}
