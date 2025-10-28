package io.github.dawncord.api.action.command.slashcommand;

import io.github.dawncord.api.action.command.CommandAction;
import io.github.dawncord.api.action.command.Optionable;
import io.github.dawncord.api.action.command.SubCommandGroupable;
import io.github.dawncord.api.action.command.SubCommandable;
import io.github.dawncord.api.command.SubCommandGroup;
import io.github.dawncord.api.utils.SlashCommandUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class SlashCommandAction extends CommandAction
        implements Optionable<SlashCommandAction>, SubCommandable<SlashCommandAction>, SubCommandGroupable<SlashCommandAction> {
    protected final List<SubCommandGroup> subCommandGroupList = new ArrayList<>();

    protected SlashCommandAction(String name, String description) {
        super(name, description);
    }

    protected SlashCommandAction() {
        super();
    }

    @Override
    public SlashCommandAction getSelf() {
        return this;
    }

    @Override
    public List<SubCommandGroup> getSubCommandGroupList() {
        return subCommandGroupList;
    }

    @Override
    public void setHasChanges(boolean hasChanges) {
        this.hasChanges = hasChanges;
    }

    @Override
    protected void submit() {
        if (!hasChanges) return;

        SlashCommandUtils.createDefaults(jsonObject, localizedNameList, localizedDescriptionList, optionList);
        if (optionList.isEmpty()) {
            if (!subCommandList.isEmpty()) {
                SlashCommandUtils.createSubCommandsArray(jsonObject, subCommandList);
            }
            if (!subCommandGroupList.isEmpty()) {
                SlashCommandUtils.createSubCommandGroupArray(jsonObject, subCommandGroupList);
            }
        } else {
            if (!subCommandList.isEmpty() || !subCommandGroupList.isEmpty()) {
                throw new IllegalArgumentException("Cannot have options and subcommands in the same command");
            }
        }
    }
}
