package io.github.dawncord.api.command.builder;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.command.SubCommand;
import io.github.dawncord.api.types.OptionType;
import io.github.dawncord.api.utils.SlashCommandUtils;

/**
 * A builder for constructing sub-commands.
 * SubCommandBuilder facilitates the creation of sub-commands with various options and localized details.
 *
 * @see SubCommand
 */
public class SubCommandBuilder extends CommandBuilder<SubCommandBuilder, SubCommand>
        implements IOptionCommandBuilder<SubCommandBuilder> {
    /**
     * Constructs a new SubCommandBuilder instance with the specified name and description.
     *
     * @param name        The name of the sub-command.
     * @param description The description of the sub-command.
     */
    public SubCommandBuilder(String name, String description) {
        super(name, description);
    }

    @Override
    public SubCommand build() {
        ObjectNode subCommand = mapper.createObjectNode()
                .put("type", OptionType.SUB_COMMAND.getValue())
                .put("name", name)
                .put("description", description);
        SlashCommandUtils.createDefaults(subCommand, nameLocalizations, descriptionLocalizations, optionList);
        return new SubCommand(subCommand);
    }
}
