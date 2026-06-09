package io.github.dawncord.api.entities.message.poll;

import io.github.dawncord.api.entities.Emoji;

/**
 * Represents the data of an answer in a poll.
 */
public record AnswerData(String text, Emoji emoji) {
    /**
     * Constructs an AnswerData object with text only.
     *
     * @param text The text of the answer.
     */
    public AnswerData(String text) {
        this(text, null);
    }

    /**
     * Constructs an AnswerData object with text and an emoji.
     *
     * @param text  The text of the answer.
     * @param emoji The emoji of the answer.
     */
    public AnswerData {
    }

    /**
     * Gets the text of the answer.
     *
     * @return The text of the answer.
     */
    @Override
    public String text() {
        return text;
    }

    /**
     * Gets the emoji of the answer.
     *
     * @return The emoji of the answer.
     */
    @Override
    public Emoji emoji() {
        return emoji;
    }
}
