package io.github.dawncord.api.action.command.slashcommand;

import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.command.SlashCommand;

/**
 * Represents an action for creating a slash command.
 *
 * @see SlashCommand
 */
public class SlashCommandCreateAction extends SlashCommandAction {
    /**
     * Create a new {@link SlashCommandCreateAction}
     *
     * @param name        The name of the slash command.
     * @param description The description of the slash command.
     */
    public SlashCommandCreateAction(String name, String description) {
        super(name, description);
    }

    @Override
    protected void submit() {
        super.submit();
        ApiClient.post(jsonObject, Routes.SlashCommand.All());
    }
}
