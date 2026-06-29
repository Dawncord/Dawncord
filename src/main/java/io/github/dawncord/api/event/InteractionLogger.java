package io.github.dawncord.api.event;

import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.channel.GuildChannel;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.guild.GuildMember;
import jakarta.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Package-private helper for null-safe debug logging of interaction events. Internal to the
 * {@code event} package — not part of the public API.
 */
final class InteractionLogger {

    private static final Logger logger = LoggerFactory.getLogger(Event.class);

    private InteractionLogger() {
    }

    /**
     * Logs an interaction event at debug level, tolerating an unresolved channel or member
     * (logged as {@code "unknown"} instead of throwing).
     *
     * @param type       The human-readable event type (e.g. {@code "Button"}).
     * @param identifier The command name or component custom id that triggered the event.
     * @param guild      The guild the interaction occurred in.
     * @param channel    The resolved channel, or {@code null} if unavailable.
     * @param member     The resolved member, or {@code null} if unavailable.
     */
    static void log(String type, String identifier, Guild guild, @Nullable GuildChannel channel, @Nullable GuildMember member) {
        if (!logger.isDebugEnabled()) {
            return;
        }
        String channelInfo = channel != null ? channel.getId() + ":" + channel.getName() : "unknown";
        String memberInfo = (member != null && member.getUser() != null)
                ? member.getUser().getId() + ":" + member.getUser().getUsername() : "unknown";
        logger.debug("{} event [{}] -> {} in [{}:{}]:[{}] from [{}]",
                type, identifier, Routes.Reply("{id}", "{token}"),
                guild.getId(), guild.getName(), channelInfo, memberInfo);
    }
}
