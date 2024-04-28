package org.dimas4ek.dawncord.entities.message.modal;

import java.util.List;

public class ModalData {
    private final String customId;
    private final List<ElementData> elements;

    public ModalData(String customId, List<ElementData> elements) {
        this.customId = customId;
        this.elements = elements;
    }

    public String getCustomId() {
        return customId;
    }

    public List<ElementData> getElements() {
        return elements;
    }
}
