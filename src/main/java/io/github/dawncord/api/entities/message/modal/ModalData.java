package io.github.dawncord.api.entities.message.modal;

import java.util.List;

/**
 * Represents data for a modal.
 */
public class ModalData {
    private final String customId;
    private final List<ElementData> elements;

    /**
     * Constructs a ModalData object with the specified custom ID and elements.
     *
     * @param customId The custom ID of the modal.
     * @param elements The elements of the modal.
     */
    public ModalData(String customId, List<ElementData> elements) {
        this.customId = customId;
        this.elements = elements;
    }

    /**
     * Gets the custom ID of the modal.
     *
     * @return The custom ID.
     */
    public String getCustomId() {
        return customId;
    }

    /**
     * Gets the elements of the modal.
     *
     * @return The elements.
     */
    public List<ElementData> getElements() {
        return elements;
    }
}
