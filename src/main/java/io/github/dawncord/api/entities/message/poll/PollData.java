package io.github.dawncord.api.entities.message.poll;

import java.util.List;

/**
 * Represents the data of a poll.
 */
public record PollData(String question, List<AnswerData> answers, int duration, boolean allowMultiselect) {
}
