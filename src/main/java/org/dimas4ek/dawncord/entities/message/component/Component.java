package org.dimas4ek.dawncord.entities.message.component;

public class Component {
    public static ActionRowBuilder actionRow(ButtonBuilder... components) {
        return new ActionRowBuilder(components);
    }

    public static ActionRowBuilder actionRow(SelectMenuBuilder component) {
        return new ActionRowBuilder(component);
    }
}
