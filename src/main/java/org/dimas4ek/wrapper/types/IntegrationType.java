package org.dimas4ek.wrapper.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IntegrationType {
    TWITCH("twitch"),
    DISCORD("discord"),
    YOUTUBE("youtube"),
    GUILD_SUBSCRIPTION("guild_subscription");

    private final String value;
}
