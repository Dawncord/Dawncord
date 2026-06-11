package io.github.dawncord.api.types;

/**
 * Represents Discord Gateway close event codes.
 */
public enum GatewayCloseEventCode {
    UNKNOWN_ERROR(4000, "We're not sure what went wrong. Try reconnecting?", true),
    UNKNOWN_OPCODE(4001, "You sent an invalid Gateway opcode or an invalid payload for an opcode. Don't do that!", true),
    DECODE_ERROR(4002, "You sent an invalid payload to Discord. Don't do that!", true),
    NOT_AUTHENTICATED(4003, "You sent us a payload prior to identifying, or this session has been invalidated.", true),
    AUTHENTICATION_FAILED(4004, "The account token sent with your identify payload is incorrect.", false),
    ALREADY_AUTHENTICATED(4005, "You sent more than one identify payload. Don't do that!", true),
    INVALID_SEQ(4007, "The sequence sent when resuming the session was invalid. Reconnect and start a new session.", true),
    RATE_LIMITED(4008, "Woah nelly! You're sending payloads to us too quickly. Slow it down! You will be disconnected on receiving this.", true),
    SESSION_TIMEOUT(4009, "Your session timed out. Reconnect and start a new one.", true),
    INVALID_SHARD(4010, "You sent us an invalid shard when identifying.", false),
    SHARDING_REQUIRED(4011, "The session would have handled too many guilds - you are required to shard your connection in order to connect.", false),
    INVALID_API_VERSION(4012, "You sent an invalid version for the gateway.", false),
    INVALID_INTENTS(4013, "You sent an invalid intent for a Gateway Intent. You may have incorrectly calculated the bitwise value.", false),
    DISALLOWED_INTENTS(4014, "You sent a disallowed intent for a Gateway Intent. You may have tried to specify an intent that you have not enabled or are not approved for.", false);

    private final int code;
    private final String explanation;
    private final boolean reconnect;

    GatewayCloseEventCode(int code, String explanation, boolean reconnect) {
        this.code = code;
        this.explanation = explanation;
        this.reconnect = reconnect;
    }

    public int getCode() {
        return code;
    }

    public String getExplanation() {
        return explanation;
    }

    public boolean isReconnect() {
        return reconnect;
    }
}
