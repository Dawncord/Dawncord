package org.dimas4ek.dawncord.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.dimas4ek.dawncord.ApiClient;
import org.dimas4ek.dawncord.Routes;
import org.dimas4ek.dawncord.entities.message.modal.Element;
import org.dimas4ek.dawncord.interaction.InteractionData;
import org.dimas4ek.dawncord.types.ComponentType;
import org.dimas4ek.dawncord.types.InteractionCallbackType;

import java.io.File;
import java.util.List;

public class ModalCreateAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private List<File> attachments;
    private final InteractionData data;

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

    public ModalCreateAction setTitle(String title) {
        return setProperty("title", title);
    }

    public ModalCreateAction setCustomId(String customId) {
        return setProperty("custom_id", customId);
    }

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
        jsonObject.removeAll();
    }
}
