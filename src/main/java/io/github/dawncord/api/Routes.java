package io.github.dawncord.api;

/**
 * Utility class for defining routes to various API endpoints.
 */
public class Routes {
    /**
     * Routes related to guild operations.
     */
    public static class Guild {
        /**
         * Routes related to scheduled events within a guild.
         */
        public static class ScheduledEvent {
            /**
             * Constructs the route to get a specific scheduled event in a guild.
             *
             * @param guildId The ID of the guild.
             * @param eventId The ID of the event.
             * @return The route to get the specific scheduled event.
             */
            public static String Get(String guildId, String eventId) {
                return "/guilds/" + guildId + "/scheduled-events/" + eventId;
            }

            /**
             * Constructs the route to get all scheduled events in a guild.
             *
             * @param guildId The ID of the guild.
             * @return The route to get all scheduled events.
             */
            public static String All(String guildId) {
                return "/guilds/" + guildId + "/scheduled-events";
            }

            /**
             * Constructs the route to get members of a specific scheduled event in a guild.
             *
             * @param guildId The ID of the guild.
             * @param eventId The ID of the event.
             * @return The route to get members of the scheduled event.
             */
            public static String Members(String guildId, String eventId) {
                return "/guilds/" + guildId + "/scheduled-events/" + eventId + "/users";
            }
        }

        /**
         * Routes related to guild member operations.
         */
        public static class Member {
            /**
             * Constructs the route to get a specific member in a guild.
             *
             * @param guildId The ID of the guild.
             * @param userId  The ID of the user.
             * @return The route to get the specific member.
             */
            public static String Get(String guildId, String userId) {
                return "/guilds/" + guildId + "/members/" + userId;
            }

            /**
             * Constructs the route to get all members in a guild.
             *
             * @param guildId The ID of the guild.
             * @return The route to get all members.
             */
            public static String All(String guildId) {
                return "/guilds/" + guildId + "/members";
            }

            /**
             * Constructs the route to search for members in a guild.
             *
             * @param guildId The ID of the guild.
             * @return The route to search for members.
             */
            public static String Search(String guildId) {
                return "/guilds/" + guildId + "/members/search";
            }

            /**
             * Constructs the route to assign a role to a member in a guild.
             *
             * @param guildId The ID of the guild.
             * @param userId  The ID of the user.
             * @param roleId  The ID of the role.
             * @return The route to assign the role to the member.
             */
            public static String Role(String guildId, String userId, String roleId) {
                return "/guilds/" + guildId + "/members/" + userId + "/roles/" + roleId;
            }
        }

        /**
         * Routes related to stickers in a guild.
         */
        public static class Sticker {
            /**
             * Constructs the route to get a specific sticker in a guild.
             *
             * @param guildId   The ID of the guild.
             * @param stickerId The ID of the sticker.
             * @return The route to get the specific sticker.
             */
            public static String Get(String guildId, String stickerId) {
                return "/guilds/" + guildId + "/stickers/" + stickerId;
            }

            /**
             * Constructs the route to get all stickers in a guild.
             *
             * @param guildId The ID of the guild.
             * @return The route to get all stickers.
             */
            public static String All(String guildId) {
                return "/guilds/" + guildId + "/stickers";
            }
        }

        /**
         * Routes related to emojis in a guild.
         */
        public static class Emoji {
            /**
             * Constructs the route to get a specific emoji in a guild.
             *
             * @param guildId The ID of the guild.
             * @param emojiId The ID of the emoji.
             * @return The route to get the specific emoji.
             */
            public static String Get(String guildId, String emojiId) {
                return "/guilds/" + guildId + "/emojis/" + emojiId;
            }

            /**
             * Constructs the route to get all emojis in a guild.
             *
             * @param guildId The ID of the guild.
             * @return The route to get all emojis.
             */
            public static String All(String guildId) {
                return "/guilds/" + guildId + "/emojis";
            }
        }

        /**
         * Routes related to guild widgets.
         */
        public static class Widget {
            /**
             * Constructs the route to get the widget settings JSON for a guild.
             *
             * @param guildId The ID of the guild.
             * @return The route to get the widget settings JSON.
             */
            public static String Get(String guildId) {
                return "/guilds/" + guildId + "/widget.json";
            }

