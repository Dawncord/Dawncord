package org.dimas4ek.dawncord.command;

import org.dimas4ek.dawncord.action.SlashCommandModifyAction;
import org.dimas4ek.dawncord.command.option.Option;
import org.dimas4ek.dawncord.types.CommandType;
import org.dimas4ek.dawncord.types.PermissionType;

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
