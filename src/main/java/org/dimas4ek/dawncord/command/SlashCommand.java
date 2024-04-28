package org.dimas4ek.dawncord.command;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.dawncord.ApiClient;
import org.dimas4ek.dawncord.Routes;
import org.dimas4ek.dawncord.action.SlashCommandModifyAction;
import org.dimas4ek.dawncord.command.option.Option;
import org.dimas4ek.dawncord.types.CommandType;
import org.dimas4ek.dawncord.types.Locale;
import org.dimas4ek.dawncord.types.OptionType;
import org.dimas4ek.dawncord.types.PermissionType;
import org.dimas4ek.dawncord.utils.ActionExecutor;
import org.dimas4ek.dawncord.utils.EnumUtils;
import org.dimas4ek.dawncord.utils.SlashCommandUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class SlashCommand implements ISlashCommand {
    private final JsonNode command;
    private String id;
    private String name;
    private String description;
    private String applicationId;
    private List<PermissionType> memberPermissions;
    private CommandType type;
    private Boolean nsfw;
    private String version;
    private List<Option> options;
    private List<SubCommand> subCommands;
    private List<SubCommandGroup> subCommandGroups;
    private Map<Locale, String> nameLocalizations;
    private Map<Locale, String> descriptionLocalizations;

    public SlashCommand(JsonNode command) {
        this.command = command;
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

    @Override
    public String getName() {
        if (name == null) {
            name = command.get("name").asText();
        }
        return name;
    }

    @Override
    public String getDescription() {
        if (description == null) {
            description = command.get("description").asText();
        }
        return description;
    }

    @Override
    public String getApplicationId() {
        if (applicationId == null) {
            applicationId = command.get("application_id").asText();
        }
        return applicationId;
    }

    @Override
    public long getApplicationIdLong() {
        return Long.parseLong(getApplicationId());
    }

    @Override
    public List<PermissionType> getMemberPermissions() {
        if (memberPermissions == null) {
            memberPermissions = EnumUtils.getEnumListFromLong(command, "default_member_permissions", PermissionType.class);
        }
        return memberPermissions;
    }

    @Override
    public CommandType getType() {
        if (type == null) {
            type = EnumUtils.getEnumObject(command, "type", CommandType.class);
        }
        return type;
    }

    @Override
    public boolean isNsfw() {
        if (nsfw == null) {
            nsfw = command.get("nsfw").asBoolean();
        }
        return nsfw;
    }

    @Override
    public String getVersion() {
        if (version == null) {
            version = command.get("version").asText();
        }
        return version;
    }

    @Override
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

    @Override
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

    @Override
    public List<Option> getOptions() {
        if (options == null) {
            options = SlashCommandUtils.createOptions(command);
        }
        return options;
    }

    @Override
    public Map<Locale, String> getNameLocalizations() {
        if (nameLocalizations == null) {
            nameLocalizations = SlashCommandUtils.getLocaleStringMap(command, "name_localizations");
        }
        return nameLocalizations;
    }

    @Override
    public Map<Locale, String> getDescriptionLocalizations() {
        if (descriptionLocalizations == null) {
            descriptionLocalizations = SlashCommandUtils.getLocaleStringMap(command, "description_localizations");
        }
        return descriptionLocalizations;
    }

    @Override
    public void modify(Consumer<SlashCommandModifyAction> handler) {
        ActionExecutor.modifySlashCommand(handler, getId());
    }

    @Override
    public void delete() {
        ApiClient.delete(Routes.SlashCommand.Get(getId()));
    }
}
