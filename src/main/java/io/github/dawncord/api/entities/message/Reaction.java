package io.github.dawncord.api.entities.message;

import io.github.dawncord.api.entities.Emoji;

import java.util.List;

/**
 * Represents a reaction to a message.
 */
public interface Reaction {
    /**
     * Gets the total count of reactions.
     *
     * @return The total count of reactions.
     */
    int getTotal();

    /**
     * Gets the count of normal reactions.
     *
     * @return The count of normal reactions.
     */
    int getNormalCount();

    /**
     * Gets the count of burst reactions.
     *
     * @return The count of burst reactions.
     */
    int getBurstCount();

    /**
     * Checks if the current user has reacted with this emoji.
     *
     * @return true if the current user has reacted, false otherwise.
     */
    boolean isMe();

    /**
     * Checks if the current user has reacted with this emoji in burst mode.
     *
     * @return true if the current user has reacted in burst mode, false otherwise.
     */
    boolean isMeBurst();

    /**
     * Gets the guild emoji associated with this reaction.
     *
     * @return The guild emoji associated with this reaction
     */
    Emoji getGuildEmoji();

    /**
     * Checks if this reaction is a guild emoji.
     *
     * @return true if the reaction is a guild emoji, false otherwise.
     */
    boolean isGuildEmoji();

    /**
     * Gets the emoji string associated with this reaction.
     *
     * @return The emoji string associated with this reaction.
     */
    String getEmoji();

    /**
     * Gets the list of burst colors associated with this reaction.
     *
     * @return The list of burst colors associated with this reaction.
     */
    List<String> getBurstColors();

    /**
     * Deletes the user's reaction.
     *
     * @param userId The ID of the user whose reaction is to be deleted.
     */
    void delete(String userId);

    /**
     * Deletes the user's reaction.
     *
     * @param userId The ID of the user whose reaction is to be deleted.
     */
    void delete(long userId);
}
