package org.dimas4ek.wrapper.entities.message;

import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.entities.Emoji;
import org.dimas4ek.wrapper.entities.EmojiImpl;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.json.JSONObject;

import java.util.List;

public class ReactionImpl implements Reaction {
    private final Guild guild;
    private final Message message;
    private final JSONObject reaction;

    public ReactionImpl(Guild guild, Message message, JSONObject reaction) {
        this.guild = guild;
        this.message = message;
        this.reaction = reaction;
    }

    @Override
    public int getTotal() {
        return reaction.getInt("count");
    }

    @Override
    public int getNormalCount() {
        return reaction.getJSONObject("count_details").getInt("normal");
    }

    @Override
    public int getBurstCount() {
        return reaction.getJSONObject("count_details").getInt("burst");
    }

    @Override
    public boolean isMe() {
        return reaction.getBoolean("me");
    }

    @Override
    public boolean isMeBurst() {
        return reaction.getBoolean("me_burst");
    }

    @Override
    public Emoji getGuildEmoji() {
        return new EmojiImpl(guild, reaction.getJSONObject("emoji"));
    }

    public String getEmoji() {
        return reaction.getJSONObject("emoji").getString("name");
    }

    @Override
    public boolean isGuildEmoji() {
        return !reaction.getJSONObject("emoji").isNull("id");
    }

    @Override
    public List<String> getBurstColors() {
        //TODO add burst colors
        return null;
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
