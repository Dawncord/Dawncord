package io.github.dawncord.api.command;

import io.github.dawncord.api.action.SlashCommandModifyAction;
import io.github.dawncord.api.command.option.Option;
import io.github.dawncord.api.types.CommandType;
import io.github.dawncord.api.types.PermissionType;

import java.util.List;
import java.util.function.Consumer;

/**
 * Represents a slash command in the Discord API.
 */
public interface ISlashCommand extends Command {
    /**
     * Gets the ID of the slash command.
     *
     * @return The ID of the slash command
     */
    String getId();

    /**
     * Gets the ID of the slash command as a long.
     *
     * @return The ID of the slash command as a long
     */
    long getIdLong();

    /**
     * Gets the type of the slash command.
     *
     * @return The type of the slash command
     */
    CommandType getType();

    /**
     * Gets the ID of the application that owns the slash command.
     *
     * @return The ID of the application that owns the slash command
     */
    String getApplicationId();

    /**
     * Gets the ID of the application that owns the slash command as a long.
     *
     * @return The ID of the application that owns the slash command as a long
     */
    long getApplicationIdLong();

    /**
     * Gets the permissions required by the slash command to be executed by members.
     *
     * @return The permissions required by the slash command to be executed by members
     */
    List<PermissionType> getMemberPermissions();

    /**
     * Checks if the slash command is marked as NSFW (Not Safe For Work).
     *
     * @return True if the slash command is marked as NSFW, false otherwise
     */
    boolean isNsfw();

    /**
     * Gets the version of the slash command.
     *
     * @return The version of the slash command
     */
    String getVersion();

    /**
     * Gets the options of the slash command.
     *
     * @return The options of the slash command
     */
    List<Option> getOptions();

    /**
     * Gets the subcommands of the slash command.
     *
     * @return The subcommands of the slash command
     */
    List<SubCommand> getSubCommands();

    /**
     * Gets the subcommand groups of the slash command.
     *
     * @return The subcommand groups of the slash command
     */
    List<SubCommandGroup> getSubCommandGroups();

    /**
     * Modifies the slash command using the provided handler.
     *
     * @param handler The handler to modify the slash command
     */
    void modify(Consumer<SlashCommandModifyAction> handler);

    /**
     * Deletes the slash command.
     */
    void delete();
}
