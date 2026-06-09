package io.github.dawncord.api.command;

/**
 * Represents the data associated with a slash command, including its name, subcommand, and subcommand group.
 */
public record SlashCommandData(String name, String subCommand, String subCommandGroup) {
    /**
     * Constructs a new SlashCommandData object with the specified parameters.
     *
     * @param name            The name of the slash command.
     * @param subCommand      The subcommand of the slash command.
     * @param subCommandGroup The subcommand group of the slash command.
     */
    public SlashCommandData {
    }

    /**
     * Gets the name of the slash command.
     *
     * @return The name of the slash command.
     */
    @Override
    public String name() {
        return name;
    }

    /**
     * Gets the subcommand of the slash command.
     *
     * @return The subcommand of the slash command.
     */
    @Override
    public String subCommand() {
        return subCommand;
    }

    /**
     * Gets the subcommand group of the slash command.
     *
     * @return The subcommand group of the slash command.
     */
    @Override
    public String subCommandGroup() {
        return subCommandGroup;
    }
}
