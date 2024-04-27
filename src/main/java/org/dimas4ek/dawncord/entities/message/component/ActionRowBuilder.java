package org.dimas4ek.dawncord.entities.message.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActionRowBuilder implements ComponentBuilder {
    private final List<ComponentBuilder> components = new ArrayList<>();

    public ActionRowBuilder(ComponentBuilder... components) {
        this.components.addAll(Arrays.asList(components));
    }

    public List<ComponentBuilder> getComponents() {
        return components;
    }

    @Override
    public int getType() {
        return 1;
    }
}
