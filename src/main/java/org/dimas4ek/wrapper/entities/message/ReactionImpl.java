package org.dimas4ek.wrapper.entities.message;

import org.json.JSONObject;

import java.util.List;

public class ReactionImpl implements Reaction {
    private final JSONObject reaction;

    public ReactionImpl(JSONObject reaction) {
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
    public String getEmoji() {
        return reaction.getJSONObject("emoji").isNull("id")
                ? reaction.getJSONObject("emoji").getString("name")
                : reaction.getJSONObject("emoji").getString("id");
    }

    @Override
    public List<String> getBurstColors() {
        //TODO add burst colors
        return null;
    }

    public static boolean isEmojiLong(String input) {
        try {
            Long.parseLong(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
