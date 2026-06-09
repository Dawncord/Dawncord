package io.github.dawncord.api.entities.message.poll;

import java.util.List;

/**
 * Represents the data of a poll.
 */
public record PollData(String question, List<AnswerData> answers, int duration, boolean allowMultiselect) {
    /**
     * Constructs a PollData object with the specified parameters.
     *
     * @param question         The question of the poll.
     * @param answers          The list of answer data.
     * @param duration         The duration of the poll.
     * @param allowMultiselect Whether multiselect is allowed in the poll.
     */
    public PollData {
    }

    /**
     * Gets the question of the poll.
     *
     * @return The question of the poll.
     */
    @Override
    public String question() {
        return question;
    }

    /**
     * Gets the list of answer data.
     *
     * @return The list of answer data.
     */
    @Override
    public List<AnswerData> answers() {
        return answers;
    }

    /**
     * Gets the duration of the poll.
     *
     * @return The duration of the poll.
     */
    @Override
    public int duration() {
        return duration;
    }

    /**
     * Checks if multiselect is allowed in the poll.
     *
     * @return True if multiselect is allowed, false otherwise.
     */
    @Override
    public boolean allowMultiselect() {
        return allowMultiselect;
    }
}
