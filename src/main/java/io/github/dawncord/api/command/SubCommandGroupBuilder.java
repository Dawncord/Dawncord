package io.github.dawncord.api.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.types.Locale;
import io.github.dawncord.api.utils.SlashCommandUtils;

import java.util.*;

/**
 * Builder class for constructing sub-command groups.
 * SubCommandGroupBuilder provides a convenient way to construct sub-command groups with various properties.
 *
 * @see SubCommandGroup
 * @see CommandBuilder
 */
public class SubCommandGroupBuilder implements CommandBuilder {
    private static final ObjectMapper mapper = new ObjectMapper();
    private String name;
    private String description;
    private final List<SubCommand> subCommandList = new ArrayList<>();
    private final Map<Locale, String> localizedNameList = new EnumMap<>(Locale.class);
    private final Map<Locale, String> localizedDescriptionList = new EnumMap<>(Locale.class);

    /**
     * Constructs a new SubCommandGroupBuilder with the provided name and description.
     *
     * @param name        The name of the sub-command group.
     * @param description The description of the sub-command group.
     */
    public SubCommandGroupBuilder(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public SubCommandGroupBuilder setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public SubCommandGroupBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public SubCommandGroupBuilder setLocalizedName(Locale locale, String name) {
        localizedNameList.put(locale, name);
        return this;
    }

    @Override
    public SubCommandGroupBuilder setLocalizedNames(Map<Locale, String> localizedNames) {
        localizedNameList.putAll(localizedNames);
        return this;
    }

    @Override
    public SubCommandGroupBuilder setLocalizedDescription(Locale locale, String description) {
        localizedDescriptionList.put(locale, description);
        return this;
    }

    @Override
    public SubCommandGroupBuilder setLocalizedDescriptions(Map<Locale, String> localizedDescriptions) {
        localizedDescriptionList.putAll(localizedDescriptions);
        return this;
    }

    /**
     * Adds a sub-command to the sub-command group.
     *
     * @param subCommand The sub-command to add.
     * @return This SubCommandGroupBuilder instance for method chaining.
     */
    public SubCommandGroupBuilder addSubCommand(SubCommand subCommand) {
        subCommandList.add(subCommand);
        return this;
    }

    /**
     * Adds multiple sub-commands to the sub-command group.
     *
     * @param subCommands The array of sub-commands to add.
     * @return This SubCommandGroupBuilder instance for method chaining.
     */
    public SubCommandGroupBuilder addSubCommands(SubCommand... subCommands) {
        Collections.addAll(subCommandList, subCommands);
        return this;
    }

    /**
     * Adds a list of sub-commands to the sub-command group.
     *
     * @param subCommands The list of sub-commands to add.
     * @return This SubCommandGroupBuilder instance for method chaining.
     */
    public SubCommandGroupBuilder addSubCommands(List<SubCommand> subCommands) {
        subCommandList.addAll(subCommands);
        return this;
    }

    @Override
    public SubCommandGroup build() {
        ObjectNode subCommandGroup = mapper.createObjectNode()
                .put("name", name)
                .put("description", description);
        SlashCommandUtils.createLocalizedLists(subCommandGroup, localizedNameList, localizedDescriptionList);
        if (!subCommandList.isEmpty()) {
            SlashCommandUtils.createSubCommandsArray(subCommandGroup, subCommandList);
        }
        return new SubCommandGroup(subCommandGroup);
    }
}
