package org.dimas4ek.wrapper.entities.message.component;

import org.dimas4ek.wrapper.types.SelectMenuType;

public class SelectMenu {
    public static SelectMenuBuilder create(SelectMenuType type, String customId) {
        return new SelectMenuBuilder(type, customId);
    }
}
