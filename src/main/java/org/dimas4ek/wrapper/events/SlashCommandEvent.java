package org.dimas4ek.wrapper.events;

public interface SlashCommandEvent {
    String getCommandName();

    void reply(String message);
}
