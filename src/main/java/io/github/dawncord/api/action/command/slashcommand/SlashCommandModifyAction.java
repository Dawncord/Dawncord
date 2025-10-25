package io.github.dawncord.api.action.command.slashcommand;

import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.command.SlashCommand;

/**
 * Represents an action for updating a slash command.
 *
 * @see SlashCommand
 */
public class SlashCommandModifyAction extends SlashCommandAction {
    private final String commandId;

    /**
     * Create a new {@link SlashCommandModifyAction}
     *
     * @param commandId The ID of the slash command to modify.
     */
    public SlashCommandModifyAction(String commandId) {
        super();
        this.commandId = commandId;
    }

    @Override
    protected void submit() {
        super.submit();
        ApiClient.patch(jsonObject, Routes.SlashCommand.Get(commandId));
    }
}
