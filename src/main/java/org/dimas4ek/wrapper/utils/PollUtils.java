package org.dimas4ek.wrapper.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.dimas4ek.wrapper.entities.CustomEmoji;
import org.dimas4ek.wrapper.entities.DefaultEmoji;
import org.dimas4ek.wrapper.entities.message.poll.PollData;

public class PollUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static JsonNode createPoll(PollData poll) {
        ObjectNode pollNode = mapper.createObjectNode();
        pollNode.set("question", mapper.createObjectNode().put("text", poll.getQuestion()));
        pollNode.put("allow_multiselect", poll.isAllowMultiselect());
        pollNode.put("duration", poll.getDuration());
        ArrayNode answersNode = mapper.createArrayNode();
        poll.getAnswers().forEach(answer -> {
            ObjectNode answerNode = mapper.createObjectNode();
            answerNode.put("text", answer.getText());
            if (answer.getEmoji() != null) {
                if (answer.getEmoji() instanceof CustomEmoji customEmoji) {
                    answerNode.put("emoji", customEmoji.getId());
                } else if (answer.getEmoji() instanceof DefaultEmoji defaultEmoji) {
                    answerNode.put("emoji", defaultEmoji.getName());
                }
            }
            answersNode.add(mapper.createObjectNode().set("poll_media", answerNode));
        });
        pollNode.set("answers", answersNode);
        return pollNode;
    }
}
