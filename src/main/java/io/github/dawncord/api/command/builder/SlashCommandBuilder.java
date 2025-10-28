package io.github.dawncord.api.command.builder;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.command.SlashCommand;
import io.github.dawncord.api.command.SubCommandGroup;
import io.github.dawncord.api.utils.SlashCommandUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A builder for constructing slash commands.
 * SlashCommandBuilder facilitates the creation of slash commands with various options, sub-commands, and localized details.
 *
 * @see SlashCommand
 */
public class SlashCommandBuilder extends CommandBuilder<SlashCommandBuilder, SlashCommand>
        implements IOptionCommandBuilder<SlashCommandBuilder>, ISubCommandBuilder<SlashCommandBuilder>, ISubCommandGroupBuilder<SlashCommandBuilder> {
    private boolean nsfw;
    private final List<SubCommandGroup> subCommandGroupList = new ArrayList<>();

    /**
     * Constructs a new SlashCommandBuilder instance with the specified name and description.
     *
     * @param name        The name of the slash command.
     * @param description The description of the slash command.
     */
    public SlashCommandBuilder(String name, String description) {
        super(name, description);
    }

    @Override
    public List<SubCommandGroup> getSubCommandGroupList() {
        return subCommandGroupList;
    }

    /**
     * Sets whether the slash command is NSFW (Not Safe For Work).
     *
     * @param nsfw Whether the slash command is NSFW.
     * @return This SlashCommandBuilder instance for method chaining.
     */
    public SlashCommandBuilder setNsfw(boolean nsfw) {
        this.nsfw = nsfw;
        return this;
    }

    @Override
    public SlashCommand build() {
        ObjectNode slashCommand = mapper.createObjectNode()
                .put("name", name)
                .put("description", description)
                .put("nsfw", nsfw);
        SlashCommandUtils.createDefaults(slashCommand, nameLocalizations, descriptionLocalizations, optionList);
        if (!subCommandList.isEmpty()) {
            SlashCommandUtils.createSubCommandsArray(slashCommand, subCommandList);
        }
        if (!subCommandGroupList.isEmpty()) {
            SlashCommandUtils.createSubCommandGroupArray(slashCommand, subCommandGroupList);
        }
        return new SlashCommand(slashCommand);
    }
}