            /**
             * Constructs the route to access or modify the widget settings for a guild.
             *
             * @param guildId The ID of the guild.
             * @return The route to access or modify the widget settings.
             */
            public static String Settings(String guildId) {
                return "/guilds/" + guildId + "/widget";
            }
        }

        /**
         * Routes related to bans in a guild.
         */
        public static class Ban {
            /**
             * Constructs the route to get information about a ban in a guild.
             *
             * @param guildId  The ID of the guild.
             * @param memberId The ID of the banned member.
             * @return The route to get ban information.
             */
            public static String Get(String guildId, String memberId) {
                return "/guilds/" + guildId + "/bans/" + memberId;
            }

            /**
             * Constructs the route to get a list of all bans in a guild.
             *
             * @param guildId The ID of the guild.
             * @return The route to get a list of all bans.
             */
            public static String All(String guildId) {
                return "/guilds/" + guildId + "/bans";
            }
        }

        /**
         * Routes related to roles in a guild.
         */
        public static class Role {
            /**
             * Constructs the route to get information about a specific role in a guild.
             *
             * @param guildId The ID of the guild.
             * @param roleId  The ID of the role.
             * @return The route to get role information.
             */
            public static String Get(String guildId, String roleId) {
                return "/guilds/" + guildId + "/roles/" + roleId;
            }

            /**
             * Constructs the route to get a list of all roles in a guild.
             *
             * @param guildId The ID of the guild.
             * @return The route to get a list of all roles.
             */
            public static String All(String guildId) {
                return "/guilds/" + guildId + "/roles";
            }
        }

        /**
         * Routes related to integrations in a guild.
         */
        public static class Integration {
            /**
             * Constructs the route to get information about a specific integration in a guild.
             *
             * @param guildId       The ID of the guild.
             * @param integrationId The ID of the integration.
             * @return The route to get integration information.
             */
            public static String Get(String guildId, String integrationId) {
                return "/guilds/" + guildId + "/integrations/" + integrationId;
            }

            /**
             * Constructs the route to get a list of all integrations in a guild.
             *
             * @param guildId The ID of the guild.
             * @return The route to get a list of all integrations.
             */
            public static String All(String guildId) {
                return "/guilds/" + guildId + "/integrations";
            }
        }

        /**
         * Routes related to auto-moderation rules in a guild.
         */
        public static class AutoMod {
            /**
             * Constructs the route to get information about a specific auto-moderation rule in a guild.
             *
             * @param guildId The ID of the guild.
             * @param ruleId  The ID of the auto-moderation rule.
             * @return The route to get auto-moderation rule information.
             */
            public static String Get(String guildId, String ruleId) {
                return "/guilds/" + guildId + "/auto-moderation/rules/" + ruleId;
            }

            /**
             * Constructs the route to get a list of all auto-moderation rules in a guild.
             *
             * @param guildId The ID of the guild.
             * @return The route to get a list of all auto-moderation rules.
             */
            public static String All(String guildId) {
                return "/guilds/" + guildId + "/auto-moderation/rules";
            }
        }

        /**
         * Constructs the route to get information about a specific guild.
         *
         * @param guildId The ID of the guild.
         * @return The route to get guild information.
         */
        public static String Get(String guildId) {
            return "/guilds/" + guildId;
        }

        /**
         * Constructs the route to get a list of all guilds.
         *
         * @return The route to get a list of all guilds.
         */
        public static String All() {
            return "/guilds";
        }

        /**
         * Constructs the route to access channels within a guild.
         *
         * @param guildId The ID of the guild.
         * @return The route to access guild channels.
         */
        public static String Channels(String guildId) {
            return "/guilds/" + guildId + "/channels";
        }

        /**
         * Constructs the route to enable or disable MFA requirement in a guild.
         *
         * @param guildId The ID of the guild.
         * @return The route to manage MFA.
         */
        public static String Mfa(String guildId) {
            return "/guilds/" + guildId + "/mfa";
        }

        /**
         * Constructs the route to retrieve active threads in a guild.
         *
         * @param guildId The ID of the guild.
         * @return The route to retrieve active threads.
         */
        public static String ActiveThreads(String guildId) {
            return "/guilds/" + guildId + "/threads/active";
        }

        /**
         * Constructs the route to retrieve voice regions available in a guild.
         *
         * @param guildId The ID of the guild.
         * @return The route to retrieve voice regions.
         */
        public static String VoiceRegions(String guildId) {
            return "/guilds/" + guildId + "/regions";
        }

