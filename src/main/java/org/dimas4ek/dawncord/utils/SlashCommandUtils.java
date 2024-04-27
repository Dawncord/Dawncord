package org.dimas4ek.dawncord.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.dimas4ek.dawncord.command.SubCommand;
import org.dimas4ek.dawncord.command.SubCommandGroup;
import org.dimas4ek.dawncord.command.option.Option;
import org.dimas4ek.dawncord.types.Locale;
import org.dimas4ek.dawncord.types.OptionType;

import java.util.*;

public class SlashCommandUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

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

    public static void createLocalizedLists(ObjectNode node, Map<Locale, String> localizedNameList, Map<Locale, String> localizedDescriptionList) {
        if (!localizedNameList.isEmpty()) {
            node.set("name_localizations", mapper.valueToTree(localizedNameList));
        }
        if (!localizedDescriptionList.isEmpty()) {
            node.set("description_localizations", mapper.valueToTree(localizedDescriptionList));
        }
    }

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

    public static Map<Locale, String> getLocaleStringMap(JsonNode command, String localizations) {
        if (command.has(localizations) && command.hasNonNull(localizations)) {
            Map<Locale, String> map = new EnumMap<>(Locale.class);
            command.get(localizations).fields().forEachRemaining(entry -> map.put(Locale.valueOf(entry.getKey()), entry.getValue().asText()));
            return map;
        }
        return Collections.emptyMap();
    }

    /*public static Map<Locale, String> getLocaleStringMap(JsonNode command, String localizations) {
        if (command.has(localizations) && command.hasNonNull(localizations)) {
            Map<Locale, String> map = new EnumMap<>(Locale.class);
            command.get(localizations).fieldNames().forEachRemaining(key -> {
                map.put(Locale.valueOf(key), command.get(localizations).get(key).asText());
            });
            return map;
        }
        return Collections.emptyMap();
    }*/

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
    /*public static List<Option> createOptions(JsonNode command) {
        List<Option> options = new ArrayList<>();
        if (command.has("options") && command.hasNonNull("options")) {
            for (JsonNode option : command.get("options")) {
                if (option.get("type").asInt() == OptionType.SUB_COMMAND.getValue() ||
                        option.get("type").asInt() == OptionType.SUB_COMMAND_GROUP.getValue()) {
                    continue;
                }
                Option optionData = new Option(
                        EnumUtils.getEnumObject(option, "type", OptionType.class),
                        option.get("name").asText(),
                        option.get("description").asText()
                );
                if (option.has("required")) optionData.setRequired(option.get("required").asBoolean());
                if (option.has("autocomplete")) optionData.setRequired(option.get("autocomplete").asBoolean());
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
    }*/
}
