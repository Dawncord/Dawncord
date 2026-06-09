package io.github.dawncord.api.entities.message.modal;

import java.util.List;

/**
 * Represents a modal dialog with input elements.
 */
public record Modal(String title, String customId, List<Element> elements) {
    /**
     * Constructs an empty modal.
     */
    public Modal() {
        this(null, null, null);
    }

    /**
     * Constructs a modal with the specified title, custom ID, and input elements.
     *
     * @param title    The title of the modal.
     * @param customId The custom ID of the modal.
     * @param elements The input elements of the modal.
     */
    public Modal {
    }

    /**
     * Gets the title of the modal.
     *
     * @return The title.
     */
    @Override
    public String title() {
        return title;
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
     * Gets the input elements of the modal.
     *
     * @return The input elements.
     */
    @Override
    public List<Element> elements() {
        return elements;
    }
}
