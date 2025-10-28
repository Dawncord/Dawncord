package io.github.dawncord.api.command;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.command.option.Option;
import io.github.dawncord.api.types.OptionType;
import io.github.dawncord.api.utils.SlashCommandUtils;

import java.util.List;

/**
 * Represents a sub-command within a command.
 * SubCommand instances define additional commands within a parent command.
 *
 * @see ICommand
 */
public class SubCommand extends Command {
    private final JsonNode subCommand;
    private List<Option> options;

    /**
     * Constructs a new SubCommand instance.
     *
     * @param subCommand The JSON node representing the sub-command.
     */
    public SubCommand(JsonNode subCommand) {
        this.subCommand = subCommand;
        super(subCommand);
    }

    /**
     * Retrieves the type of the sub-command.
     *
     * @return The type of the sub-command.
     */
    public OptionType getType() {
        return OptionType.SUB_COMMAND;
    }

    /**
     * Retrieves the options associated with the sub-command.
     *
     * @return The options associated with the sub-command.
     */
    public List<Option> getOptions() {
        if (options == null) {
            options = SlashCommandUtils.createOptions(subCommand);
        }
        return options;
    }
}
