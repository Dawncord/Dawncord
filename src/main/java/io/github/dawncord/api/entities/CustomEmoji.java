package io.github.dawncord.api.entities;

import io.github.dawncord.api.action.EmojiModifyAction;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.guild.role.GuildRole;
import io.github.dawncord.api.event.ModifyEvent;

import java.util.List;
import java.util.function.Consumer;

/**
 * Represents a custom emoji.
 */
public interface CustomEmoji extends Emoji {
    /**
     * Retrieves the guild to which the emoji belongs.
     *
     * @return The guild to which the emoji belongs.
     */
    Guild getGuild();

    /**
     * Retrieves the roles that can use the emoji.
     *
     * @return The roles that can use the emoji.
     */
    List<GuildRole> getRoles();

    /**
     * Retrieves the creator of the emoji.
     *
     * @return The creator of the emoji.
     */
    User getCreator();

    /**
     * Checks if the emoji requires colons to use.
     *
     * @return true if the emoji requires colons, false otherwise.
     */
    boolean isRequiredColons();

    /**
     * Checks if the emoji is managed by an external service.
     *
     * @return true if the emoji is managed, false otherwise.
     */
    boolean isManaged();

    /**
     * Checks if the emoji is animated.
     *
     * @return true if the emoji is animated, false otherwise.
     */
    boolean isAnimated();

    /**
     * Checks if the emoji is available.
     *
     * @return true if the emoji is available, false otherwise.
     */
    boolean isAvailable();

    /**
     * Modifies the emoji.
     *
     * @param handler The consumer for handling the modification action.
     * @return The modify event for the custom emoji.
     */
    ModifyEvent<CustomEmoji> modify(Consumer<EmojiModifyAction> handler);

    /**
     * Deletes the emoji.
     */
    void delete();
}

