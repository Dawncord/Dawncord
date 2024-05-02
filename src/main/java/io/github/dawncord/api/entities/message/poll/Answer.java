package io.github.dawncord.api.entities.message.poll;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.entities.DefaultEmoji;
import io.github.dawncord.api.entities.Emoji;
import io.github.dawncord.api.entities.guild.Guild;

/**
 * Represents an answer in a poll.
 */
public class Answer {
    private final JsonNode answer;
    private final Guild guild;
    private String id;
    private String text;
    private Emoji emoji;

    /**
     * Constructs an Answer object.
     *
     * @param answer The JSON representation of the answer.
     * @param guild  The guild associated with the answer.
     */
    public Answer(JsonNode answer, Guild guild) {
        this.answer = answer;
        this.guild = guild;
    }

    /**
     * Gets the ID of the answer.
     *
     * @return The ID of the answer.
     */
    public String getId() {
        if (id == null) {
            id = answer.get("answer_id").asText();
        }
        return id;
    }

    /**
     * Gets the text of the answer.
     *
     * @return The text of the answer.
     */
    public String getText() {
        if (text == null) {
            text = answer.get("poll_media").get("text").asText();
        }
        return text;
    }

    /**
     * Gets the emoji associated with the answer.
     *
     * @return The emoji associated with the answer.
     */
    public Emoji getEmoji() {
        if (emoji == null) {
            if (answer.get("poll_media").has("emoji") && answer.get("poll_media").get("emoji") != null) {
                JsonNode emojiNode = answer.get("poll_media").get("emoji");
                if (emojiNode.get("id") != null) {
                    emoji = guild.getEmojiById(emojiNode.get("id").asText());
                } else {
                    emoji = new DefaultEmoji(emojiNode.get("name").asText());
                }
            }
        }
        return emoji;
    }
}
