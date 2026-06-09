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
}
