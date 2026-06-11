package io.github.dawncord.api.entities.guild.automod;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.ApiClient;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.action.automoderule.AutoModRuleModifyAction;
import io.github.dawncord.api.entities.ISnowflake;
import io.github.dawncord.api.entities.User;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.event.ModifyEvent;
import io.github.dawncord.api.types.AutoModEventType;
import io.github.dawncord.api.types.AutoModTriggerType;
import io.github.dawncord.api.utils.ActionExecutor;
import io.github.dawncord.api.utils.JsonUtils;
import io.github.dawncord.api.utils.LazyLoader;

import java.util.List;
import java.util.function.Consumer;


/**
 * Represents an auto moderation rule in a guild.
 */
public class AutoModRule implements ISnowflake {
    private final LazyLoader loader;
    private final JsonNode rule;
    private final Guild guild;
    private String id;
    private String name;
    private User creator;
    private AutoModTriggerType triggerType;
    private AutoModTriggerMetadata triggerMetadata;
    private List<AutoModAction> actions;
    private Boolean enabled;
    private List<String> exemptRoles;
    private List<String> exemptChannels;

    /**
     * Constructs an AutoModRule object.
     *
     * @param rule  The JSON node representing the auto-mod rule.
     * @param guild The guild to which the auto-mod rule belongs.
     */
    public AutoModRule(JsonNode rule, Guild guild) {
        this.rule = rule;
        this.guild = guild;
        loader = new LazyLoader(rule);
    }

    @Override
    public String getId() {
        id = loader.loadString(id, "id");
        return id;
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
    }

    public String getName() {
        name = loader.loadString(name, "name");
        return name;
    }

    public User getCreator() {
        creator = loader.loadIfExists(creator, "user_id",
                () -> new User(JsonUtils.fetch(Routes.User(rule.get("creator_id").asText()))));
        return creator;
    }

    public AutoModEventType getEventType() {
        return AutoModEventType.MESSAGE_SEND;
    }

    public AutoModTriggerType getTriggerType() {
        triggerType = loader.loadEnumObject(triggerType, "trigger_type", AutoModTriggerType.class);
        return triggerType;
    }

    public AutoModTriggerMetadata getTriggerMetadata() {
        triggerMetadata = loader.load(triggerMetadata, () -> new AutoModTriggerMetadata(rule.get("trigger_metadata")));
        return triggerMetadata;
    }

    public List<AutoModAction> getActions() {
        actions = loader.loadEntityList(actions, "actions", action -> new AutoModAction(action, guild));
        return actions;
    }

    public boolean isEnabled() {
        enabled = loader.loadBoolean(enabled, "enabled");
        return enabled;
    }

    public List<String> getExemptRoles() {
        exemptRoles = loader.loadStringList(exemptRoles, "exempt_roles");
        return exemptRoles;
    }

    public List<String> getExemptChannels() {
        exemptChannels = loader.loadStringList(exemptChannels, "exempt_channels");
        return exemptChannels;
    }

    public ModifyEvent<AutoModRule> modify(Consumer<AutoModRuleModifyAction> handler) {
        ActionExecutor.modifyAutoModRule(handler, guild.getId(), getTriggerType());
        return new ModifyEvent<>(guild.getAutoModRuleById(getId()));
    }

    public void delete() {
        ApiClient.delete(Routes.Guild.AutoMod.Get(guild.getId(), getId()));
    }
}
