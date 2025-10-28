package io.github.dawncord.api.action.command.subcommand;

import io.github.dawncord.api.action.command.CommandAction;
import io.github.dawncord.api.action.command.SubCommandable;
import io.github.dawncord.api.command.SubCommandGroup;
import io.github.dawncord.api.utils.SlashCommandUtils;

import java.util.List;

/**
 * Represents an action for creating a subcommand group.
 *
 * @see SubCommandGroup
 */
public class SubCommandGroupCreateAction extends CommandAction implements SubCommandable<SubCommandGroupCreateAction> {
    private final List<SubCommandGroup> subCommandGroupList;

    /**
     * Create a new {@link SubCommandGroupCreateAction}
     *
     * @param name                The name of the subcommand group.
     * @param description         The description of the subcommand group.
     * @param subCommandGroupList The list of subcommand groups.
     */
    public SubCommandGroupCreateAction(String name, String description, List<SubCommandGroup> subCommandGroupList) {
        super(name, description);
        this.subCommandGroupList = subCommandGroupList;
    }

    @Override
    protected void submit() {
        SlashCommandUtils.createLocalizedLists(jsonObject, localizedNameList, localizedDescriptionList);
        if (!subCommandList.isEmpty()) {
            SlashCommandUtils.createSubCommandsArray(jsonObject, subCommandList);
        }
        subCommandGroupList.add(new SubCommandGroup(jsonObject));
    }

    @Override
    public SubCommandGroupCreateAction getSelf() {
        return this;
    }
}
