package org.dimas4ek.wrapper.slashcommand;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class SlashCommand {
    private final String name;
    private final String description;
    private final List<Option> optionList = new ArrayList<>();

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
}