        /**
         * Constructs the route to manage invites in a guild.
         *
         * @param guildId The ID of the guild.
         * @return The route to manage invites.
         */
        public static String Invites(String guildId) {
            return "/guilds/" + guildId + "/invites";
        }

        /**
         * Constructs the route to manage webhooks in a guild.
         *
         * @param guildId The ID of the guild.
         * @return The route to manage webhooks.
         */
        public static String Webhooks(String guildId) {
            return "/guilds/" + guildId + "/webhooks";
        }

        /**
         * Constructs the route to access audit logs in a guild.
         *
         * @param guildId The ID of the guild.
         * @return The route to access audit logs.
         */
        public static String AuditLog(String guildId) {
            return "/guilds/" + guildId + "/audit-logs";
        }

        /**
         * Constructs the route to preview guild changes.
         *
         * @param guildId The ID of the guild.
         * @return The route to preview guild changes.
         */
        public static String Preview(String guildId) {
            return "/guilds/" + guildId + "/preview";
        }

        /**
         * Constructs the route to manage onboarding in a guild.
         *
         * @param guildId The ID of the guild.
         * @return The route to manage onboarding.
         */
        public static String Onboarding(String guildId) {
            return "/guilds/" + guildId + "/onboarding";
        }

        /**
         * Constructs the route to manage the welcome screen in a guild.
         *
         * @param guildId The ID of the guild.
         * @return The route to manage the welcome screen.
         */
        public static String WelcomeScreen(String guildId) {
            return "/guilds/" + guildId + "/welcome-screen";
        }
    }

    /**
     * Routes related to channels.
     */
    public static class Channel {
        /**
         * Routes related to messages within channels.
         */
        public static class Message {
            /**
             * Routes related to reactions on messages.
             */
            public static class Reaction {
                /**
                 * Constructs the route to get reactions on a specific message with a given emoji.
                 *
                 * @param channelId     The ID of the channel containing the message.
                 * @param messageId     The ID of the message.
                 * @param emojiIdOrName The ID or name of the emoji.
                 * @return The route to get reactions on the message with the specified emoji.
                 */
                public static String Get(String channelId, String messageId, String emojiIdOrName) {
                    return "/channels/" + channelId + "/messages/" + messageId + "/reactions/" + emojiIdOrName;
                }

                /**
                 * Constructs the route to get all reactions on a specific message.
                 *
                 * @param channelId The ID of the channel containing the message.
                 * @param messageId The ID of the message.
                 * @return The route to get all reactions on the message.
                 */
                public static String All(String channelId, String messageId) {
                    return "/channels/" + channelId + "/messages/" + messageId + "/reactions";
                }

                /**
                 * Constructs the route to get reactions from a specific user on a specific message with a given emoji.
                 *
                 * @param channelId     The ID of the channel containing the message.
                 * @param messageId     The ID of the message.
                 * @param emojiIdOrName The ID or name of the emoji.
                 * @param userId        The ID of the user.
                 * @return The route to get reactions from the user on the message with the specified emoji.
                 */
                public static String ByUser(String channelId, String messageId, String emojiIdOrName, String userId) {
                    return "/channels/" + channelId + "/messages/" + messageId + "/reactions/" + emojiIdOrName + "/" + userId;
                }
            }

            /**
             * Routes related to polls within messages.
             */
            public static class Poll {
                /**
                 * Constructs the route to get voters who answered a specific poll answer within a message.
                 *
                 * @param channelId The ID of the channel containing the message.
                 * @param messageId The ID of the message containing the poll.
                 * @param answerId  The ID of the poll answer.
                 * @return The route to get voters who answered the poll answer within the message.
                 */
                public static String GetAnswerVoters(String channelId, String messageId, String answerId) {
                    return "/channels/" + channelId + "/polls/" + messageId + "/answers/" + answerId;
                }

                /**
                 * Constructs the route to end a poll within a message.
                 *
                 * @param channelId The ID of the channel containing the message.
                 * @param messageId The ID of the message containing the poll to end.
                 * @return The route to end the poll within the message.
                 */
                public static String End(String channelId, String messageId) {
                    return "/channels/" + channelId + "/polls/" + messageId + "/expire";
                }
            }

