package io.github.dawncord.api.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.dawncord.api.command.SubCommand;
import io.github.dawncord.api.command.SubCommandGroup;
import io.github.dawncord.api.command.option.Option;
import io.github.dawncord.api.types.Locale;
import io.github.dawncord.api.types.OptionType;

import java.util.*;

/**
 * Utility class for handling Slash Commands.
 */
public class SlashCommandUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Creates default settings for a Slash Command.
     *
     * @param node                     The JSON node to which settings are applied.
     * @param localizedNameList        The localized names for the command.
     * @param localizedDescriptionList The localized descriptions for the command.
     * @param optionList               The list of options for the command.
     */
    public static void createDefaults(ObjectNode node, Map<Locale, String> localizedNameList, Map<Locale, String> localizedDescriptionList, List<Option> optionList) {
        createLocalizedLists(node, localizedNameList, localizedDescriptionList);
        if (!optionList.isEmpty()) {
            ArrayNode optionsArray = createOptionsArray(optionList);
            if (node.has("options")) {
                ((ArrayNode) node.get("options")).addAll(optionsArray);
            } else {
                node.set("options", optionsArray);
            }
        }
    }

    /**
     * Creates localized lists for a Slash Command.
     *
     * @param node                     The JSON node to which localized lists are applied.
     * @param localizedNameList        The localized names for the command.
     * @param localizedDescriptionList The localized descriptions for the command.
     */
    public static void createLocalizedLists(ObjectNode node, Map<Locale, String> localizedNameList, Map<Locale, String> localizedDescriptionList) {
        if (!localizedNameList.isEmpty()) {
            node.set("name_localizations", mapper.valueToTree(localizedNameList));
        }
        if (!localizedDescriptionList.isEmpty()) {
            node.set("description_localizations", mapper.valueToTree(localizedDescriptionList));
        }
    }

    /**
     * Creates an array of options for a Slash Command.
     *
     * @param optionList The list of options to convert to JSON.
     * @return The JSON array of options.
     */
    public static ArrayNode createOptionsArray(List<Option> optionList) {
        ArrayNode optionsArray = mapper.createArrayNode();
        optionList.forEach(option -> {
            ObjectNode optionJson = mapper.createObjectNode();
            optionJson.put("type", option.getType().getValue())
                    .put("name", option.getName())
                    .put("description", option.getDescription());
            if (option.isRequired()) optionJson.put("required", true);
            if (option.isAutocomplete()) optionJson.put("autocomplete", true);
            List<Option.Choice> choices = option.getChoices();
            if (choices != null && !choices.isEmpty()) {
                ArrayNode choicesArray = mapper.createArrayNode();
                choices.forEach(choice -> {
                    ObjectNode choiceJson = mapper.createObjectNode()
                            .put("name", choice.getName())
                            .put("value", choice.getValue());
                    choicesArray.add(choiceJson);
                });
                optionJson.set("choices", choicesArray);
            }
            optionsArray.add(optionJson);
        });

        return optionsArray;
    }

    /**
     * Creates an array of sub-commands for a Slash Command.
     *
     * @param node           The JSON node to which sub-commands are added.
     * @param subCommandList The list of sub-commands to include in the array.
     * @return The JSON array of sub-commands.
     */
    public static ArrayNode createSubCommandsArray(ObjectNode node, List<SubCommand> subCommandList) {
        ArrayNode subCommandsArray = mapper.createArrayNode();
        subCommandList.forEach(subCommand -> {
            ObjectNode subCommandJson = mapper.createObjectNode()
                    .put("type", OptionType.SUB_COMMAND.getValue())
                    .put("name", subCommand.getName())
                    .put("description", subCommand.getDescription());
            createDefaults(subCommandJson, subCommand.getNameLocalizations(), subCommand.getDescriptionLocalizations(), subCommand.getOptions());
            subCommandsArray.add(subCommandJson);
        });
        if (node.has("options")) {
            ((ArrayNode) node.get("options")).addAll(subCommandsArray);
        } else {
            node.set("options", subCommandsArray);
        }
        return subCommandsArray;
    }

    /**
     * Creates an array of sub-command groups for a Slash Command.
     *
     * @param node                The JSON node to which sub-command groups are added.
     * @param subCommandGroupList The list of sub-command groups to include in the array.
     */
    public static void createSubCommandGroupArray(ObjectNode node, List<SubCommandGroup> subCommandGroupList) {
        ArrayNode subCommandGroupsArray = mapper.createArrayNode();
        subCommandGroupList.forEach(subCommandGroup -> {
            ObjectNode subCommandGroupJson = mapper.createObjectNode()
                    .put("type", OptionType.SUB_COMMAND_GROUP.getValue())
                    .put("name", subCommandGroup.getName())
                    .put("description", subCommandGroup.getDescription());
            List<SubCommand> subCommands = subCommandGroup.getSubCommands();
            if (subCommands != null && !subCommands.isEmpty()) {
                ArrayNode subCommandsArray = createSubCommandsArray(subCommandGroupJson, subCommands);
                subCommandGroupJson.set("options", subCommandsArray);
            }
            subCommandGroupsArray.add(subCommandGroupJson);
            if (!subCommandGroup.getNameLocalizations().isEmpty()) {
                subCommandGroupJson.set("name_localizations", mapper.valueToTree(subCommandGroup.getNameLocalizations()));
            }
            if (!subCommandGroup.getDescriptionLocalizations().isEmpty()) {
                subCommandGroupJson.set("description_localizations", mapper.valueToTree(subCommandGroup.getDescriptionLocalizations()));
            }
        });
        if (node.has("options")) {
            ((ArrayNode) node.get("options")).addAll(subCommandGroupsArray);
        } else {
            node.set("options", subCommandGroupsArray);
        }
    }

    /**
     * Retrieves a map of localized strings from a JSON node.
     *
     * @param command       The JSON node containing the localization data.
     * @param localizations The key for the localization data within the JSON node.
     * @return A map containing the localized strings, keyed by Locale.
     */
    public static Map<Locale, String> getLocaleStringMap(JsonNode command, String localizations) {
        if (command.has(localizations) && command.hasNonNull(localizations)) {
            Map<Locale, String> map = new EnumMap<>(Locale.class);
            command.get(localizations).fields().forEachRemaining(entry -> map.put(Locale.valueOf(entry.getKey()), entry.getValue().asText()));
            return map;
        }
        return Collections.emptyMap();
    }

    /**
     * Creates a list of options from a JSON node representing a command.
     *
     * @param command The JSON node representing the command.
     * @return A list of options parsed from the JSON node.
     */
    public static List<Option> createOptions(JsonNode command) {
        List<Option> options = new ArrayList<>();
        JsonNode optionsNode = command.get("options");
        if (optionsNode != null && !optionsNode.isNull()) {
            for (JsonNode option : optionsNode) {
                int type = option.get("type").asInt();
                if (type == OptionType.SUB_COMMAND.getValue() || type == OptionType.SUB_COMMAND_GROUP.getValue()) {
                    continue;
                }
                Option optionData = new Option(
                        EnumUtils.getEnumObject(option, "type", OptionType.class),
                        option.get("name").asText(),
                        option.get("description").asText()
                );
                optionData.setRequired(option.has("required") && option.get("required").asBoolean());
                optionData.setAutocomplete(option.has("autocomplete") && option.get("autocomplete").asBoolean());
                if (option.has("choices")) {
                    for (JsonNode choice : option.get("choices")) {
                        Option.Choice choiceData = new Option.Choice(
                                choice.get("name").asText(),
                                choice.get("value").asText()
                        );
                        optionData.addChoice(choiceData);
                    }
                }
                options.add(optionData);
            }
        }
        return options;
    }
}
