package io.github.dawncord.api.action.guildchannel;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.channel.GuildChannel;
import io.github.dawncord.api.types.*;

/**
 * Represents an action for creating a guild channel.
 *
 * @see GuildChannel
 */
public class GuildChannelCreateAction extends GuildChannelAction {
    private String createdId;

    /**
     * Create a new {@link GuildChannelCreateAction}
     *
     * @param guildId     The ID of the guild where the channel will be created.
     * @param channelType The type of the channel to be created.
     */
    public GuildChannelCreateAction(String guildId, ChannelType channelType) {
        super(guildId, channelType);
    }

    /**
     * Create a new {@link GuildChannelCreateAction}
     */
    public GuildChannelCreateAction() {
        super();
    }

    public GuildChannelCreateAction test(){
        return this;
    }
    
    private String getCreatedId() {
        return createdId;
    }

    private JsonNode getJsonObject() {
        return jsonObject;
    }

    @Override
    protected void submit() {
        if (hasChanges) {
            JsonNode jsonNode = ApiClient.post(jsonObject, Routes.Guild.Channels(guildId));
            if (jsonNode != null && jsonNode.has("id")) {
                createdId = jsonNode.get("id").asText();
            }
            hasChanges = false;
        }
    }
}