            /**
             * Constructs the route to retrieve a specific message within a channel.
             *
             * @param channelId The ID of the channel containing the message.
             * @param messageId The ID of the message to retrieve.
             * @return The route to retrieve the message.
             */
            public static String Get(String channelId, String messageId) {
                return "/channels/" + channelId + "/messages/" + messageId;
            }

            /**
             * Constructs the route to retrieve all messages within a channel.
             *
             * @param channelId The ID of the channel.
             * @return The route to retrieve all messages within the channel.
             */
            public static String All(String channelId) {
                return "/channels/" + channelId + "/messages";
            }

            /**
             * Constructs the route to retrieve threads associated with a message within a channel.
             *
             * @param channelId The ID of the channel containing the message.
             * @param messageId The ID of the message.
             * @return The route to retrieve threads associated with the message.
             */
            public static String Threads(String channelId, String messageId) {
                return "/channels/" + channelId + "/messages/" + messageId + "/threads";
            }

            /**
             * Constructs the route to delete messages in bulk within a channel.
             *
             * @param channelId The ID of the channel.
             * @return The route to delete messages in bulk within the channel.
             */
            public static String ToDelete(String channelId) {
                return "/channels/" + channelId + "/messages/bulk-delete";
            }

            /**
             * Routes related to pinning messages within channels.
             */
            public static class Pin {
                /**
                 * Constructs the route to retrieve a pinned message within a channel.
                 *
                 * @param channelId The ID of the channel containing the pinned message.
                 * @param messageId The ID of the pinned message.
                 * @return The route to retrieve the pinned message.
                 */
                public static String Get(String channelId, String messageId) {
                    return "/channels/" + channelId + "/pins/" + messageId;
                }

                /**
                 * Constructs the route to retrieve all pinned messages within a channel.
                 *
                 * @param channelId The ID of the channel.
                 * @return The route to retrieve all pinned messages within the channel.
                 */
                public static String All(String channelId) {
                    return "/channels/" + channelId + "/pins";
                }
            }
        }

        /**
         * Routes related to invites within channels.
         */
        public static class Invite {
            /**
             * Constructs the route to retrieve an invite by its code within a channel.
             *
             * @param code The invite code.
             * @return The route to retrieve the invite by its code.
             */
            public static String Get(String code) {
                return "/channels/" + code;
            }

            /**
             * Constructs the route to retrieve all invites within a channel.
             *
             * @param channelId The ID of the channel.
             * @return The route to retrieve all invites within the channel.
             */
            public static String All(String channelId) {
                return "/channels/" + channelId + "/invites";
            }
        }

        /**
         * Routes related to threads within channels.
         */
        public static class Thread {
            /**
             * Constructs the route to retrieve all threads within a channel.
             *
             * @param channelId The ID of the channel.
             * @return The route to retrieve all threads within the channel.
             */
            public static String All(String channelId) {
                return "/channels/" + channelId + "/threads";
            }

            /**
             * Routes related to archived threads within channels.
             */
            public static class Archive {
                /**
                 * Constructs the route to archive public threads within a channel.
                 *
                 * @param channelId The ID of the channel.
                 * @return The route to archive public threads within the channel.
                 */
                public static String Public(String channelId) {
                    return "/channels/" + channelId + "/threads/archived/public";
                }

                /**
                 * Constructs the route to archive private threads within a channel.
                 *
                 * @param channelId The ID of the channel.
                 * @return The route to archive private threads within the channel.
                 */
                public static String Private(String channelId) {
                    return "/channels/" + channelId + "/threads/archived/private";
                }

                /**
                 * Constructs the route to archive private threads joined by a specific user within a channel.
                 *
                 * @param channelId The ID of the channel.
                 * @param userId    The ID of the user.
                 * @return The route to archive private threads joined by the user within the channel.
                 */
                public static String JoinedPrivate(String channelId, String userId) {
                    return "/channels/" + channelId + "/users/" + userId + "/threads/archived/private";
                }
            }

            /**
             * Routes related to members within threads.
             */
            public static class Member {
                /**
                 * Constructs the route to retrieve a member of a thread within a channel.
                 *
                 * @param channelId The ID of the channel.
                 * @param userId    The ID of the user.
                 * @return The route to retrieve the thread member.
                 */
                public static String Get(String channelId, String userId) {
                    return "/channels/" + channelId + "/thread-members/" + userId;
                }

