package io.github.dawncord.api.entities.message.component;

import io.github.dawncord.api.types.SelectMenuType;

/**
 * Utility class for creating select menus.
 */
public class SelectMenu {
    /**
     * Creates a select menu with the specified type and custom ID.
     *
     * @param type     The type of the select menu.
     * @param customId The custom ID of the select menu.
     * @return A SelectMenuBuilder with the specified type and custom ID.
     */
    public static SelectMenuBuilder create(SelectMenuType type, String customId) {
        return new SelectMenuBuilder(type, customId);
    }
}
