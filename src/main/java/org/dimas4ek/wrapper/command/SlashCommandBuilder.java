package org.dimas4ek.wrapper.command;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.dimas4ek.wrapper.command.option.Option;
import org.dimas4ek.wrapper.types.Locale;
import org.dimas4ek.wrapper.types.OptionType;

import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
public class SlashCommandBuilder {
    private String name;
    private String description;
    private final List<Option> optionList = new ArrayList<>();
    private final Map<Locale, String> localizedNameList = new EnumMap<>(Locale.class);
    private final Map<Locale, String> localizedDescriptionList = new EnumMap<>(Locale.class);

    public SlashCommandBuilder addOption(OptionType type, String name, String description) {
        optionList.add(new Option(type, name, description));
        return this;
    }

    public SlashCommandBuilder addOption(OptionType type, String name, String description, boolean isRequired) {
        optionList.add(new Option(type, name, description, isRequired));
        return this;
    }

    public SlashCommandBuilder addOption(OptionType type, String name, String description, boolean isRequired, boolean isAutocomplete) {
        optionList.add(new Option(type, name, description, isRequired, isAutocomplete));
        return this;
    }

    public SlashCommandBuilder addOption(Option option) {
        optionList.add(option);
        return this;
    }

    public SlashCommandBuilder addOptions(Option... options) {
        Collections.addAll(optionList, options);
        return this;
    }

    public void setLocalizedName(Locale locale, String name) {
        localizedNameList.put(locale, name);
    }

    public void setLocalizedNames(Map<Locale, String> localizedNames) {
        localizedNameList.putAll(localizedNames);
    }

    public void setLocalizedDescription(Locale locale, String description) {
        localizedDescriptionList.put(locale, description);
    }

    public void setLocalizedDescriptions(Map<Locale, String> localizedDescriptions) {
        localizedDescriptionList.putAll(localizedDescriptions);
    }

    public SlashCommand build() {
        ObjectNode slashCommand = JsonNodeFactory.instance.objectNode()
                .put("name", name)
                .put("description", description);
        if (!optionList.isEmpty()) {
            ArrayNode optionsArray = JsonNodeFactory.instance.arrayNode();
            optionList.forEach(option -> {
                ObjectNode optionJson = JsonNodeFactory.instance.objectNode();
                optionJson.put("type", option.getType().getValue())
                        .put("name", option.getName())
                        .put("description", option.getDescription());
                if (option.isRequired()) optionJson.put("required", true);
                if (option.isAutocomplete()) optionJson.put("autocomplete", true);
                if (!option.getChoices().isEmpty()) {
                    ArrayNode choicesArray = JsonNodeFactory.instance.arrayNode();
                    option.getChoices().forEach(choice -> {
                        ObjectNode choiceJson = JsonNodeFactory.instance.objectNode()
                                .put("name", choice.getName())
                                .put("value", choice.getValue());
                        choicesArray.add(choiceJson);
                    });
                    optionJson.set("choices", choicesArray);
                }

                optionsArray.add(optionJson);
            });
            slashCommand.set("options", optionsArray);
        }

        return new SlashCommand(slashCommand);
    }
}
