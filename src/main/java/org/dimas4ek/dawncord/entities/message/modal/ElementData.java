package org.dimas4ek.dawncord.entities.message.modal;

public class ElementData {
    private final String value;
    private final String customId;

    public ElementData(String value, String customId) {
        this.value = value;
        this.customId = customId;
    }

    public String getValue() {
        return value;
    }

    public String getCustomId() {
        return customId;
    }
}
