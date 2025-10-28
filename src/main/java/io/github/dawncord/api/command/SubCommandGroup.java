package io.github.dawncord.api.command;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.types.OptionType;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a sub-command group.
 * SubCommandGroup encapsulates the details of a group of sub-commands within a slash command.
 *
 * @see SubCommand
 */
public class SubCommandGroup extends Command {
    private final JsonNode subCommandGroup;
    private List<SubCommand> subCommandList;

    /**
     * Constructs a new SubCommandGroup instance with the provided JSON node.
     *
     * @param subCommandGroup The JSON node representing the sub-command group.
     */
    public SubCommandGroup(JsonNode subCommandGroup) {
        this.subCommandGroup = subCommandGroup;
        super(subCommandGroup);
    }

    /**
     * Retrieves the type of the sub-command group.
     *
     * @return The type of the sub-command group.
     */
    public OptionType getType() {
        return OptionType.SUB_COMMAND_GROUP;
    }

    /**
     * Retrieves the list of sub-commands within the sub-command group.
     *
     * @return The list of sub-commands.
     */
    public List<SubCommand> getSubCommands() {
        if (subCommandList == null) {
            subCommandList = new ArrayList<>();
            if (subCommandGroup.has("options") && subCommandGroup.hasNonNull("options")) {
                for (JsonNode subCommand : subCommandGroup.get("options")) {
                    subCommandList.add(new SubCommand(subCommand));
                }
            }
        }
        return subCommandList;
    }
}