                /**
                 * Constructs the route to retrieve all members of threads within a channel.
                 *
                 * @param channelId The ID of the channel.
                 * @return The route to retrieve all thread members.
                 */
                public static String All(String channelId) {
                    return "/channels/" + channelId + "/thread-members";
                }
            }
        }

        /**
         * Constructs the route to retrieve a specific channel.
         *
         * @param channelId The ID of the channel to retrieve.
         * @return The route to retrieve the channel.
         */
        public static String Get(String channelId) {
            return "/channels/" + channelId;
        }

        /**
         * Constructs the route to retrieve webhooks within a channel.
         *
         * @param channelId The ID of the channel.
         * @return The route to retrieve webhooks within the channel.
         */
        public static String Webhooks(String channelId) {
            return "/channels/" + channelId + "/webhooks";
        }

        /**
         * Constructs the route to manage permissions within a channel.
         *
         * @param channelId    The ID of the channel.
         * @param permissionId The ID of the permission.
         * @return The route to manage permissions within the channel.
         */
        public static String Permission(String channelId, String permissionId) {
            return "/channels/" + channelId + "/permissions/" + permissionId;
        }
    }

    /**
     * Routes related to retrieving various types of icons.
     */
    public static class Icon {
        /**
         * Constructs the route to retrieve the avatar of a user within a guild.
         *
         * @param guildId The ID of the guild.
         * @param userId  The ID of the user.
         * @param hash    The avatar hash.
         * @param format  The image format.
         * @return The route to retrieve the user's avatar within the guild.
         */
        public static String GuildUserAvatar(String guildId, String userId, String hash, String format) {
            return Constants.CDN_URL + "/guilds/" + guildId + "/users/" + userId + "/avatars/" + hash + format;
        }

        /**
         * Constructs the route to retrieve an image associated with a guild event.
         *
         * @param eventId The ID of the guild event.
         * @param hash    The image hash.
         * @param format  The image format.
         * @return The route to retrieve the image associated with the guild event.
         */
        public static String GuildEventImage(String eventId, String hash, String format) {
            return Constants.CDN_URL + "/guild-events/" + eventId + "/" + hash + format;
        }

        /**
         * Constructs the route to retrieve the icon of a guild.
         *
         * @param guildId The ID of the guild.
         * @param hash    The icon hash.
         * @param format  The image format.
         * @return The route to retrieve the guild's icon.
         */
        public static String GuildIcon(String guildId, String hash, String format) {
            return Constants.CDN_URL + "/icons/" + guildId + "/" + hash + format;
        }

        /**
         * Constructs the route to retrieve an image associated with an activity.
         *
         * @param applicationId The ID of the application.
         * @param imageId       The ID of the image.
         * @param format        The image format.
         * @return The route to retrieve the image associated with the activity.
         */
        public static String ActivityImage(String applicationId, String imageId, String format) {
            return Constants.CDN_URL + "/app-assets/" + applicationId + "/" + imageId + "." + format;
        }

        /**
         * Constructs the route to retrieve the icon of an application.
         *
         * @param applicationId The ID of the application.
         * @param hash          The icon hash.
         * @param format        The image format.
         * @return The route to retrieve the application's icon.
         */
        public static String ApplicationIcon(String applicationId, String hash, String format) {
            return Constants.CDN_URL + "/app-icons/" + applicationId + "/" + hash + format;
        }

        /**
         * Constructs the route to retrieve the avatar of a user.
         *
         * @param userId The ID of the user.
         * @param hash   The avatar hash.
         * @param format The image format.
         * @return The route to retrieve the user's avatar.
         */
        public static String UserAvatar(String userId, String hash, String format) {
            return Constants.CDN_URL + "/avatars/" + userId + "/" + hash + format;
        }

        /**
         * Constructs the route to retrieve the discovery splash image of a guild.
         *
         * @param guildId The ID of the guild.
         * @param hash    The image hash.
         * @param format  The image format.
         * @return The route to retrieve the guild's discovery splash image.
         */
        public static String DiscoverySplash(String guildId, String hash, String format) {
            return Constants.CDN_URL + "/discovery-splashes/" + guildId + "/" + hash + format;
        }

        /**
         * Constructs the route to retrieve the icon of a role within a guild.
         *
         * @param roleId The ID of the role.
         * @param hash   The icon hash.
         * @param format The image format.
         * @return The route to retrieve the role's icon within the guild.
         */
        public static String RoleIcon(String roleId, String hash, String format) {
            return Constants.CDN_URL + "/role-icons/" + roleId + "/" + hash + format;
        }

