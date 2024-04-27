package org.dimas4ek.dawncord.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ButtonStyle {
    Primary(1),
    Secondary(2),
    Success(3),
    Danger(4),
    Link(5);

    private final int value;
}
