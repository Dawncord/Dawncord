package org.dimas4ek.wrapper.entities.message.poll;

import com.fasterxml.jackson.databind.JsonNode;

public class AnswerCount {
    private final JsonNode answerCount;
    private String id;
    private Integer count;
    private Boolean meVoted;

    public AnswerCount(JsonNode answerCount) {
        this.answerCount = answerCount;
    }

    public String getId() {
        if (id == null) {
            id = answerCount.get("id").asText();
        }
        return id;
    }

    public int getCount() {
        if (count == null) {
            count = answerCount.get("count").asInt();
        }
        return count;
    }

    public boolean isMeVoted() {
        if (meVoted == null) {
            meVoted = answerCount.get("me_voted").asBoolean();
        }
        return meVoted;
    }
}
