package io.github.dawncord.api.entities.message.modal;

import java.util.List;

/**
 * Represents data for a modal.
 */
public record ModalData(String customId, List<ElementData> elements) {
}
