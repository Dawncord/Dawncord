package org.dimas4ek.wrapper.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.Routes;
import org.dimas4ek.wrapper.entities.CustomEmoji;
import org.dimas4ek.wrapper.entities.DefaultEmoji;
import org.dimas4ek.wrapper.entities.message.poll.AnswerData;

public class PollCreateAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final String channelId;
    private boolean hasChanges = false;

    public PollCreateAction(String channelId) {
        this.jsonObject = mapper.createObjectNode();
        this.jsonObject.set("poll", mapper.createObjectNode());
        this.channelId = channelId;
    }

    private PollCreateAction setProperty(String key, Object value) {
        ((ObjectNode) jsonObject.get("poll")).set(key, mapper.valueToTree(value));
        hasChanges = true;
        return this;
    }


    public PollCreateAction setQuestion(String text) {
        return setProperty("question", mapper.createObjectNode().put("text", text));
    }

    public PollCreateAction setAnswers(AnswerData... answers) {
        ArrayNode answersNode = mapper.createArrayNode();
        for (AnswerData answer : answers) {
            ObjectNode answerNode = mapper.createObjectNode();
            answerNode.put("text", answer.getText());
            if (answer.getEmoji() != null) {
                ObjectNode emojiNode = mapper.createObjectNode();
                if (answer.getEmoji() instanceof CustomEmoji customEmoji) {
                    emojiNode.put("id", customEmoji.getId());
                } else if (answer.getEmoji() instanceof DefaultEmoji defaultEmoji) {
                    emojiNode.put("name", defaultEmoji.getName());
                }
                answerNode.set("emoji", emojiNode);
            }
            answersNode.add(mapper.createObjectNode().set("poll_media", answerNode));
        }
        return setProperty("answers", answersNode);
    }

    public PollCreateAction setDuration(int hours) {
        return setProperty("duration", hours);
    }

    public PollCreateAction setAllowMultiselect(boolean allowMultiselect) {
        return setProperty("allow_multiselect", allowMultiselect);
    }

    private void submit() {
        System.out.println(jsonObject.toPrettyString());
        if (hasChanges) {
            ApiClient.post(jsonObject, Routes.Channel.Message.All(channelId));
            hasChanges = false;
        }
        jsonObject.removeAll();
    }
}
