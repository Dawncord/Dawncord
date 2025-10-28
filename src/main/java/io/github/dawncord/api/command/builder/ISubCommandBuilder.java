package io.github.dawncord.api.command.builder;

import io.github.dawncord.api.command.SubCommand;

import java.util.Collections;
import java.util.List;

public interface ISubCommandBuilder<T> {
    List<SubCommand> getSubCommandList();

    /**
     * Adds a sub-command to the slash command.
     *
     * @param subCommand The sub-command to add.
     * @return This instance for method chaining.
     */
    default T addSubCommand(SubCommand subCommand) {
        getSubCommandList().add(subCommand);
        return (T) this;
    }

    /**
     * Adds multiple sub-commands to the slash command.
     *
     * @param subCommands The array of sub-commands to add.
     * @return This instance for method chaining.
     */
    default T addSubCommands(SubCommand... subCommands) {
        Collections.addAll(getSubCommandList(), subCommands);
        return (T) this;
    }

    /**
     * Adds a list of sub-commands to the slash command.
     *
     * @param subCommands The list of sub-commands to add.
     * @return This instance for method chaining.
     */
    default T addSubCommands(List<SubCommand> subCommands) {
        getSubCommandList().addAll(subCommands);
        return (T) this;
    }
}
