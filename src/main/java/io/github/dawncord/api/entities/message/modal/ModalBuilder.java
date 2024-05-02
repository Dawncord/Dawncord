package io.github.dawncord.api.entities.message.modal;

import java.util.ArrayList;
import java.util.List;

/**
 * A builder for creating instances of the Modal class.
 */
public class ModalBuilder {
    private String title;
    private String customId;
    private List<Element> elements = new ArrayList<>();

    /**
     * Constructs a ModalBuilder with default values.
     */
    public ModalBuilder() {
    }

    /**
     * Constructs a ModalBuilder with the specified title, custom ID, and elements.
     *
     * @param title    The title of the modal.
     * @param customId The custom ID of the modal.
     * @param elements The input elements of the modal.
     */
    public ModalBuilder(String title, String customId, List<Element> elements) {
        this.title = title;
        this.customId = customId;
        this.elements = elements;
    }

    /**
     * Sets the title of the modal.
     *
     * @param title The title to set.
     * @return This ModalBuilder instance.
     */
    public ModalBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Sets the custom ID of the modal.
     *
     * @param customId The custom ID to set.
     * @return This ModalBuilder instance.
     */
    public ModalBuilder setCustomId(String customId) {
        this.customId = customId;
        return this;
    }

    /**
     * Sets the input elements of the modal.
     *
     * @param elements The input elements to set.
     * @return This ModalBuilder instance.
     */
    public ModalBuilder setElements(List<Element> elements) {
        this.elements.addAll(elements);
        return this;
    }

    /**
     * Adds input elements to the modal.
     *
     * @param elements The input elements to add.
     * @return This ModalBuilder instance.
     */
    public ModalBuilder setElements(Element... elements) {
        this.elements.addAll(List.of(elements));
        return this;
    }

    /**
     * Builds and returns a Modal instance.
     *
     * @return The constructed Modal instance.
     */
    public Modal build() {
        return new Modal(title, customId, elements);
    }
}
