package io.github.dawncord.api.command;

/**
 * Represents the data associated with a slash command, including its name, subcommand, and subcommand group.
 */
public class SlashCommandData {
    private final String name;
    private final String subCommand;
    private final String subCommandGroup;

    /**
     * Constructs a new SlashCommandData object with the specified parameters.
     *
     * @param name            The name of the slash command.
     * @param subCommand      The subcommand of the slash command.
     * @param subCommandGroup The subcommand group of the slash command.
     */
    public SlashCommandData(String name, String subCommand, String subCommandGroup) {
        this.name = name;
        this.subCommand = subCommand;
        this.subCommandGroup = subCommandGroup;
    }

    /**
     * Gets the name of the slash command.
     *
     * @return The name of the slash command.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the subcommand of the slash command.
     *
     * @return The subcommand of the slash command.
     */
    public String getSubCommand() {
        return subCommand;
    }

    /**
     * Gets the subcommand group of the slash command.
     *
     * @return The subcommand group of the slash command.
     */
    public String getSubCommandGroup() {
        return subCommandGroup;
    }
}
