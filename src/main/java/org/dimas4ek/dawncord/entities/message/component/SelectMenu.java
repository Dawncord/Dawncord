package org.dimas4ek.dawncord.entities.message.component;

import org.dimas4ek.dawncord.types.SelectMenuType;

public class SelectMenu {
    public static SelectMenuBuilder create(SelectMenuType type, String customId) {
        return new SelectMenuBuilder(type, customId);
    }
}
