package io.github.dawncord.api.entities.message.poll;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.utils.JsonUtils;
import io.github.dawncord.api.utils.TimeUtils;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Represents an implementation of the Poll interface.
 */
public class Poll {
    private final JsonNode poll;
    private final Guild guild;
    private String question;
    private List<Answer> answers;
    private ZonedDateTime expiry;
    private Boolean multiselect;
    private List<Result> results;

    /**
     * Constructs a new PollImpl instance.
     *
     * @param poll  The JSON representation of the poll.
     * @param guild The guild associated with the poll.
     */
    public Poll(JsonNode poll, Guild guild) {
        this.poll = poll;
        this.guild = guild;
    }

    public String getQuestion() {
        if (question == null) {
            question = poll.get("question").get("text").asText();
        }
        return question;
    }

    public List<Answer> getAnswers() {
        if (answers == null) {
            answers = JsonUtils.getEntityList(poll.get("answers"), answer -> new Answer(answer, guild));
        }
        return answers;
    }

    public ZonedDateTime getExpiry() {
        if (expiry == null) {
            if (poll.has("expiry")) {
                expiry = TimeUtils.getZonedDateTime(poll, "expiry");
            }
        }
        return expiry;
    }

    public boolean isExpired() {
        return expiry != null && expiry.isBefore(ZonedDateTime.now());
    }

    public boolean isMultiselect() {
        if (multiselect == null) {
            multiselect = poll.get("allow_multiselect").asBoolean();
        }
        return multiselect;
    }

    public List<Result> getResults() {
        if (results == null) {
            results = JsonUtils.getEntityList(poll.get("results"), Result::new);
        }
        return results;
    }
}
