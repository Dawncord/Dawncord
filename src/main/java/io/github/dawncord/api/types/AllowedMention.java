package io.github.dawncord.api.types;

/**
 * Represents types of allowed mentions in messages.
 */
public enum AllowedMention {
    /**
     * Controls role mentions.
     */
    ROLE("roles"),

    /**
     * Controls user mentions.
     */
    USER("users"),

    /**
     * Controls @everyone and @here mentions.
     */
    EVERYONE("everyone"),

    /**
     * Suppresses all mentions.
     */
    EMPTY(null);

    private final String value;

    AllowedMention(String value) {
        this.value = value;
    }

    /**
     * Gets the value representing the allowed mention.
     *
     * @return The value representing the allowed mention.
     */
    public String getValue() {
        return value;
    }
}
