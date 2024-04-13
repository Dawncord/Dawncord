package org.dimas4ek.wrapper.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.Constants;
import org.dimas4ek.wrapper.command.option.Option;
import org.dimas4ek.wrapper.types.Locale;
import org.dimas4ek.wrapper.types.OptionType;
import org.dimas4ek.wrapper.utils.SlashCommandUtils;

import java.util.*;

public class SlashCommandModifyAction {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode jsonObject;
    private final String commandId;
    private boolean hasChanges = false;
    private final List<Option> optionList = new ArrayList<>();
    private final Map<Locale, String> localizedNameList = new EnumMap<>(Locale.class);
    private final Map<Locale, String> localizedDescriptionList = new EnumMap<>(Locale.class);

    public SlashCommandModifyAction(String commandId) {
        this.jsonObject = mapper.createObjectNode();
        this.commandId = commandId;
    }

    private SlashCommandModifyAction setProperty(String key, Object value) {
        jsonObject.set(key, mapper.valueToTree(value));
        hasChanges = true;
        return this;
    }

    public SlashCommandModifyAction setName(String name) {
        return setProperty("name", name);
    }

    public SlashCommandModifyAction setDescription(String description) {
        return setProperty("description", description);
    }

    public SlashCommandModifyAction addOption(OptionType type, String name, String description) {
        optionList.add(new Option(type, name, description));
        return this;
    }

    public SlashCommandModifyAction addOption(OptionType type, String name, String description, boolean isRequired) {
        optionList.add(new Option(type, name, description, isRequired));
        return this;
    }

    public SlashCommandModifyAction addOption(OptionType type, String name, String description, boolean isRequired, boolean isAutocomplete) {
        optionList.add(new Option(type, name, description, isRequired, isAutocomplete));
        return this;
    }

    public SlashCommandModifyAction addOption(Option option) {
        optionList.add(option);
        return this;
    }

    public SlashCommandModifyAction addOptions(Option... options) {
        Collections.addAll(optionList, options);
        return this;
    }

    public SlashCommandModifyAction addLocalizedName(Locale locale, String name) {
        localizedNameList.put(locale, name);
        return this;
    }

    public SlashCommandModifyAction setNameLocalizations(Map<Locale, String> nameLocalizations) {
        localizedNameList.putAll(nameLocalizations);
        return this;
    }

    public SlashCommandModifyAction addLocalizedDescription(Locale locale, String description) {
        localizedDescriptionList.put(locale, description);
        return this;
    }

    public SlashCommandModifyAction setDescriptionLocalizations(Map<Locale, String> descriptionLocalizations) {
        localizedDescriptionList.putAll(descriptionLocalizations);
        return this;
    }

    private void submit() {
        if (hasChanges) {
            if (!localizedNameList.isEmpty()) {
                jsonObject.set("name_localizations", mapper.valueToTree(localizedNameList));
            }
            if (!localizedDescriptionList.isEmpty()) {
                jsonObject.set("description_localizations", mapper.valueToTree(localizedDescriptionList));
            }
            if (!optionList.isEmpty()) {
                ArrayNode optionsArray = SlashCommandUtils.createOptionsArray(optionList);
                if (jsonObject.has("options")) {
                    ((ArrayNode) jsonObject.get("options")).addAll(optionsArray);
                } else {
                    jsonObject.set("options", optionsArray);
                }
            }

            ApiClient.patch(jsonObject, "/applications/" + Constants.APPLICATION_ID + "/commands/" + commandId);
            hasChanges = false;
        }
        jsonObject.removeAll();
    }
}
