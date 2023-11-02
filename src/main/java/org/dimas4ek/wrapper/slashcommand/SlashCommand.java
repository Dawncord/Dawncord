package org.dimas4ek.wrapper.slashcommand;

import org.dimas4ek.wrapper.slashcommand.option.Option;
import org.dimas4ek.wrapper.types.Locale;
import org.dimas4ek.wrapper.types.OptionType;
import org.dimas4ek.wrapper.utils.EnumUtils;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;


/*@Getter
public class SlashCommand {
    private final String name;
    private final String description;
    private final List<Option> optionList = new ArrayList<>();
    private final Map<Locale, String> localizedNameList = new EnumMap<>(Locale.class);
    private final Map<Locale, String> localizedDescriptionList = new EnumMap<>(Locale.class);

    public SlashCommand(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void addOption(Option option) {
        optionList.add(option);
    }

    public void addOptions(Option... options) {
        Collections.addAll(optionList, options);
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
}*/

public class SlashCommand implements Command {
    private final JSONObject command;

    public SlashCommand(JSONObject command) {
        this.command = command;
    }

    @Override
    public String getName() {
        return command.getString("name");
    }

    @Override
    public String getDescription() {
        return command.getString("description");
    }

    @Override
    public List<Option> getOptions() {
        if (command.has("options")) {
            List<Option> options = new ArrayList<>();
            JSONArray optionsArray = command.getJSONArray("options");
            for (int i = 0; i < optionsArray.length(); i++) {
                JSONObject option = optionsArray.getJSONObject(i);
                Option optionData = new Option(
                        setOptionType(option.getInt("type")),
                        option.getString("name"),
                        option.getString("description")
                );
                if (option.has("required")) optionData.setRequired(option.getBoolean("required"));
                if (option.has("autocomplete")) optionData.setRequired(option.getBoolean("autocomplete"));

                if (option.has("choices")) {
                    JSONArray choicesArray = option.getJSONArray("choices");
                    for (int j = 0; j < choicesArray.length(); j++) {
                        JSONObject choice = option.getJSONArray("choices").getJSONObject(j);
                        Option.Choice choiceData = new Option.Choice(
                                choice.getString("name"),
                                choice.getString("value")
                        );
                        optionData.addChoice(choiceData);
                    }
                }
                options.add(optionData);
            }
            return options;
        }
        return Collections.emptyList();
    }

    private OptionType setOptionType(int type) {
        return EnumUtils.getEnumObjectFromInt(type, OptionType.class);
        /*for (OptionType optionType : OptionType.values()) {
            if (type == optionType.getValue()) {
                return optionType;
            }
        }
        return null;*/
    }

    @Override
    public Map<Locale, String> getNameLocalizations() {
        return getLocaleStringMap("name_localizations");
    }

    @Override
    public Map<Locale, String> getDescriptionLocalizations() {
        return getLocaleStringMap("description_localizations");
    }

    @NotNull
    private Map<Locale, String> getLocaleStringMap(String localizations) {
        if (command.has(localizations)) {
            Map<Locale, String> map = new EnumMap<>(Locale.class);
            JSONObject nameLocalizations = command.getJSONObject(localizations);
            for (int i = 0; i < nameLocalizations.length(); i++) {
                for (String key : nameLocalizations.keySet()) {
                    String value = nameLocalizations.getString(key);
                    map.put(Locale.valueOf(key), value);
                }
            }
            return map;
        }
        return Collections.emptyMap();
    }
}
