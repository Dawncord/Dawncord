package org.dimas4ek.dawncord.entities.message.poll;

import java.time.ZonedDateTime;
import java.util.List;

public interface Poll {
    String getQuestion();

    List<Answer> getAnswers();

    ZonedDateTime getExpiry();

    boolean isExpired();

    boolean isMultiselect();

    List<Result> getResults();
}
