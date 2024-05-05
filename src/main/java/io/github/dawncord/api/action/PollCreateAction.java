package io.github.dawncord.api.action;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.CustomEmoji;
import io.github.dawncord.api.entities.DefaultEmoji;
import io.github.dawncord.api.entities.message.poll.AnswerData;
import io.github.dawncord.api.entities.message.poll.Poll;

/**
 * Represents an action for creating a poll.
 *
 * @see Poll
 */
public class PollCreateAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final String channelId;
    private boolean hasChanges = false;
    private String createdId;

    /**
     * Create a new {@link PollCreateAction}
     *
     * @param channelId The ID of the channel where the poll will be created.
     */
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

    /**
     * Sets the question text for the poll.
     *
     * @param text the text of the question
     * @return the modified PollCreateAction object
     */
    public PollCreateAction setQuestion(String text) {
        return setProperty("question", mapper.createObjectNode().put("text", text));
    }

    /**
     * Sets the answers for the poll.
     *
     * @param answers an array of AnswerData objects representing the answers for the poll
     * @return the modified PollCreateAction object
     */
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

    /**
     * Sets the duration of the poll in hours.
     *
     * @param hours the duration of the poll in hours
     * @return the modified PollCreateAction object
     */
    public PollCreateAction setDuration(int hours) {
        return setProperty("duration", hours);
    }

    /**
     * Sets the multi-select setting for the poll.
     *
     * @param allowMultiselect a boolean indicating whether multiple selections are allowed
     * @return the modified PollCreateAction object
     */
    public PollCreateAction setAllowMultiselect(boolean allowMultiselect) {
        return setProperty("allow_multiselect", allowMultiselect);
    }

    private String getCreatedId() {
        return createdId;
    }

    private void submit() {
        if (hasChanges) {
            JsonNode jsonNode = ApiClient.post(jsonObject, Routes.Channel.Message.All(channelId));
            if (jsonNode != null && jsonNode.has("id")) {
                createdId = jsonNode.get("id").asText();
            }
            hasChanges = false;
        }
    }
}
