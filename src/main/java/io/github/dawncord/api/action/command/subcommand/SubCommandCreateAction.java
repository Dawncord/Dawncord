package io.github.dawncord.api.action.command.subcommand;

import io.github.dawncord.api.action.command.CommandAction;
import io.github.dawncord.api.action.command.Optionable;
import io.github.dawncord.api.command.SubCommand;
import io.github.dawncord.api.utils.SlashCommandUtils;

import java.util.List;

/**
 * Represents an action for creating a subcommand.
 *
 * @see SubCommand
 */
public class SubCommandCreateAction extends CommandAction implements Optionable<SubCommandCreateAction> {
    /**
     * Create a new {@link SubCommandCreateAction}
     *
     * @param name           The name of the subcommand.
     * @param description    The description of the subcommand.
     * @param subCommandList The list of subcommands.
     */
    public SubCommandCreateAction(String name, String description, List<SubCommand> subCommandList) {
        super(name, description);
        this.subCommandList = subCommandList;
    }

    @Override
    public SubCommandCreateAction getSelf() {
        return this;
    }

    @Override
    public void setHasChanges(boolean hasChanges) {
        this.hasChanges = hasChanges;
    }

    @Override
    protected void submit() {
        SlashCommandUtils.createDefaults(jsonObject, localizedNameList, localizedDescriptionList, optionList);
        subCommandList.add(new SubCommand(jsonObject));
    }
}
