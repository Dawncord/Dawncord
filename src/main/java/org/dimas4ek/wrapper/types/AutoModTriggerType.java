package org.dimas4ek.wrapper.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AutoModTriggerType {
    KEYWORD(1, "check if content contains words from a user defined list of keywords", 6),
    SPAM(3, "check if content represents generic spam", 1),
    KEYWORD_PRESET(4, "check if content contains words from internal pre-defined wordsets", 1),
    MENTION_SPAM(5, "check if content contains more unique mentions than allowed", 1);

    private final int value;
    private final String description;
    private final int maxPerGuild;
}
