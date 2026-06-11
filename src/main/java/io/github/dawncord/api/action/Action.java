package io.github.dawncord.api.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


/**
 * Represents a configurable API action that can be submitted.
 *
 * @param <T> The concrete action type returned by fluent setters.
 */
public abstract class Action<T> {
    protected static final ObjectMapper mapper = new ObjectMapper();
    protected final ObjectNode jsonObject;
    protected boolean hasChanges = false;
    protected String createdId;

    public Action() {
        this.jsonObject = mapper.createObjectNode();
    }

    //todo check return
    protected T setProperty(String name, Object value) {
        jsonObject.set(name, mapper.valueToTree(value));
        hasChanges = true;
        return (T) this;
    }

    protected String getCreatedId() {
        return createdId;
    }

    protected abstract void submit();
}
