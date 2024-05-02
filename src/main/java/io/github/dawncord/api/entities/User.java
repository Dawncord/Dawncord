package io.github.dawncord.api.entities;

import io.github.dawncord.api.entities.image.Avatar;
import io.github.dawncord.api.types.NitroType;
import io.github.dawncord.api.types.UserFlag;

import java.util.List;

/**
 * Represents a user in Discord.
 */
public interface User extends ISnowflake, IMentionable {

    /**
     * Retrieves the global name of the user.
     *
     * @return The global name  of the user.
     */
    String getGlobalName();

    /**
     * Retrieves the username of the user.
     *
     * @return The username of the user.
     */
    String getUsername();

    /**
     * Retrieves the avatar of the user.
     *
     * @return The avatar of the user.
     */
    Avatar getAvatar();

    /**
     * Retrieves the list of user flags.
     *
     * @return The list of user flags.
     */
    List<UserFlag> getFlags();

    /**
     * Retrieves the list of public user flags.
     *
     * @return The list of public user flags.
     */
    List<UserFlag> getPublicFlags();

    /**
     * Checks if the user has Discord Nitro subscription.
     *
     * @return True if the user has Discord Nitro, false otherwise.
     */
    boolean hasNitro();

    /**
     * Retrieves the type of Nitro subscription the user has.
     *
     * @return The type of Nitro subscription the user has.
     */
    NitroType getNitroType();

    /**
     * Checks if the user is a bot account.
     *
     * @return True if the user is a bot account, false otherwise.
     */
    boolean isBot();
}
