package io.github.dawncord.api.command;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.action.command.slashcommand.SlashCommandModifyAction;
import io.github.dawncord.api.command.option.Option;
import io.github.dawncord.api.entities.ISnowflake;
import io.github.dawncord.api.types.CommandType;
import io.github.dawncord.api.types.OptionType;
import io.github.dawncord.api.types.PermissionType;
import io.github.dawncord.api.utils.ActionExecutor;
import io.github.dawncord.api.utils.EnumUtils;
import io.github.dawncord.api.utils.SlashCommandUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Represents a Discord Slash Command. This class is used to interact with existing slash commands
 * retrieved from the Discord API.
 */
public class SlashCommand extends Command implements ISnowflake {
    private final JsonNode command;
    private String id;
    private String applicationId;
    private List<PermissionType> memberPermissions;
    private CommandType type;
    private Boolean nsfw;
    private String version;
    private List<Option> options;
    private List<SubCommand> subCommands;
    private List<SubCommandGroup> subCommandGroups;

    /**
     * Constructs a new SlashCommand instance.
     *
     * @param command The JSON node representing the slash command.
     */
    public SlashCommand(JsonNode command) {
        this.command = command;
        super(command);
    }

    @Override
    public String getId() {
        if (id == null) {
            id = command.get("id").asText();
        }
        return id;
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
    }

    public CommandType getType() {
        if (type == null) {
            type = EnumUtils.getEnumObject(command, "type", CommandType.class);
        }
        return type;
    }

    public String getApplicationId() {
        if (applicationId == null) {
            applicationId = command.get("application_id").asText();
        }
        return applicationId;
    }

    public long getApplicationIdLong() {
        return Long.parseLong(getApplicationId());
    }

    public List<PermissionType> getMemberPermissions() {
        if (memberPermissions == null) {
            memberPermissions = EnumUtils.getEnumListFromLong(command, "default_member_permissions", PermissionType.class);
        }
        return memberPermissions;
    }

    public boolean isNsfw() {
        if (nsfw == null) {
            nsfw = command.get("nsfw").asBoolean();
        }
        return nsfw;
    }

    public String getVersion() {
        if (version == null) {
            version = command.get("version").asText();
        }
        return version;
    }

    public List<SubCommand> getSubCommands() {
        if (subCommands == null) {
            subCommands = new ArrayList<>();
            if (command.has("options") && command.hasNonNull("options")) {
                for (JsonNode option : command.get("options")) {
                    if (option.get("type").asInt() == OptionType.SUB_COMMAND.getValue()) {
                        subCommands.add(new SubCommand(option));
                    }
                }
            }
        }
        return subCommands;
    }

    public List<SubCommandGroup> getSubCommandGroups() {
        if (subCommandGroups == null) {
            subCommandGroups = new ArrayList<>();
            if (command.has("options") && command.hasNonNull("options")) {
                for (JsonNode option : command.get("options")) {
                    if (option.get("type").asInt() == OptionType.SUB_COMMAND_GROUP.getValue()) {
                        subCommandGroups.add(new SubCommandGroup(option));
                    }
                }
            }
        }
        return subCommandGroups;
    }

    public List<Option> getOptions() {
        if (options == null) {
            options = SlashCommandUtils.createOptions(command);
        }
        return options;
    }

    public void modify(Consumer<SlashCommandModifyAction> handler) {
        ActionExecutor.modifySlashCommand(handler, getId());
    }

    public void delete() {
        ApiClient.delete(Routes.SlashCommand.Get(getId()));
    }
}
