package org.dimas4ek.wrapper.entities.guild.audit;

import com.fasterxml.jackson.databind.JsonNode;
import org.dimas4ek.wrapper.command.SlashCommand;
import org.dimas4ek.wrapper.entities.*;
import org.dimas4ek.wrapper.entities.application.CommandPermission;
import org.dimas4ek.wrapper.entities.channel.thread.Thread;
import org.dimas4ek.wrapper.entities.channel.thread.ThreadImpl;
import org.dimas4ek.wrapper.entities.guild.Guild;
import org.dimas4ek.wrapper.entities.guild.automod.AutoModRule;
import org.dimas4ek.wrapper.entities.guild.automod.AutoModRuleImpl;
import org.dimas4ek.wrapper.entities.guild.event.GuildScheduledEvent;
import org.dimas4ek.wrapper.entities.guild.event.GuildScheduledEventImpl;
import org.dimas4ek.wrapper.entities.guild.integration.Integration;
import org.dimas4ek.wrapper.entities.guild.integration.IntegrationImpl;
import org.dimas4ek.wrapper.types.AuditLogEvent;
import org.dimas4ek.wrapper.types.PermissionOverrideType;
import org.dimas4ek.wrapper.types.PermissionType;
import org.dimas4ek.wrapper.utils.EnumUtils;
import org.dimas4ek.wrapper.utils.JsonUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.dimas4ek.wrapper.types.AuditLogEvent.*;

public class AuditLog {
    private final JsonNode audit;
    private final Guild guild;
    private List<Entry> entries;
    private List<User> auditors;
    private List<Integration> integrations = new ArrayList<>();
    private List<Webhook> webhooks;
    private List<GuildScheduledEvent> guildEvents;
    private List<Thread> threads;
    private List<SlashCommand> slashCommands;
    private List<AutoModRule> autoModRules;

    public AuditLog(JsonNode audit, Guild guild) {
        this.audit = audit;
        this.guild = guild;
    }

    public List<Entry> getEntries() {
        if (entries == null) {
            entries = JsonUtils.getEntityList(audit.get("audit_log_entries"), entry -> new Entry(entry, guild));
        }
        return entries;
    }

    public Entry getEntryById(String entryId) {
        return getEntries().stream().filter(entry -> entry.getId().equals(entryId)).findFirst().orElse(null);
    }

    public Entry getEntryById(long entryId) {
        return getEntryById(String.valueOf(entryId));
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
        if (auditors == null) {
            auditors = JsonUtils.getEntityList(audit.get("users"), UserImpl::new);
        }
        return auditors;
    }

    public User getAuditorById(String userId) {
        return getAuditors().stream().filter(user -> user.getId().equals(userId)).findFirst().orElse(null);
    }

