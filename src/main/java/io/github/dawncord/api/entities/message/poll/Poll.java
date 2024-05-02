package io.github.dawncord.api.entities.message.poll;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Represents a poll with a question, answers, expiry time, and results.
 */
public interface Poll {
    /**
     * Gets the question of the poll.
     *
     * @return The question of the poll.
     */
    String getQuestion();

    /**
     * Gets the list of answers for the poll.
     *
     * @return The list of answers for the poll.
     */
    List<Answer> getAnswers();

    /**
     * Gets the expiry time of the poll.
     *
     * @return The expiry time of the poll.
     */
    ZonedDateTime getExpiry();

    /**
     * Checks if the poll is expired.
     *
     * @return True if the poll is expired, false otherwise.
     */
    boolean isExpired();

    /**
     * Checks if the poll allows multiple selections.
     *
     * @return True if the poll allows multiple selections, false otherwise.
     */
    boolean isMultiselect();

    /**
     * Gets the results of the poll.
     *
     * @return The results of the poll.
     */
    List<Result> getResults();
}
