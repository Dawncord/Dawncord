package org.dimas4ek.wrapper.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OnboardingMode {
    ONBOARDING_DEFAULT(0, "Counts only Default Channels towards constraints"),
    ONBOARDING_ADVANCED(1, "Counts Default Channels and Questions towards constraints");

    private final int value;
    private final String description;
}