        /**
         * Constructs the route to retrieve the splash image of a guild.
         *
         * @param guildId The ID of the guild.
         * @param hash    The image hash.
         * @param format  The image format.
         * @return The route to retrieve the guild's splash image.
         */
        public static String Splash(String guildId, String hash, String format) {
            return Constants.CDN_URL + "/splashes/" + guildId + "/" + hash + format;
        }

        /**
         * Constructs the route to retrieve the icon of a team.
         *
         * @param teamId The ID of the team.
         * @param hash   The icon hash.
         * @param format The image format.
         * @return The route to retrieve the team's icon.
         */
        public static String TeamIcon(String teamId, String hash, String format) {
            return Constants.CDN_URL + "/team-icons/" + teamId + "/" + hash + format;
        }
    }

    /**
     * Routes related to slash commands.
     */
    public static class SlashCommand {
        /**
         * Constructs the route to retrieve a specific slash command by its ID.
         *
         * @param commandId The ID of the slash command.
         * @return The route to retrieve the slash command by ID.
         */
        public static String Get(String commandId) {
            return "/applications/" + Constants.APPLICATION_ID + "/commands/" + commandId;
        }

        /**
         * Constructs the route to retrieve all slash commands.
         *
         * @return The route to retrieve all slash commands.
         */
        public static String All() {
            return "/applications/" + Constants.APPLICATION_ID + "/commands";
        }
    }

    /**
     * Routes related to webhooks.
     */
    public static class Webhook {
        /**
         * Constructs the route to access a webhook by its ID.
         *
         * @param webhookId The ID of the webhook.
         * @return The route to access the webhook by ID.
         */
        public static String ById(String webhookId) {
            return "/webhooks/" + webhookId;
        }

        /**
         * Constructs the route to access a webhook by its ID and token.
         *
         * @param webhookId    The ID of the webhook.
         * @param webhookToken The token of the webhook.
         * @return The route to access the webhook by ID and token.
         */
        public static String ByToken(String webhookId, String webhookToken) {
            return "/webhooks/" + webhookId + "/" + webhookToken;
        }
    }

    /**
     * Constructs the route to the original message of an interaction.
     *
     * @param interactionToken The token of the interaction.
     * @return The route to the original message of the interaction.
     */
    public static String OriginalMessage(String interactionToken) {
        return "/webhooks/" + Constants.APPLICATION_ID + "/" + interactionToken + "/messages/@original";
    }

    /**
     * Constructs the route to the application.
     *
     * @return The route to the application.
     */
    public static String Application() {
        return "/applications/@me";
    }

    /**
     * Constructs the route to reply to an interaction.
     *
     * @param interactionId    The ID of the interaction.
     * @param interactionToken The token of the interaction.
     * @return The route to reply to the interaction.
     */
    public static String Reply(String interactionId, String interactionToken) {
        return "/interactions/" + interactionId + "/" + interactionToken + "/callback";
    }

    /**
     * Constructs the route to retrieve stage instances.
     *
     * @return The route to retrieve stage instances.
     */
    public static String StageInstances() {
        return "/stage-instances";
    }

    /**
     * Constructs the route to indicate typing in a channel.
     *
     * @param channelId The ID of the channel.
     * @return The route to indicate typing in the channel.
     */
    public static String Typing(String channelId) {
        return "/channels/" + channelId + "/typing";
    }

    /**
     * Constructs the route to a specific stage instance.
     *
     * @param channelId The ID of the channel.
     * @return The route to the specific stage instance.
     */
    public static String StageInstance(String channelId) {
        return "/stage-instances/" + channelId;
    }

    /**
     * Constructs the route to the current member of a guild.
     *
     * @param guildId The ID of the guild.
     * @return The route to the current member of the guild.
     */
    public static String CurrentMember(String guildId) {
        return "/guilds/" + guildId + "/members/@me";
    }

    /**
     * Constructs the route to a specific user.
     *
     * @param userId The ID of the user.
     * @return The route to the specific user.
     */
    public static String User(String userId) {
        return "/users/" + userId;
    }

    /**
     * Constructs the route to retrieve voice regions.
     *
     * @return The route to retrieve voice regions.
     */
    public static String VoiceRegions() {
        return "/voice/regions";
    }
}
