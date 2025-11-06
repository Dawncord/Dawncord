package io.github.dawncord.api.entities.guild.automod;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.types.AutoModActionType;
import io.github.dawncord.api.utils.LazyLoader;

/**
 * Represents an action taken by the auto-moderation system.
 */
public class AutoModAction {
    private final LazyLoader loader;
    private final JsonNode action;
    private final Guild guild;
    private AutoModActionType type;
    private ActionMetadata metadata;

    /**
     * Constructs an AutoModAction object with the provided JSON node and guild.
     *
     * @param action The JSON node representing the auto-moderation action.
     * @param guild  The guild associated with the action.
     */
    public AutoModAction(JsonNode action, Guild guild) {
        this.action = action;
        this.guild = guild;
        loader = new LazyLoader(action);
    }

    /**
     * Retrieves the type of the auto-moderation action.
     *
     * @return The type of the auto-moderation action.
     */
    public AutoModActionType getType() {
        type = loader.loadEnumObject(type, "type", AutoModActionType.class);
        return type;
    }

    /**
     * Retrieves the metadata associated with the auto-moderation action.
     *
     * @return The metadata associated with the auto-moderation action, or null if not available.
     */
    public ActionMetadata getMetadata() {
        metadata = loader.loadIfExists(metadata, "metadata",
                () -> new ActionMetadata(action.get("metadata"), guild));
        return metadata;
    }
}
