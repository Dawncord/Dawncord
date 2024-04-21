package org.dimas4ek.wrapper.entities.guild.automod;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.wrapper.ApiClient;
import org.dimas4ek.wrapper.Routes;
import org.dimas4ek.wrapper.action.AutoModRuleModifyAction;
import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.UserImpl;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.types.AutoModEventType;
import org.dimas4ek.wrapper.types.AutoModTriggerType;
import org.dimas4ek.wrapper.utils.ActionExecutor;
import org.dimas4ek.wrapper.utils.EnumUtils;
import org.dimas4ek.wrapper.utils.JsonUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class AutoModRuleImpl implements AutoModRule {
    private final JsonNode rule;
    private final Guild guild;
    private String id;
    private String name;
    private User creator;
    private AutoModTriggerType triggerType;
    private AutoModTriggerMetadata triggerMetadata;
    private List<AutoModAction> actions;
    private Boolean isEnabled;
    private List<String> exemptRoles;
    private List<String> exemptChannels;

    public AutoModRuleImpl(JsonNode rule, Guild guild) {
        this.rule = rule;
        this.guild = guild;
    }

    @Override
    public String getId() {
        if (id == null) {
            id = rule.get("id").asText();
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
            name = rule.get("name").asText();
        }
        return name;
    }

    @Override
    public User getCreator() {
        if (creator == null) {
            creator = new UserImpl(JsonUtils.fetchEntity(Routes.User(rule.get("creator_id").asText())));
        }
        return creator;
    }

    @Override
    public AutoModEventType getEventType() {
        return AutoModEventType.MESSAGE_SEND;
    }

    @Override
    public AutoModTriggerType getTriggerType() {
        if (triggerType == null) {
            triggerType = EnumUtils.getEnumObject(rule, "trigger_type", AutoModTriggerType.class);
        }
        return triggerType;
    }

    @Override
    public AutoModTriggerMetadata getTriggerMetadata() {
        if (triggerMetadata == null) {
            triggerMetadata = new AutoModTriggerMetadataImpl(rule.get("trigger_metadata"));
        }
        return triggerMetadata;
    }

    @Override
    public List<AutoModAction> getActions() {
        if (actions == null) {
            actions = JsonUtils.getEntityList(rule.get("actions"), action -> new AutoModAction(action, guild));
        }
        return actions;
    }

    @Override
    public boolean isEnabled() {
        if (isEnabled == null) {
            isEnabled = rule.get("enabled").asBoolean();
        }
        return isEnabled;
    }

    @Override
    public List<String> getExemptRoles() {
        if (exemptRoles == null) {
            exemptRoles = getStringList(rule, "exempt_roles");
        }
        return exemptRoles;
    }

    @Override
    public List<String> getExemptChannels() {
        if (exemptChannels == null) {
            exemptChannels = getStringList(rule, "exempt_channels");
        }
        return exemptChannels;
    }

    @Override
    public void modify(Consumer<AutoModRuleModifyAction> handler) {
        ActionExecutor.modifyAutoModRule(handler, guild.getId(), getTriggerType());
    }

    @Override
    public void delete() {
        ApiClient.delete(Routes.Guild.AutoMod.Get(guild.getId(), getId()));
    }

    @NotNull
    private List<String> getStringList(JsonNode autoMod, String key) {
        List<String> list = new ArrayList<>();
        for (JsonNode node : autoMod.get(key)) {
            list.add(node.asText());
        }
        return list;
    }
}
