package io.github.dawncord.api.entities.message.component;

/**
 * Utility class for creating components such as action rows.
 */
public class Component {
    /**
     * Creates an action row containing buttons.
     *
     * @param components The button components to include in the action row.
     * @return An ActionRowBuilder with the specified button components.
     */
    public static ActionRowBuilder actionRow(ButtonBuilder... components) {
        return new ActionRowBuilder(components);
    }

    /**
     * Creates an action row containing a select menu.
     *
     * @param component The select menu component to include in the action row.
     * @return An ActionRowBuilder with the specified select menu component.
     */
    public static ActionRowBuilder actionRow(SelectMenuBuilder component) {
        return new ActionRowBuilder(component);
    }
}
