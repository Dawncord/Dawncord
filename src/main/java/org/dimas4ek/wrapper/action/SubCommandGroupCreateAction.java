package org.dimas4ek.wrapper.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.dimas4ek.wrapper.command.SubCommand;
import org.dimas4ek.wrapper.command.SubCommandGroup;
import org.dimas4ek.wrapper.types.Locale;
import org.dimas4ek.wrapper.utils.ActionExecutor;
import org.dimas4ek.wrapper.utils.SlashCommandUtils;

import java.util.*;
import java.util.function.Consumer;

public class SubCommandGroupCreateAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final Map<Locale, String> localizedNameList = new EnumMap<>(Locale.class);
    private final Map<Locale, String> localizedDescriptionList = new EnumMap<>(Locale.class);
    private final List<SubCommand> subCommandList = new ArrayList<>();
    private final List<SubCommandGroup> subCommandGroupList;

    public SubCommandGroupCreateAction(String name, String description, List<SubCommandGroup> subCommandGroupList) {
        this.jsonObject = mapper.createObjectNode();
        this.jsonObject.put("name", name);
        this.jsonObject.put("description", description);
        this.subCommandGroupList = subCommandGroupList;
    }

    public SubCommandGroupCreateAction setProperty(String name, Object value) {
        jsonObject.set(name, mapper.valueToTree(value));
        return this;
    }

    public SubCommandGroupCreateAction setName(String name) {
        return setProperty("name", name);
    }

    public SubCommandGroupCreateAction setDescription(String description) {
        return setProperty("description", description);
    }

    public SubCommandGroupCreateAction addLocalizedName(Locale locale, String name) {
        localizedNameList.put(locale, name);
        return this;
    }

    public SubCommandGroupCreateAction addNameLocalizations(Map<Locale, String> nameLocalizations) {
        localizedNameList.putAll(nameLocalizations);
        return this;
    }

    public SubCommandGroupCreateAction addLocalizedDescription(Locale locale, String description) {
        localizedDescriptionList.put(locale, description);
        return this;
    }

    public SubCommandGroupCreateAction addDescriptionLocalizations(Map<Locale, String> descriptionLocalizations) {
        localizedDescriptionList.putAll(descriptionLocalizations);
        return this;
    }

    public SubCommandGroupCreateAction addSubCommand(SubCommand subCommand) {
        subCommandList.add(subCommand);
        return this;
    }

    public SubCommandGroupCreateAction addSubCommands(SubCommand... subCommands) {
        Collections.addAll(subCommandList, subCommands);
        return this;
    }

    public SubCommandGroupCreateAction addSubCommand(String name, String description, Consumer<SubCommandCreateAction> handler) {
        ActionExecutor.createSubCommand(handler, name, description, this.subCommandList);
        return this;
    }

    private void submit() {
        SlashCommandUtils.createLocalizedLists(jsonObject, localizedNameList, localizedDescriptionList);
        if (!subCommandList.isEmpty()) {
            SlashCommandUtils.createSubCommandsArray(jsonObject, subCommandList);
        }
        subCommandGroupList.add(new SubCommandGroup(jsonObject));
        //jsonObject.removeAll();
    }
}
