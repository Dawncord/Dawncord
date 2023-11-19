package org.dimas4ek.wrapper.entities.guild.audit;

import org.dimas4ek.wrapper.entities.*;
import org.dimas4ek.wrapper.entities.application.CommandPermission;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.guild.automod.AutoModRule;
import org.dimas4ek.wrapper.entities.guild.automod.AutoModRuleImpl;
import org.dimas4ek.wrapper.entities.guild.event.GuildEvent;
import org.dimas4ek.wrapper.entities.guild.event.GuildEventImpl;
import org.dimas4ek.wrapper.entities.guild.integration.Integration;
import org.dimas4ek.wrapper.entities.guild.integration.IntegrationImpl;
import org.dimas4ek.wrapper.entities.thread.Thread;
import org.dimas4ek.wrapper.entities.thread.ThreadImpl;
import org.dimas4ek.wrapper.slashcommand.SlashCommand;
import org.dimas4ek.wrapper.types.AuditLogEvent;
import org.dimas4ek.wrapper.types.PermissionOverrideType;
import org.dimas4ek.wrapper.types.PermissionType;
import org.dimas4ek.wrapper.utils.EnumUtils;
import org.dimas4ek.wrapper.utils.JsonUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.dimas4ek.wrapper.types.AuditLogEvent.*;

public class AuditLog {
    private final Guild guild;
    private final JSONObject audit;

    public AuditLog(Guild guild, JSONObject audit) {
        this.guild = guild;
        this.audit = audit;
    }

    public List<Entry> getEntries() {
        return JsonUtils.getEntityList(audit.getJSONArray("audit_log_entries"), Entry::new);
    }

    public List<Entry> getEntriesByUserId(String userId) {
        return getEntries().stream().filter(entry -> entry.getUser().getId().equals(userId)).toList();
    }

    public List<Entry> getEntriesByUserId(long userId) {
        return getEntriesByUserId(String.valueOf(userId));
    }

    public List<Entry> getEntriesByActionType(AuditLogEvent type) {
        return getEntries().stream().filter(entry -> entry.getActionType().equals(type)).toList();
    }

    public List<Entry> getEntriesByTargetId(String targetId) {
        return getEntries().stream().filter(entry -> entry.getTargetId().equals(targetId)).toList();
    }

    public List<User> getAuditors() {
        return JsonUtils.getEntityList(audit.getJSONArray("users"), UserImpl::new);
    }

    public User getAuditorById(String userId) {
        return getAuditors().stream().filter(user -> user.getId().equals(userId)).findFirst().orElse(null);
    }

    public List<Integration> getIntegrations() {
        List<Integration> integrations = new ArrayList<>();
        JSONArray guildIntegrations = JsonUtils.fetchArray("/guilds/" + guild.getId() + "/integrations");

        Set<String> auditIntegrationIds = new HashSet<>();
        JSONArray integrationsArray = audit.getJSONArray("integrations");
        for (int i = 0; i < integrationsArray.length(); i++) {
            auditIntegrationIds.add(integrationsArray.getJSONObject(i).getString("id"));
        }

        for (int j = 0; j < guildIntegrations.length(); j++) {
            JSONObject guildIntegration = guildIntegrations.getJSONObject(j);
            if (auditIntegrationIds.contains(guildIntegration.getString("id"))) {
                integrations.add(new IntegrationImpl(guild, guildIntegration));
            }
        }

        return integrations;
    }

    public Integration getIntegrationById(String integrationId) {
        return getIntegrations().stream().filter(integration -> integration.getId().equals(integrationId)).findFirst().orElse(null);
    }

    public Integration getIntegrationById(long integrationId) {
        return getIntegrationById(String.valueOf(integrationId));
    }

    public List<Webhook> getWebhooks() {
        return JsonUtils.getEntityList(audit.getJSONArray("webhooks"), WebhookImpl::new);
    }

    public Webhook getWebhookById(String webhookId) {
        return getWebhooks().stream().filter(webhook -> webhook.getId().equals(webhookId)).findFirst().orElse(null);
    }

    public Webhook getWebhookById(long webhookId) {
        return getWebhookById(String.valueOf(webhookId));
    }

    public List<GuildEvent> getGuildEvents() {
        return JsonUtils.getEntityList(audit.getJSONArray("guild_scheduled_events"), GuildEventImpl::new);
    }

    public GuildEvent getGuildEventById(String eventId) {
        return getGuildEvents().stream().filter(event -> event.getId().equals(eventId)).findFirst().orElse(null);
    }

    public GuildEvent getGuildEventById(long eventId) {
        return getGuildEventById(String.valueOf(eventId));
    }

