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
    private static ObjectNode createAnswerJson(AnswerData answer) {
        ObjectNode answerNode = mapper.createObjectNode();
        answerNode.put("text", answer.text());
        if (answer.emoji() != null) {
            if (answer.emoji() instanceof CustomEmoji customEmoji) {
                answerNode.put("emoji", customEmoji.getId());
            } else if (answer.emoji() instanceof DefaultEmoji(String name)) {
                answerNode.put("emoji", name);
            }
        }
        return answerNode;
    }
}
