package org.dimas4ek.wrapper.entities.message.poll;

import org.dimas4ek.wrapper.entities.Emoji;

public class AnswerData {
    private final String text;
    private final Emoji emoji;

    public AnswerData(String text) {
        this.text = text;
        this.emoji = null;
    }

    public AnswerData(String text, Emoji emoji) {
        this.text = text;
        this.emoji = emoji;
    }

    public String getText() {
        return text;
    }

    public Emoji getEmoji() {
        return emoji;
    }
}
