package org.dimas4ek.dawncord.entities.message.poll;

import java.util.List;

public class PollData {
    private final String question;
    private final List<AnswerData> answers;
    private final int duration;
    private final boolean allowMultiselect;

    public PollData(String question, List<AnswerData> answers, int duration, boolean allowMultiselect) {
        this.question = question;
        this.answers = answers;
        this.duration = duration;
        this.allowMultiselect = allowMultiselect;
    }

    public String getQuestion() {
        return question;
    }

    public List<AnswerData> getAnswers() {
        return answers;
    }

    public int getDuration() {
        return duration;
    }

    public boolean isAllowMultiselect() {
        return allowMultiselect;
    }
}
