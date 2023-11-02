package org.dimas4ek.wrapper.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageFlag {
    ROSSPOSTED(1 << 0, "This message has been published to subscribed channels (via Channel Following)"),
    IS_CROSSPOST(1 << 1, "This message originated from a message in another channel (via Channel Following)"),
    SUPPRESS_EMBEDS(1 << 2, "Do not include any embeds when serializing this message"),
    SOURCE_MESSAGE_DELETED(1 << 3, "The source message for this crosspost has been deleted (via Channel Following)"),
    URGENT(1 << 4, "This message came from the urgent message system"),
    HAS_THREAD(1 << 5, "This message has an associated thread, with the same id as the message"),
    EPHEMERAL(1 << 6, "This message is only visible to the user who invoked the Interaction"),
    LOADING(1 << 7, "This message is an Interaction Response, and the bot is 'thinking'"),
    FAILED_TO_MENTION_SOME_ROLES_IN_THREAD(1 << 8, "This message failed to mention some roles and add their members to the thread"),
    SUPPRESS_NOTIFICATIONS(1 << 12, "This message will not trigger push and desktop notifications"),
    IS_VOICE_MESSAGE(1 << 13, "This message is a voice message");

    private final int value;
    private final String description;

}
