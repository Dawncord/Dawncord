package io.github.dawncord.api.types;

/**
 * Represents option types.
 */
public enum OptionType {
    /**
     * Sub-command option type.
     */
    SUB_COMMAND(1),

    /**
     * Sub-command group option type.
     */
    SUB_COMMAND_GROUP(2),

    /**
     * String option type.
     */
    STRING(3),

    /**
     * Integer option type.
     */
    INTEGER(4),

    /**
     * Boolean option type.
     */
    BOOLEAN(5),

    /**
     * User option type.
     */
    USER(6),

    /**
     * Channel option type.
     */
    CHANNEL(7),

    /**
     * Role option type.
     */
    ROLE(8),

    /**
     * Mentionable option type.
     */
    MENTIONABLE(9),

    /**
     * Number option type.
     */
    NUMBER(10),

    /**
     * Attachment option type.
     */
    ATTACHMENT(11);

    private final int value;

    OptionType(int value) {
        this.value = value;
    }

    /**
     * Gets the value of the option type.
     *
     * @return The value of the option type.
     */
    public int getValue() {
        return value;
    }
}
