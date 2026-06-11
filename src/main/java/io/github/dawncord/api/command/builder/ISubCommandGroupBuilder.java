package io.github.dawncord.api.command.builder;

import io.github.dawncord.api.command.SubCommandGroup;

import java.util.Collections;
import java.util.List;

/**
 * Defines builder operations for sub-command groups.
 *
 * @param <T> The concrete builder type returned by fluent setters.
 */
public interface ISubCommandGroupBuilder<T> {
    List<SubCommandGroup> getSubCommandGroupList();

    /**
     * Adds a sub-command group to the slash command.
     *
     * @param subCommandGroups The sub-command group to add.
     * @return This instance for method chaining.
     */
    default T addSubCommandGroup(SubCommandGroup subCommandGroups) {
        getSubCommandGroupList().add(subCommandGroups);
        return (T) this;
    }

    /**
     * Adds multiple sub-command groups to the slash command.
     *
     * @param subCommandGroups The array of sub-command groups to add.
     * @return This instance for method chaining.
     */
    default T addSubCommandGroups(SubCommandGroup... subCommandGroups) {
        Collections.addAll(getSubCommandGroupList(), subCommandGroups);
        return (T) this;
    }

    /**
     * Adds a list of sub-command groups to the slash command.
     *
     * @param subCommandGroup The list of sub-command groups to add.
     * @return This instance for method chaining.
     */
    default T addSubCommandGroup(List<SubCommandGroup> subCommandGroup) {
        getSubCommandGroupList().addAll(subCommandGroup);
        return (T) this;
    }
}
