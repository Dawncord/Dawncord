package org.dimas4ek.wrapper.command;

import org.dimas4ek.wrapper.action.SlashCommandModifyAction;
import org.dimas4ek.wrapper.command.option.Option;
import org.dimas4ek.wrapper.types.CommandType;
import org.dimas4ek.wrapper.types.PermissionType;

import java.util.List;
import java.util.function.Consumer;

public interface ISlashCommand extends Command {
    String getId();

    long getIdLong();

    CommandType getType();

    String getApplicationId();

    long getApplicationIdLong();

    List<PermissionType> getMemberPermissions();

    boolean isNsfw();

    String getVersion();

    List<Option> getOptions();

    List<SubCommand> getSubCommands();

    List<SubCommandGroup> getSubCommandGroups();

    void modify(Consumer<SlashCommandModifyAction> handler);

    void delete();
}
