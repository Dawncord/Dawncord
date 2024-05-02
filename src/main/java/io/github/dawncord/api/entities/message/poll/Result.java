package io.github.dawncord.api.entities.message.poll;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.utils.JsonUtils;

import java.util.List;

/**
 * Represents the result of a poll.
 */
public class Result {
    private final JsonNode result;
    private Boolean finalized;
    private List<AnswerCount> answerCounts;

    /**
     * Constructs a Result object with the specified JSON node.
     *
     * @param result The JSON node representing the result of the poll.
     */
    public Result(JsonNode result) {
        this.result = result;
    }

    /**
     * Checks if the poll result is finalized.
     *
     * @return True if the poll result is finalized, false otherwise.
     */
    public boolean isFinalized() {
        if (finalized == null) {
            finalized = result.get("is_finalized").asBoolean();
        }
        return finalized;
    }

    /**
     * Gets the list of answer counts.
     *
     * @return The list of answer counts.
     */
    public List<AnswerCount> getAnswerCounts() {
        if (answerCounts == null) {
            answerCounts = JsonUtils.getEntityList(result.get("answer_count"), AnswerCount::new);
        }
        return answerCounts;
    }
}
