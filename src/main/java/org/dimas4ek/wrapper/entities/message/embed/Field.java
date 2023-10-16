package org.dimas4ek.wrapper.entities.message.embed;

import org.json.JSONObject;

public class Field {
    private final JSONObject field;

    public Field(JSONObject field) {
        this.field = field;
    }

    public String getName() {
        return field.getString("name");
    }

    public String getValue() {
        return field.getString("value");
    }

    public boolean isInline() {
        return field.getBoolean("inline");
    }
}
