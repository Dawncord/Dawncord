package io.github.dawncord.api.interaction;

/**
 * Represents data associated with an interaction.
 */
public interface InteractionData {
    /**
     * Retrieves the interaction object associated with this data.
     *
     * @return The interaction object.
     */
    Interaction getInteraction();

    /**
     * Retrieves the ID of the guild where the interaction occurred.
     *
     * @return The ID of the guild.
     */
    String getGuildId();

    /**
     * Retrieves the ID of the channel where the interaction occurred.
     *
     * @return The ID of the channel.
     */
    String getChannelId();

    /**
     * Retrieves the ID of the member who initiated the interaction.
     *
     * @return The ID of the member.
     */
    String getMemberId();
}
