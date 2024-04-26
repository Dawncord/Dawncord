package org.dimas4ek.wrapper.entities.message.poll;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.wrapper.utils.JsonUtils;

import java.util.List;

public class Result {
    private final JsonNode result;
    private Boolean finalized;
    private List<AnswerCount> answerCounts;

    public Result(JsonNode result) {
        this.result = result;
    }

    public boolean isFinalized() {
        if (finalized == null) {
            finalized = result.get("is_finalized").asBoolean();
        }
        return finalized;
    }

    public List<AnswerCount> getAnswerCounts() {
        if (answerCounts == null) {
            answerCounts = JsonUtils.getEntityList(result.get("answer_count"), AnswerCount::new);
        }
        return answerCounts;
    }
}
