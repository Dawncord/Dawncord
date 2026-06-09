package io.github.dawncord.api.entities.message.modal;

import java.util.List;

/**
 * Represents data for a modal.
 */
public record ModalData(String customId, List<ElementData> elements) {
    /**
     * Constructs a ModalData object with the specified custom ID and elements.
     *
     * @param customId The custom ID of the modal.
     * @param elements The elements of the modal.
     */
    public ModalData {
    }

    /**
     * Gets the custom ID of the modal.
     *
     * @return The custom ID.
     */
    @Override
    public String customId() {
        return customId;
    }

    /**
     * Gets the elements of the modal.
     *
     * @return The elements.
     */
    @Override
    public List<ElementData> elements() {
        return elements;
    }
}
