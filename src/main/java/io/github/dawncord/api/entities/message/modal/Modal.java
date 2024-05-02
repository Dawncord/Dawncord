package io.github.dawncord.api.entities.message.modal;

import java.util.List;

/**
 * Represents a modal dialog with input elements.
 */
public class Modal {
    private final String title;
    private final String customId;
    private final List<Element> elements;

    /**
     * Constructs an empty modal.
     */
    public Modal() {
        this.title = null;
        this.customId = null;
        this.elements = null;
    }

    /**
     * Constructs a modal with the specified title, custom ID, and input elements.
     *
     * @param title    The title of the modal.
     * @param customId The custom ID of the modal.
     * @param elements The input elements of the modal.
     */
    public Modal(String title, String customId, List<Element> elements) {
        this.title = title;
        this.customId = customId;
        this.elements = elements;
    }

    /**
     * Gets the title of the modal.
     *
     * @return The title.
     */
    public String getTitle() {
        return title;
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
     * Gets the input elements of the modal.
     *
     * @return The input elements.
     */
    public List<Element> getElements() {
        return elements;
    }
}
