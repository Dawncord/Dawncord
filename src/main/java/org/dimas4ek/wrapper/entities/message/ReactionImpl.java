package org.dimas4ek.wrapper.entities.message;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.entities.Emoji;
import org.dimas4ek.wrapper.entities.EmojiImpl;
import org.dimas4ek.wrapper.entities.guild.Guild;

import java.util.List;

public class ReactionImpl implements Reaction {
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

    public ReactionImpl(JsonNode reaction, Guild guild, Message message) {
        this.reaction = reaction;
        this.guild = guild;
        this.message = message;
    }

    @Override
    public int getTotal() {
        if (total == null) {
            total = reaction.get("count").asInt();
        }
        return total;
    }

    @Override
    public int getNormalCount() {
        if (normalCount == null) {
            normalCount = reaction.get("count_details").get("normal").asInt();
        }
        return normalCount;
    }

    @Override
    public int getBurstCount() {
        if (burstCount == null) {
            burstCount = reaction.get("count_details").get("burst").asInt();
        }
        return burstCount;
    }

    @Override
    public boolean isMe() {
        if (isMe == null) {
            isMe = reaction.get("me").asBoolean();
        }
        return isMe;
    }

    @Override
    public boolean isMeBurst() {
        if (isMeBurst == null) {
            isMeBurst = reaction.get("me_burst").asBoolean();
        }
        return isMeBurst;
    }

    @Override
    public Emoji getGuildEmoji() {
        if (guildEmoji == null) {
            guildEmoji = new EmojiImpl(reaction.get("emoji"), guild);
        }
        return guildEmoji;
    }

    public String getEmoji() {
        if (emoji == null) {
            emoji = reaction.get("emoji").get("name").asText();
        }
        return emoji;
    }

    @Override
    public boolean isGuildEmoji() {
        if (isGuildEmoji == null) {
            isGuildEmoji = reaction.get("emoji").has("id") && reaction.get("emoji").hasNonNull("id");
        }
        return isGuildEmoji;
    }

    @Override
    public List<String> getBurstColors() {
        if (burstColors == null) {
            //todo add burst colors
        }
        return burstColors;
    }

    @Override
    public void delete(String userId) {
        ApiClient.delete("/channels/" + message.getChannel().getId() + "/messages/" + message.getId() + "/" +
                (isGuildEmoji() ? getGuildEmoji().getName() : getEmoji()) + "/" + userId);
    }

    @Override
    public void delete(long userId) {
        delete(String.valueOf(userId));
    }
}
