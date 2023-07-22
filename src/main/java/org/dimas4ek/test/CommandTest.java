package org.dimas4ek.test;

import org.dimas4ek.event.Event;
import org.dimas4ek.event.entities.OptionData;
import org.dimas4ek.event.slashcommand.interaction.SlashCommandInteractionEvent;

import java.util.List;

public class CommandTest extends Event {
    @Override
    public void onEvent(SlashCommandInteractionEvent event) {
        if (event.getCommandName().equals("test2")) {
            List<OptionData> optionData = event.getOptions();
            for (OptionData optionData1 : optionData) {
                System.out.println(optionData1.getName());
                System.out.println(optionData1.getDescription());
            }
        }
    }
}
