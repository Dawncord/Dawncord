package io.github.dawncord.api.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.message.modal.Element;
import io.github.dawncord.api.entities.message.modal.ModalData;
import io.github.dawncord.api.interaction.InteractionData;
import io.github.dawncord.api.types.ComponentType;
import io.github.dawncord.api.types.InteractionCallbackType;

import java.io.File;
import java.util.List;

/**
 * Represents an action for creating a modal.
 *
 * @see ModalData
 */
public class ModalCreateAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private List<File> attachments;
    private final InteractionData data;

    /**
     * Create a new {@link ModalCreateAction}
     *
     * @param interactionData The interaction data associated with the creation of the modal.
     */
    public ModalCreateAction(InteractionData interactionData) {
        this.data = interactionData;
        this.jsonObject = mapper.createObjectNode();
        this.jsonObject.put("type", InteractionCallbackType.MODAL.getValue());
        this.jsonObject.set("data", mapper.createObjectNode().set("components", mapper.createArrayNode()));
    }

    private ModalCreateAction setProperty(String key, Object value) {
        ((ObjectNode) jsonObject.get("data")).set(key, mapper.valueToTree(value));
        return this;
    }

    private void createComponentProperty(int size) {
        ArrayNode componentsNode = (ArrayNode) jsonObject.get("data").get("components");
        if (size != 0) {
            for (int i = 0; i < size; i++) {
                componentsNode.add(
                        mapper.createObjectNode()
                                .put("type", ComponentType.ACTION.getValue())
                                .set("components", mapper.createArrayNode()
                                        .add(mapper.createObjectNode()))
                );
            }
        }
    }

    private void setComponentProperty(int i, String key, Object value) {
        ArrayNode componentsNode = (ArrayNode) jsonObject.get("data").get("components");
        ((ObjectNode) componentsNode.get(i).get("components").get(0)).set(key, mapper.valueToTree(value));
    }

    /**
     * Sets the title for the modal.
     *
     * @param title the title to set
     * @return the modified ModalCreateAction object
     */
    public ModalCreateAction setTitle(String title) {
        return setProperty("title", title);
    }

    /**
     * Sets the custom ID for the modal.
     *
     * @param customId the custom ID to set
     * @return the modified ModalCreateAction object
     */
    public ModalCreateAction setCustomId(String customId) {
        return setProperty("custom_id", customId);
    }

    /**
     * Sets the elements for the modal.
     *
     * @param elements the elements to set
     * @return the modified ModalCreateAction object
     */
    public ModalCreateAction setElements(Element... elements) {
        createComponentProperty(elements.length);
        for (int i = 0; i < elements.length; i++) {
            setComponentProperty(i, "type", ComponentType.TEXT_INPUT.getValue());
            setComponentProperty(i, "label", elements[i].getLabel());
            setComponentProperty(i, "custom_id", elements[i].getCustomId());
            setComponentProperty(i, "style", elements[i].getStyle().getValue());
            setComponentProperty(i, "min_length", elements[i].getMinLength() != null ? elements[i].getMinLength() : null);
            setComponentProperty(i, "max_length", elements[i].getMaxLength() != null ? elements[i].getMaxLength() : null);
            setComponentProperty(i, "placeholder", elements[i].getPlaceholder() != null ? elements[i].getPlaceholder() : null);
            setComponentProperty(i, "required", elements[i].getRequired() != null ? elements[i].getRequired() : null);
        }
        return this;
    }

    private void submit() {
        ApiClient.post(jsonObject, Routes.Reply(data.getInteraction().getInteractionId(), data.getInteraction().getInteractionToken()));
    }
}
