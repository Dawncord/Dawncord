package io.github.dawncord.api.entities.message;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.CustomEmoji;
import io.github.dawncord.api.entities.Emoji;
import io.github.dawncord.api.entities.guild.Guild;

import java.util.List;

/**
 * Represents an implementation of the Reaction interface.
 */
public class Reaction {
    private final JsonNode reaction;
    private final Guild guild;
    private final Message message;
    private Integer total;
    private Integer normalCount;
    private Integer burstCount;
    private Boolean isMe;
    private Boolean isMeBurst;
    private Emoji guildEmoji;
    private String emoji;
    private Boolean isGuildEmoji;
    private List<String> burstColors;

    /**
     * Constructs a ReactionImpl object with the given parameters.
     *
     * @param reaction The JSON node representing the reaction.
     * @param guild    The guild associated with the reaction.
     * @param message  The message associated with the reaction.
     */
    public Reaction(JsonNode reaction, Guild guild, Message message) {
        this.reaction = reaction;
        this.guild = guild;
        this.message = message;
    }

    public int getTotal() {
        if (total == null) {
            total = reaction.get("count").asInt();
        }
        return total;
    }

    public int getNormalCount() {
        if (normalCount == null) {
            normalCount = reaction.get("count_details").get("normal").asInt();
        }
        return normalCount;
    }

    public int getBurstCount() {
        if (burstCount == null) {
            burstCount = reaction.get("count_details").get("burst").asInt();
        }
        return burstCount;
    }

    public boolean isMe() {
        if (isMe == null) {
            isMe = reaction.get("me").asBoolean();
        }
        return isMe;
    }

    public boolean isMeBurst() {
        if (isMeBurst == null) {
            isMeBurst = reaction.get("me_burst").asBoolean();
        }
        return isMeBurst;
    }

    public Emoji getGuildEmoji() {
        if (guildEmoji == null) {
            guildEmoji = new CustomEmoji(reaction.get("emoji"), guild);
        }
        return guildEmoji;
    }

    public String getEmoji() {
        if (emoji == null) {
            emoji = reaction.get("emoji").get("name").asText();
        }
        return emoji;
    }

    public boolean isGuildEmoji() {
        if (isGuildEmoji == null) {
            isGuildEmoji = reaction.get("emoji").has("id") && reaction.get("emoji").hasNonNull("id");
        }
        return isGuildEmoji;
    }

    public List<String> getBurstColors() {
        if (burstColors == null) {
            //todo
        }
        return burstColors;
    }

    public void delete(String userId) {
        String emoji = (isGuildEmoji() ? getGuildEmoji().name() : getEmoji());
        ApiClient.delete(Routes.Channel.Message.Reaction.ByUser(message.getChannel().getId(), message.getId(), emoji, userId));
    }

    public void delete(long userId) {
        delete(String.valueOf(userId));
    }
}
