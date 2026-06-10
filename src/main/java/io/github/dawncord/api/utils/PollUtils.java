package io.github.dawncord.api.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.entities.CustomEmoji;
import io.github.dawncord.api.entities.DefaultEmoji;
import io.github.dawncord.api.entities.message.poll.AnswerData;
import io.github.dawncord.api.entities.message.poll.PollData;
import org.jetbrains.annotations.NotNull;

/**
 * Utility class for creating JSON nodes representing polls.
 */
public class PollUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Creates a JSON node representing a poll.
     *
     * @param poll The poll data.
     * @return The JSON node representing the poll.
     */
    public static JsonNode createPoll(PollData poll) {
        ObjectNode pollNode = mapper.createObjectNode();
        pollNode.set("question", mapper.createObjectNode().put("text", poll.question()));
        pollNode.put("allow_multiselect", poll.allowMultiselect());
        pollNode.put("duration", poll.duration());
        ArrayNode answersNode = mapper.createArrayNode();
        poll.answers().forEach(answer -> {
            ObjectNode answerNode = createAnswerJson(answer);
            answersNode.add(mapper.createObjectNode().set("poll_media", answerNode));
        });
        pollNode.set("answers", answersNode);
        return pollNode;
    }

    @NotNull
    public static ObjectNode createAnswerJson(AnswerData answer) {
        ObjectNode answerNode = mapper.createObjectNode();
        answerNode.put("text", answer.text());
        if (answer.emoji() != null) {
            ObjectNode emojiNode = mapper.createObjectNode();
            switch (answer.emoji()) {
                case CustomEmoji ce -> emojiNode.put("id", ce.getId());
                case DefaultEmoji de -> emojiNode.put("name", de.name());
                default -> throw new IllegalStateException("Unexpected value: " + answer.emoji());
            }
            answerNode.set("emoji", emojiNode);
        }
        return answerNode;
    }
}
