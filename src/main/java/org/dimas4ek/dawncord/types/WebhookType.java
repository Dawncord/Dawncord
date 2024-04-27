package org.dimas4ek.dawncord.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WebhookType {
    Incoming(1, "Incoming Webhooks can post messages to channels with a generated token"),
    Channel(2, "Follower	Channel Follower Webhooks are internal webhooks used with Channel Following to post new messages into channels"),
    Application(3, "Application webhooks are webhooks used with Interactions");

    private final int value;
    private final String description;
}
