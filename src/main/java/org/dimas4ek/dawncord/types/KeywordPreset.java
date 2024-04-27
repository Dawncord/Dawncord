package org.dimas4ek.dawncord.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KeywordPreset {
    PROFANITY(1, "words that may be considered forms of swearing or cursing"),
    SEXUAL_CONTENT(2, "words that refer to sexually explicit behavior or activity"),
    SLURS(3, "personal insults or words that may be considered hate speech");

    private final int value;
    private final String description;
}
