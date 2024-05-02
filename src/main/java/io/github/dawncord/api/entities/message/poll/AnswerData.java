package io.github.dawncord.api.entities.message.poll;

import io.github.dawncord.api.entities.Emoji;

/**
 * Represents the data of an answer in a poll.
 */
public class AnswerData {
    private final String text;
    private final Emoji emoji;

    /**
     * Constructs an AnswerData object with text only.
     *
     * @param text The text of the answer.
     */
    public AnswerData(String text) {
        this.text = text;
        this.emoji = null;
    }

    /**
     * Constructs an AnswerData object with text and an emoji.
     *
     * @param text  The text of the answer.
     * @param emoji The emoji of the answer.
     */
    public AnswerData(String text, Emoji emoji) {
        this.text = text;
        this.emoji = emoji;
    }

    /**
     * Gets the text of the answer.
     *
     * @return The text of the answer.
     */
    public String getText() {
        return text;
    }

    /**
     * Gets the emoji of the answer.
     *
     * @return The emoji of the answer.
     */
    public Emoji getEmoji() {
        return emoji;
    }
}
