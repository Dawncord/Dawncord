package org.dimas4ek.wrapper.slashcommand;

import lombok.Getter;
import org.dimas4ek.wrapper.types.OptionType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class Option {
    private final OptionType type;
    private final String name;
    private final String description;
    private boolean isRequired;
    private boolean isAutocomplete; //TODO add autocomplete
    private final List<Choice> choicesList = new ArrayList<>();

    public Option(OptionType type, String name, String description) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.isRequired = false;
        isAutocomplete = false;
    }

    public Option(OptionType type, String name, String description, boolean isRequired) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.isRequired = isRequired;
        isAutocomplete = false;
    }

    public Option(OptionType type, String name, String description, boolean isRequired, boolean isAutocomplete) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.isRequired = isRequired;
        this.isAutocomplete = isAutocomplete;
    }

    public void setRequired(boolean isRequired) {
        this.isRequired = isRequired;
    }

    public void setAutocomplete(boolean isAutocomplete) {
        this.isAutocomplete = isAutocomplete;
    }

    public void addChoice(Choice choice) {
        choicesList.add(choice);
    }

    public void addChoices(Choice... choices) {
        Collections.addAll(choicesList, choices);
    }

    @Getter
    public static class Choice {
        private final String name;
        private final String value;

        public Choice(String name, String value) {
            this.name = name;
            this.value = value;
        }
    }
}

