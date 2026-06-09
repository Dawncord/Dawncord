package io.github.dawncord.api.entities.message.poll;

import io.github.dawncord.api.entities.Emoji;

/**
 * Represents the data of an answer in a poll.
 */
public record AnswerData(String text, Emoji emoji) {
}