    public List<Thread> getThreads() {
        return JsonUtils.getEntityList(audit.getJSONArray("threads"), ThreadImpl::new);
    }

    public Thread getThreadById(String threadId) {
        return getThreads().stream().filter(thread -> thread.getId().equals(threadId)).findFirst().orElse(null);
    }

    public Thread getThreadById(long threadId) {
        return getThreadById(String.valueOf(threadId));
    }

    public List<SlashCommand> getSlashCommands() {
        return JsonUtils.getEntityList(audit.getJSONArray("application_commands"), SlashCommand::new);
    }

    public List<AutoModRule> getAutoModRules() {
        return JsonUtils.getEntityList(audit.getJSONArray("auto_moderation_rules"), AutoModRuleImpl::new);
    }

    public class Entry {
        private final JSONObject entry;

        public Entry(JSONObject entry) {
            this.entry = entry;
        }

        public String getId() {
            return entry.getString("id");
        }

        public AuditLogEvent getActionType() {
            return EnumUtils.getEnumObject(entry, "action_type", AuditLogEvent.class);
        }

        public User getUser() {
            return new UserImpl(JsonUtils.fetchEntity("/users/" + entry.getString("user_id")));
        }

        public String getTargetId() {
            return entry.optString("target_id");
        }

        public long getTargetIdLong() {
            return Long.parseLong(getTargetId());
        }

        public List<Change> getChanges() {
            JSONArray changes = entry.optJSONArray("changes");
            return changes != null
                    ? JsonUtils.getEntityList(changes, Change::new)
                    : Collections.emptyList();
        }

        public class Change {
            private final JSONObject change;

            public Change(JSONObject change) {
                this.change = change;
            }

            public String getKey() {
                return change.getString("key");
            }

            public Object getNewValue() {
                return change.optString("new_value", null) != null
                        ? getValue("new_value")
                        : null;
            }

            public Object getOldValue() {
                return change.optString("old_value", null) != null
                        ? getValue("old_value")
                        : null;
            }

            private Object getValue(String key) {
                if (getActionType() == APPLICATION_COMMAND_PERMISSION_UPDATE) {
                    return new CommandPermission(change.getJSONObject(key));
                }
                if (isAutoModActionType()) {
                    return new AutoModRuleImpl(JsonUtils.fetchEntity("/guilds/" + guild.getId() + "/auto-moderation/rules/" + getTargetId()));
                }
                if (change.get(key) instanceof JSONArray valueArray) {
                    switch (getKey()) {
                        case "permission_overwrites" -> {
                            return IntStream.range(0, valueArray.length())
                                    .mapToObj(valueArray::getJSONObject)
                                    .map(value -> new PermissionOverride(
                                            value.getString("id"),
                                            EnumUtils.getEnumObject(value, "type", PermissionOverrideType.class),
                                            EnumUtils.getEnumListFromLong(value, "allow", PermissionType.class),
                                            EnumUtils.getEnumListFromLong(value, "deny", PermissionType.class)
                                    ))
                                    .collect(Collectors.toList());
                        }
                        case "applied_tags" -> {
                            return IntStream.range(0, valueArray.length())
                                    .mapToObj(valueArray::getString)
                                    .collect(Collectors.toList());
                        }
                        case "available_tags" -> {
                            return IntStream.range(0, valueArray.length())
                                    .mapToObj(valueArray::getJSONObject)
                                    .map(value -> new ForumTag(
                                            value.getString("id"),
                                            value.getString("name"),
                                            value.getBoolean("moderated"),
                                            !value.isNull("emoji_id") ? value.getString("emoji_id") : value.getString("emoji_name")
                                    ))
                                    .collect(Collectors.toList());
                        }
                        case "$add", "$remove" -> {
                            return IntStream.range(0, valueArray.length())
                                    .mapToObj(valueArray::getString)
                                    .map(guild::getRoleById)
                                    .collect(Collectors.toList());
                        }
                        default -> {
                            //todo add more keys support if needed
                            return null;
                        }
                    }
                } else {
                    return change.get(key);
                }
            }

            private boolean isAutoModActionType() {
                AuditLogEvent actionType = getActionType();
                return actionType == AUTO_MODERATION_RULE_CREATE ||
                        actionType == AUTO_MODERATION_RULE_UPDATE ||
                        actionType == AUTO_MODERATION_RULE_DELETE ||
                        actionType == AUTO_MODERATION_BLOCK_MESSAGE ||
                        actionType == AUTO_MODERATION_FLAG_TO_CHANNEL ||
                        actionType == AUTO_MODERATION_USER_COMMUNICATION_DISABLED;
            }
        }
    }
}
