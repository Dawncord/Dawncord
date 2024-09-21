package io.github.dawncord.api.entities.guild.audit;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.command.SlashCommand;
import io.github.dawncord.api.entities.*;
import io.github.dawncord.api.entities.application.CommandPermission;
import io.github.dawncord.api.entities.channel.thread.Thread;
import io.github.dawncord.api.entities.channel.thread.ThreadImpl;
import io.github.dawncord.api.entities.guild.Guild;
import io.github.dawncord.api.entities.guild.automod.AutoModRule;
import io.github.dawncord.api.entities.guild.automod.AutoModRuleImpl;
import io.github.dawncord.api.entities.guild.event.GuildScheduledEvent;
import io.github.dawncord.api.entities.guild.event.GuildScheduledEventImpl;
import io.github.dawncord.api.entities.guild.integration.Integration;
import io.github.dawncord.api.entities.guild.integration.IntegrationImpl;
import io.github.dawncord.api.types.AuditLogEvent;
import io.github.dawncord.api.types.ChannelPermissionType;
import io.github.dawncord.api.types.PermissionOverrideType;
import io.github.dawncord.api.utils.EnumUtils;
import io.github.dawncord.api.utils.JsonUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.github.dawncord.api.types.AuditLogEvent.*;

/**
 * Represents an audit log for a guild, containing information about various actions performed within the guild.
 */
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

    /**
     * Constructs a new AuditLog object.
     *
     * @param audit The JSON representation of the audit log.
     * @param guild The guild to which this audit log belongs.
     */
    public AuditLog(JsonNode audit, Guild guild) {
        this.audit = audit;
        this.guild = guild;
    }

    /**
     * Retrieves the list of entries in the audit log.
     *
     * @return The list of entries in the audit log.
     */
    public List<Entry> getEntries() {
        if (entries == null) {
            entries = JsonUtils.getEntityList(audit.get("audit_log_entries"), entry -> new Entry(entry, guild));
        }
        return entries;
    }

    /**
     * Retrieves an audit log entry by its ID.
     *
     * @param entryId The ID of the entry to retrieve.
     * @return The audit log entry with the specified ID, or null if not found.
     */
    public Entry getEntryById(String entryId) {
        return getEntries().stream().filter(entry -> entry.getId().equals(entryId)).findFirst().orElse(null);
    }

    /**
     * Retrieves an audit log entry by its ID.
     *
     * @param entryId The ID of the entry to retrieve.
     * @return The audit log entry with the specified ID, or null if not found.
     */
    public Entry getEntryById(long entryId) {
        return getEntryById(String.valueOf(entryId));
    }

    /**
     * Retrieves a list of entries in the audit log associated with a specific user.
     *
     * @param userId The ID of the user.
     * @return The list of entries associated with the specified user.
     */
    public List<Entry> getEntriesByUserId(String userId) {
        return getEntries().stream().filter(entry -> entry.getUser().getId().equals(userId)).toList();
    }

    /**
     * Retrieves a list of entries in the audit log associated with a specific user.
     *
     * @param userId The ID of the user.
     * @return The list of entries associated with the specified user.
     */
    public List<Entry> getEntriesByUserId(long userId) {
        return getEntriesByUserId(String.valueOf(userId));
    }

    /**
     * Retrieves a list of entries in the audit log based on the action type.
     *
     * @param type The action type.
     * @return The list of entries with the specified action type.
     */
    public List<Entry> getEntriesByActionType(AuditLogEvent type) {
        return getEntries().stream().filter(entry -> entry.getActionType().equals(type)).toList();
    }

    /**
     * Retrieves a list of entries in the audit log based on the target ID.
     *
     * @param targetId The ID of the target.
     * @return The list of entries with the specified target ID.
     */
    public List<Entry> getEntriesByTargetId(String targetId) {
        return getEntries().stream().filter(entry -> entry.getTargetId().equals(targetId)).toList();
    }

    /**
     * Retrieves the list of auditors who performed actions in the audit log.
     *
     * @return The list of auditors.
     */
    public List<User> getAuditors() {
        if (auditors == null) {
            auditors = JsonUtils.getEntityList(audit.get("users"), UserImpl::new);
        }
        return auditors;
    }

    /**
     * Retrieves an auditor by their ID.
     *
     * @param userId The ID of the auditor.
     * @return The auditor with the specified ID, or null if not found.
     */
    public User getAuditorById(String userId) {
        return getAuditors().stream().filter(user -> user.getId().equals(userId)).findFirst().orElse(null);
    }

    /**
     * Retrieves the list of integrations associated with the guild.
     *
     * @return The list of integrations.
     */
    public List<Integration> getIntegrations() {
        if (integrations == null) {
            integrations = new ArrayList<>();
            Set<String> auditIntegrationIds = new HashSet<>();
            for (JsonNode auditIntegration : audit.get("integrations")) {
                auditIntegrationIds.add(auditIntegration.get("id").asText());
            }
            for (JsonNode guildIntegration : JsonUtils.fetch(Routes.Guild.Integration.All(guild.getId()))) {
                if (auditIntegrationIds.contains(guildIntegration.get("id").asText())) {
                    integrations.add(new IntegrationImpl(guildIntegration, guild));
                }
            }
        }
        return integrations;
    }

    /**
     * Retrieves an integration by its ID.
     *
     * @param integrationId The ID of the integration.
     * @return The integration with the specified ID, or null if not found.
     */
    public Integration getIntegrationById(String integrationId) {
        return getIntegrations().stream().filter(integration -> integration.getId().equals(integrationId)).findFirst().orElse(null);
    }

    /**
     * Retrieves an integration by its ID.
     *
     * @param integrationId The ID of the integration.
     * @return The integration with the specified ID, or null if not found.
     */
    public Integration getIntegrationById(long integrationId) {
        return getIntegrationById(String.valueOf(integrationId));
    }

    /**
     * Retrieves the list of webhooks associated with the guild.
     *
     * @return The list of webhooks.
     */
    public List<Webhook> getWebhooks() {
        if (webhooks == null) {
            webhooks = JsonUtils.getEntityList(audit.get("webhooks"), webhook -> new WebhookImpl(webhook, guild));
        }
        return webhooks;
    }

    /**
     * Retrieves a webhook by its ID.
     *
     * @param webhookId The ID of the webhook.
     * @return The webhook with the specified ID, or null if not found.
     */
    public Webhook getWebhookById(String webhookId) {
        return getWebhooks().stream().filter(webhook -> webhook.getId().equals(webhookId)).findFirst().orElse(null);
    }

    /**
     * Retrieves a webhook by its ID.
     *
     * @param webhookId The ID of the webhook.
     * @return The webhook with the specified ID, or null if not found.
     */
    public Webhook getWebhookById(long webhookId) {
        return getWebhookById(String.valueOf(webhookId));
    }

    /**
     * Retrieves the list of guild events.
     *
     * @return The list of guild events.
     */
    public List<GuildScheduledEvent> getGuildEvents() {
        if (guildEvents == null) {
            guildEvents = JsonUtils.getEntityList(audit.get("guild_scheduled_events"), event -> new GuildScheduledEventImpl(event, guild));
        }
        return guildEvents;
    }

    /**
     * Retrieves a guild event by its ID.
     *
     * @param eventId The ID of the guild event.
     * @return The guild event with the specified ID, or null if not found.
     */
    public GuildScheduledEvent getGuildEventById(String eventId) {
        return getGuildEvents().stream().filter(event -> event.getId().equals(eventId)).findFirst().orElse(null);
    }

    /**
     * Retrieves a guild event by its ID.
     *
     * @param eventId The ID of the guild event.
     * @return The guild event with the specified ID, or null if not found.
     */
    public GuildScheduledEvent getGuildEventById(long eventId) {
        return getGuildEventById(String.valueOf(eventId));
    }

    /**
     * Retrieves the list of threads associated with the guild.
     *
     * @return The list of threads.
     */
    public List<Thread> getThreads() {
        if (threads == null) {
            threads = JsonUtils.getEntityList(audit.get("threads"), thread -> new ThreadImpl(thread, guild));
        }
        return threads;
    }

    /**
     * Retrieves a thread by its ID.
     *
     * @param threadId The ID of the thread.
     * @return The thread with the specified ID, or null if not found.
     */
    public Thread getThreadById(String threadId) {
        return getThreads().stream().filter(thread -> thread.getId().equals(threadId)).findFirst().orElse(null);
    }

    /**
     * Retrieves a thread by its ID.
     *
     * @param threadId The ID of the thread.
     * @return The thread with the specified ID, or null if not found.
     */
    public Thread getThreadById(long threadId) {
        return getThreadById(String.valueOf(threadId));
    }

    /**
     * Retrieves the list of slash commands associated with the guild.
     *
     * @return The list of slash commands.
     */
    public List<SlashCommand> getSlashCommands() {
        if (slashCommands == null) {
            slashCommands = JsonUtils.getEntityList(audit.get("application_commands"), SlashCommand::new);
        }
        return slashCommands;
    }

    /**
     * Retrieves the list of auto moderation rules associated with the guild.
     *
     * @return The list of auto moderation rules.
     */
    public List<AutoModRule> getAutoModRules() {
        if (autoModRules == null) {
            autoModRules = JsonUtils.getEntityList(audit.get("auto_moderation_rules"), rule -> new AutoModRuleImpl(rule, guild));
        }
        return autoModRules;
    }

    /**
     * Represents an entry in the audit log.
     */
    public static class Entry implements ISnowflake {
        private final JsonNode entry;
        private final Guild guild;
        private String id;
        private AuditLogEvent actionType;
        private User user;
        private String targetId;
        private List<Change> changes;

        /**
         * Constructs an Entry object with the provided JSON node and guild.
         *
         * @param entry The JSON node representing the entry.
         * @param guild The guild associated with the entry.
         */
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

        /**
         * Retrieves the ID of the entry as a long value.
         *
         * @return The ID of the entry as a long value.
         */
        @Override
        public long getIdLong() {
            return Long.parseLong(getId());
        }

        /**
         * Retrieves the action type of the entry.
         *
         * @return The action type of the entry.
         */
        public AuditLogEvent getActionType() {
            if (actionType == null) {
                actionType = EnumUtils.getEnumObject(entry, "action_type", AuditLogEvent.class);
            }
            return actionType;
        }

        /**
         * Retrieves the user associated with the entry.
         *
         * @return The user associated with the entry, or null if not available.
         */
        public User getUser() {
            if (user == null) {
                user = entry.has("user_id") ? new UserImpl(JsonUtils.fetch(Routes.User(entry.get("user_id").asText()))) : null;
            }
            return user;
        }

        /**
         * Retrieves the target ID of the entry.
         *
         * @return The target ID of the entry, or null if not available.
         */
        public String getTargetId() {
            if (targetId == null) {
                targetId = entry.has("target_id") ? entry.get("target_id").asText() : null;
            }
            return targetId;
        }

        /**
         * Retrieves the target ID of the entry as a long value.
         *
         * @return The target ID of the entry as a long value, or 0 if not available.
         */
        public long getTargetIdLong() {
            String target = getTargetId();
            return target != null ? Long.parseLong(target) : 0;
        }

        /**
         * Retrieves the list of changes associated with the entry.
         *
         * @return The list of changes associated with the entry, or null if not available.
         */
        public List<Change> getChanges() {
            if (changes == null) {
                changes = entry.has("changes") && entry.hasNonNull("changes")
                        ? JsonUtils.getEntityList(entry.get("changes"), change -> new Change(change, guild))
                        : null;
            }
            return changes;
        }

        /**
         * Represents a change in the audit log entry.
         */
        public class Change {
            private final JsonNode change;
            private final Guild guild;
            private String key;
            private Object newValue;
            private Object oldValue;

            /**
             * Constructs a Change object with the provided JSON node and guild.
             *
             * @param change The JSON node representing the change.
             * @param guild  The guild associated with the change.
             */
            public Change(JsonNode change, Guild guild) {
                this.change = change;
                this.guild = guild;
            }

            /**
             * Retrieves the key associated with the change.
             *
             * @return The key associated with the change.
             */
            public String getKey() {
                if (key == null) {
                    key = change.get("key").asText();
                }
                return key;
            }

            /**
             * Retrieves the new value of the change.
             *
             * @return The new value of the change, or null if not available.
             */
            public Object getNewValue() {
                if (newValue == null) {
                    newValue = change.has("new_value") && change.hasNonNull("new_value")
                            ? getValue(change, guild, "new_value")
                            : null;
                }
                return newValue;
            }

            /**
             * Retrieves the old value of the change.
             *
             * @return The old value of the change, or null if not available.
             */
            public Object getOldValue() {
                if (oldValue == null) {
                    oldValue = change.has("old_value") && change.hasNonNull("old_value")
                            ? getValue(change, guild, "old_value")
                            : null;
                }
                return oldValue;
            }

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
                                    //List<PermissionType> allow = EnumUtils.getEnumListFromLong(value, "allow", PermissionType.class);
                                    //List<PermissionType> deny = EnumUtils.getEnumListFromLong(value, "deny", PermissionType.class);
                                    List<ChannelPermissionType> allow = EnumUtils.getEnumListFromLong(value, "allow", ChannelPermissionType.class);
                                    List<ChannelPermissionType> deny = EnumUtils.getEnumListFromLong(value, "deny", ChannelPermissionType.class);
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

                        default -> null;
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
