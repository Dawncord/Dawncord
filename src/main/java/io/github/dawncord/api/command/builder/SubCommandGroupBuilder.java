package io.github.dawncord.api.command.builder;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.command.SubCommandGroup;
import io.github.dawncord.api.utils.SlashCommandUtils;

/**
 * Builder class for constructing sub-command groups.
 * SubCommandGroupBuilder provides a convenient way to construct sub-command groups with various properties.
 *
 * @see SubCommandGroup
 * @see CommandBuilder
 */
public class SubCommandGroupBuilder extends CommandBuilder<SubCommandGroupBuilder, SubCommandGroup>
        implements ISubCommandBuilder<SubCommandGroupBuilder> {
    /**
     * Constructs a new SubCommandGroupBuilder with the provided name and description.
     *
     * @param name        The name of the sub-command group.
     * @param description The description of the sub-command group.
     */
    public SubCommandGroupBuilder(String name, String description) {
        super(name, description);
    }

    @Override
    public SubCommandGroup build() {
        ObjectNode subCommandGroup = mapper.createObjectNode()
                .put("name", name)
                .put("description", description);
        SlashCommandUtils.createLocalizedLists(subCommandGroup, nameLocalizations, descriptionLocalizations);
        if (!subCommandList.isEmpty()) {
            SlashCommandUtils.createSubCommandsArray(subCommandGroup, subCommandList);
        }
        return new SubCommandGroup(subCommandGroup);
    }
}
