package io.github.dawncord.api.types;

/**
 * Represents different types of select menus.
 */
public enum SelectMenuType {
    /**
     * Select menu for selecting text options.
     */
    TEXT(3),

    /**
     * Select menu for selecting users.
     */
    USER(5),

    /**
     * Select menu for selecting roles.
     */
    ROLE(6),

    /**
     * Select menu for selecting mentionable entities.
     */
    MENTIONABLE(7),

    /**
     * Select menu for selecting channels.
     */
    CHANNEL(8);

    private final int value;

    SelectMenuType(int value) {
        this.value = value;
    }

    /**
     * Gets the value of the select menu type.
     *
     * @return The value of the select menu type.
     */
    public int getValue() {
        return value;
    }
}
