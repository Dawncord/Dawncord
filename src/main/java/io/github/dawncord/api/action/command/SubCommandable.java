package io.github.dawncord.api.action.command;

import io.github.dawncord.api.action.command.subcommand.SubCommandCreateAction;
import io.github.dawncord.api.command.SubCommand;
import io.github.dawncord.api.utils.ActionExecutor;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public interface SubCommandable<T> {
    List<SubCommand> getSubCommandList();
    T getSelf();

    /**
     * Adds a sub command.
     */
    default T addSubCommand(SubCommand subCommand) {
        getSubCommandList().add(subCommand);
        return getSelf();
    }

    /**
     * Adds multiple sub commands.
     */
    default T addSubCommands(SubCommand... subCommands) {
        Collections.addAll(getSubCommandList(), subCommands);
        return getSelf();
    }

    /**
     * Adds a list of subcommands.
     */
    default T addSubCommands(List<SubCommand> subCommands) {
        getSubCommandList().addAll(subCommands);
        return getSelf();
    }

    /**
     * Adds a new subcommand.
     */
    default T addSubCommand(String name, String description, Consumer<SubCommandCreateAction> handler) {
        ActionExecutor.createSubCommand(handler, name, description, getSubCommandList());
        return getSelf();
    }
}
