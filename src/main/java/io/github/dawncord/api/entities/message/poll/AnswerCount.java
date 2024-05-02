package io.github.dawncord.api.entities.message.poll;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Represents the count of answers in a poll.
 */
public class AnswerCount {
    private final JsonNode answerCount;
    private String id;
    private Integer count;
    private Boolean meVoted;

    /**
     * Constructs an AnswerCount object.
     *
     * @param answerCount The JSON representation of the answer count.
     */
    public AnswerCount(JsonNode answerCount) {
        this.answerCount = answerCount;
    }

    /**
     * Gets the ID of the answer.
     *
     * @return The ID of the answer.
     */
    public String getId() {
        if (id == null) {
            id = answerCount.get("id").asText();
        }
        return id;
    }

    /**
     * Gets the count of the answer.
     *
     * @return The count of the answer.
     */
    public int getCount() {
        if (count == null) {
            count = answerCount.get("count").asInt();
        }
        return count;
    }

    /**
     * Checks if the current user has voted for this answer.
     *
     * @return True if the current user has voted for this answer, false otherwise.
     */
    public boolean isMeVoted() {
        if (meVoted == null) {
            meVoted = answerCount.get("me_voted").asBoolean();
        }
        return meVoted;
    }
}
