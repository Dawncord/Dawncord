package org.dimas4ek.dawncord.types;

public enum KeywordPreset {
    PROFANITY(1, "words that may be considered forms of swearing or cursing"),
    SEXUAL_CONTENT(2, "words that refer to sexually explicit behavior or activity"),
    SLURS(3, "personal insults or words that may be considered hate speech");

    private final int value;
    private final String description;

    KeywordPreset(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