    public List<Integration> getIntegrations() {
        if (integrations == null) {
            integrations = new ArrayList<>();
            Set<String> auditIntegrationIds = new HashSet<>();
            for (JsonNode auditIntegration : audit.get("integrations")) {
                auditIntegrationIds.add(auditIntegration.get("id").asText());
            }
            for (JsonNode guildIntegration : JsonUtils.fetchArray("/guilds/" + guild.getId() + "/integrations")) {
                if (auditIntegrationIds.contains(guildIntegration.get("id").asText())) {
                    integrations.add(new IntegrationImpl(guildIntegration, guild));
                }
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
        if (webhooks == null) {
            webhooks = JsonUtils.getEntityList(audit.get("webhooks"), webhook -> new WebhookImpl(webhook, guild));
        }
        return webhooks;
    }

    public Webhook getWebhookById(String webhookId) {
        return getWebhooks().stream().filter(webhook -> webhook.getId().equals(webhookId)).findFirst().orElse(null);
    }

    public Webhook getWebhookById(long webhookId) {
        return getWebhookById(String.valueOf(webhookId));
    }

    public List<GuildScheduledEvent> getGuildEvents() {
        if (guildEvents == null) {
            guildEvents = JsonUtils.getEntityList(audit.get("guild_scheduled_events"), event -> new GuildScheduledEventImpl(event, guild));
        }
        return guildEvents;
    }

    public GuildScheduledEvent getGuildEventById(String eventId) {
        return getGuildEvents().stream().filter(event -> event.getId().equals(eventId)).findFirst().orElse(null);
    }

    public GuildScheduledEvent getGuildEventById(long eventId) {
        return getGuildEventById(String.valueOf(eventId));
    }

    public List<Thread> getThreads() {
        if (threads == null) {
            threads = JsonUtils.getEntityList(audit.get("threads"), thread -> new ThreadImpl(thread, guild));
        }
        return threads;
    }

    public Thread getThreadById(String threadId) {
        return getThreads().stream().filter(thread -> thread.getId().equals(threadId)).findFirst().orElse(null);
    }

    public Thread getThreadById(long threadId) {
        return getThreadById(String.valueOf(threadId));
    }

    public List<SlashCommand> getSlashCommands() {
        if (slashCommands == null) {
            slashCommands = JsonUtils.getEntityList(audit.get("application_commands"), SlashCommand::new);
        }
        return slashCommands;
    }

    public List<AutoModRule> getAutoModRules() {
        if (autoModRules == null) {
            autoModRules = JsonUtils.getEntityList(audit.get("auto_moderation_rules"), rule -> new AutoModRuleImpl(rule, guild));
        }
        return autoModRules;
    }

    public class Entry implements ISnowflake {
        private final JsonNode entry;
        private final Guild guild;
        private String id;
        private AuditLogEvent actionType;
        private User user;
        private String targetId;
        private List<Change> changes;

        public Entry(JsonNode entry, Guild guild) {
            this.entry = entry;
            this.guild = guild;
        }

        @Override
        public String getId() {
            if (id == null) {
                id = entry.get("id").asText();
            }
            return id;
        }

        @Override
        public long getIdLong() {
            return Long.parseLong(getId());
        }

        public AuditLogEvent getActionType() {
            if (actionType == null) {
                actionType = EnumUtils.getEnumObject(entry, "action_type", AuditLogEvent.class);
            }
            return actionType;
        }

        public User getUser() {
            if (user == null) {
                user = entry.has("user_id")
                        ? new UserImpl(JsonUtils.fetchEntity("/users/" + entry.get("user_id").asText()))
                        : null;
            }
            return user;
        }

        public String getTargetId() {
            if (targetId == null) {
                targetId = entry.has("target_id") ? entry.get("target_id").asText() : null;
            }
            return targetId;
        }

        public long getTargetIdLong() {
            return Long.parseLong(getTargetId());
        }

        public List<Change> getChanges() {
            if (changes == null) {
                changes = entry.has("changes") && entry.hasNonNull("changes")
                        ? JsonUtils.getEntityList(entry.get("changes"), change -> new Change(change, guild))
                        : null;
            }
            return changes;
        }

        public class Change {
            private final JsonNode change;
            private final Guild guild;
            private String key;
            private Object newValue;
            private Object oldValue;

            public Change(JsonNode change, Guild guild) {
                this.change = change;
                this.guild = guild;
            }

            public String getKey() {
                if (key == null) {
                    key = change.get("key").asText();
                }
                return key;
            }

            public Object getNewValue() {
                if (newValue == null) {
                    newValue = change.has("new_value") && change.hasNonNull("new_value")
                            ? getValue(change, guild, "new_value")
                            : null;
                }
                return newValue;
            }

            public Object getOldValue() {
                if (oldValue == null) {
                    oldValue = change.has("old_value") && change.hasNonNull("old_value")
                            ? getValue(change, guild, "old_value")
                            : null;
                }
                return oldValue;
            }

            //todo fix this
            private Object getValue(JsonNode change, Guild guild, String key) {
                if (actionType == APPLICATION_COMMAND_PERMISSION_UPDATE) {
                    return new CommandPermission(change.get(key));
                }
                if (isAutoModActionType()) {
                    return guild.getAutoModRuleById(getTargetId());
                }
                JsonNode valueArray = change.get(key);
                if (valueArray != null && valueArray.isArray()) {
                    return switch (key) {
                        case "permission_overwrites" -> IntStream.range(0, valueArray.size())
                                .mapToObj(index -> {
                                    JsonNode value = valueArray.get(index);
                                    String id = value.get("id").asText();
                                    PermissionOverrideType type = EnumUtils.getEnumObject(value, "type", PermissionOverrideType.class);
                                    List<PermissionType> allow = EnumUtils.getEnumListFromLong(value, "allow", PermissionType.class);
                                    List<PermissionType> deny = EnumUtils.getEnumListFromLong(value, "deny", PermissionType.class);
                                    return new PermissionOverride(id, type, allow, deny);
                                })
                                .collect(Collectors.toList());

                        case "applied_tags" -> IntStream.range(0, valueArray.size())
                                .mapToObj(index -> valueArray.get(index).asText())
                                .collect(Collectors.toList());

                        case "available_tags" -> IntStream.range(0, valueArray.size())
                                .mapToObj(index -> {
                                    JsonNode value = valueArray.get(index);
                                    String id = value.get("id").asText();
                                    String name = value.get("name").asText();
                                    boolean moderated = value.get("moderated").asBoolean();
                                    String emojiId = value.hasNonNull("emoji_id") ? value.get("emoji_id").asText() : value.get("emoji_name").asText();
                                    return new ForumTag(id, name, moderated, emojiId);
                                })
                                .collect(Collectors.toList());

                        case "$add", "$remove" -> IntStream.range(0, valueArray.size())
                                .mapToObj(index -> guild.getRoleById(valueArray.get(index).asText()))
                                .collect(Collectors.toList());

                        default -> null; //todo add more keys support if needed
                    };
                } else {
                    return change.get(key).asText();
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
