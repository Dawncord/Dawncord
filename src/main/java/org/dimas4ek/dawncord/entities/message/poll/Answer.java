package org.dimas4ek.dawncord.entities.message.poll;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.dawncord.entities.DefaultEmoji;
import org.dimas4ek.dawncord.entities.Emoji;
import org.dimas4ek.dawncord.entities.guild.Guild;

public class Answer {
    private final JsonNode answer;
    private final Guild guild;
    private String id;
    private String text;
    private Emoji emoji;

    public Answer(JsonNode answer, Guild guild) {
        this.answer = answer;
        this.guild = guild;
    }

    public String getId() {
        if (id == null) {
            id = answer.get("answer_id").asText();
        }
        return id;
    }

    public String getText() {
        if (text == null) {
            text = answer.get("poll_media").get("text").asText();
        }
        return text;
    }

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
