package org.dimas4ek.dawncord.entities.guild.automod;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.dawncord.entities.guild.Guild;
import org.dimas4ek.dawncord.types.AutoModActionType;
import org.dimas4ek.dawncord.utils.EnumUtils;

public class AutoModAction {
    private final JsonNode action;
    private final Guild guild;
    private AutoModActionType type;
    private ActionMetadata metadata;

    public AutoModAction(JsonNode action, Guild guild) {
        this.action = action;
        this.guild = guild;
    }

    public AutoModActionType getType() {
        if (type == null) {
            type = EnumUtils.getEnumObject(action, "type", AutoModActionType.class);
        }
        return type;
    }

    public ActionMetadata getMetadata() {
        if (metadata == null) {
            metadata = action.has("metadata") && action.hasNonNull("metadata") && !action.get("metadata").isEmpty()
                    ? new ActionMetadata(action.get("metadata"), guild)
                    : null;
        }
        return metadata;
    }
}
