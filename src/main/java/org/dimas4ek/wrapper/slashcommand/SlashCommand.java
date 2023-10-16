package org.dimas4ek.wrapper.slashcommand;

import lombok.Getter;
import lombok.Setter;
import org.dimas4ek.wrapper.types.Locale;

import java.util.*;

@Getter
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
}
