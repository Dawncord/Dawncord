package org.dimas4ek.dawncord.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.dimas4ek.dawncord.types.Locale;
import org.dimas4ek.dawncord.utils.SlashCommandUtils;

import java.util.*;

public class SubCommandGroupBuilder implements CommandBuilder {
    private static final ObjectMapper mapper = new ObjectMapper();
    private String name;
    private String description;
    private final List<SubCommand> subCommandList = new ArrayList<>();
    private final Map<Locale, String> localizedNameList = new EnumMap<>(Locale.class);
    private final Map<Locale, String> localizedDescriptionList = new EnumMap<>(Locale.class);

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

    public SubCommandGroupBuilder addSubCommand(SubCommand subCommand) {
        subCommandList.add(subCommand);
        return this;
    }

    public SubCommandGroupBuilder addSubCommands(SubCommand... subCommands) {
        Collections.addAll(subCommandList, subCommands);
        return this;
    }

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
