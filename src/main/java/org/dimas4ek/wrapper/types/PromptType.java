package org.dimas4ek.wrapper.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PromptType {
    MULTIPLE_CHOICE(0),
    DROPDOWN(1);

    private final int value;
}
