package io.github.dawncord.api.action.command;

import io.github.dawncord.api.action.command.subcommand.SubCommandGroupCreateAction;
import io.github.dawncord.api.command.SubCommandGroup;
import io.github.dawncord.api.utils.ActionExecutor;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public interface SubCommandGroupable<T> {
    List<SubCommandGroup> getSubCommandGroupList();

    T getSelf();

    /**
     * Adds a sub command group.
     */
    default T addSubCommandGroup(SubCommandGroup subCommandGroup) {
        getSubCommandGroupList().add(subCommandGroup);
        return getSelf();
    }

    /**
     * Adds the given subcommand groups.
     */
    default T addSubCommandGroups(SubCommandGroup... subCommandGroups) {
        Collections.addAll(getSubCommandGroupList(), subCommandGroups);
        return getSelf();
    }

    /**
     * Adds a list of subcommand groups.
     */
    default T addSubCommandGroups(List<SubCommandGroup> subCommandGroups) {
        getSubCommandGroupList().addAll(subCommandGroups);
        return getSelf();
    }

    /**
     * Adds a subcommand group.
     */
    default T addSubCommandGroup(String name, String description, Consumer<SubCommandGroupCreateAction> handler) {
        ActionExecutor.createSubCommandGroup(handler, name, description, getSubCommandGroupList());
        return getSelf();
    }
}
