package io.github.dawncord.api.entities.message.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Builder class for ActionRow components.
 */
public class ActionRowBuilder implements ComponentBuilder {
    private final List<ComponentBuilder> components = new ArrayList<>();

    /**
     * Constructs an ActionRowBuilder with the provided components.
     *
     * @param components The components to include in the action row.
     */
    public ActionRowBuilder(ComponentBuilder... components) {
        this.components.addAll(Arrays.asList(components));
    }

    /**
     * Retrieves the list of components in this action row.
     *
     * @return The list of components.
     */
    public List<ComponentBuilder> getComponents() {
        return components;
    }

    @Override
    public int getType() {
        return 1;
    }
}
