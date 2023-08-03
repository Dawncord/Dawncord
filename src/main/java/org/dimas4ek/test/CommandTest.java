package org.dimas4ek.test;

import org.dimas4ek.enities.component.ActionRow;
import org.dimas4ek.enities.component.Button;
import org.dimas4ek.enities.types.ButtonStyle;
import org.dimas4ek.event.Event;
import org.dimas4ek.event.slashcommand.interaction.SlashCommandInteractionEvent;

public class CommandTest extends Event {
    @Override
    public void onEvent(SlashCommandInteractionEvent event) {
        if (event.getCommandName().equals("test2")) {
            Button button = Button.of(ButtonStyle.PRIMARY, "button");
            button.setURL("https://autocode.com/tools/discord/embed-builder/");
            event.reply("hi").addComponents(ActionRow.of(button)).execute();
        }
    }
}
