package io.github.dawncord.api.entities.message.poll;

import java.util.List;

/**
 * Represents the data of a poll.
 */
public class PollData {
    private final String question;
    private final List<AnswerData> answers;
    private final int duration;
    private final boolean allowMultiselect;

    /**
     * Constructs a PollData object with the specified parameters.
     *
     * @param question         The question of the poll.
     * @param answers          The list of answer data.
     * @param duration         The duration of the poll.
     * @param allowMultiselect Whether multiselect is allowed in the poll.
     */
    public PollData(String question, List<AnswerData> answers, int duration, boolean allowMultiselect) {
        this.question = question;
        this.answers = answers;
        this.duration = duration;
        this.allowMultiselect = allowMultiselect;
    }

    /**
     * Gets the question of the poll.
     *
     * @return The question of the poll.
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Gets the list of answer data.
     *
     * @return The list of answer data.
     */
    public List<AnswerData> getAnswers() {
        return answers;
    }

    /**
     * Gets the duration of the poll.
     *
     * @return The duration of the poll.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Checks if multiselect is allowed in the poll.
     *
     * @return True if multiselect is allowed, false otherwise.
     */
    public boolean isAllowMultiselect() {
        return allowMultiselect;
    }
}
