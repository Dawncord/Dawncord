package org.dimas4ek.wrapper;

public class Routes {
    public static class Guild {
        public static class ScheduledEvent {
            public static String Get(String guildId, String eventId) {
                return Constants.API_URL + "/guilds/" + guildId + "/scheduled-events/" + eventId;
            }

            public static String All(String guildId) {
                return Constants.API_URL + "/guilds/" + guildId + "/scheduled-events";
            }

            public static String Members(String guildId, String eventId) {
                return Constants.API_URL + "/guilds/" + guildId + "/scheduled-events/" + eventId + "/users";
            }
        }

        public static class Member {
            public static String Get(String guildId, String userId) {
                return Constants.API_URL + "/guilds/" + guildId + "/members/" + userId;
            }

            public static String All(String guildId) {
                return Constants.API_URL + "/guilds/" + guildId + "/members";
            }

            public static String Search(String guildId) {
                return Constants.API_URL + "/guilds/" + guildId + "/members/search";
            }

            public static String Role(String guildId, String userId, String roleId) {
                return Constants.API_URL + "/guilds/" + guildId + "/members/" + userId + "/roles/" + roleId;
            }
        }

        public static class Sticker {
            public static String Get(String guildId, String stickerId) {
                return Constants.API_URL + "/guilds/" + guildId + "/stickers/" + stickerId;
            }

            public static String All(String guildId) {
                return "/guilds/" + guildId + "/stickers";
            }
        }

        public static class Emoji {
            public static String Get(String guildId, String emojiId) {
                return Constants.API_URL + "/guilds/" + guildId + "/emojis/" + emojiId;
            }

            public static String All(String guildId) {
                return Constants.API_URL + "/guilds/" + guildId + "/emojis";
            }
        }

        public static class Widget {
            public static String Get(String guildId) {
                return Constants.API_URL + "/guilds/" + guildId + "/widget.json";
            }

            public static String Settings(String guildId) {
                return Constants.API_URL + "/guilds/" + guildId + "/widget";
            }
        }

        public static class Ban {
            public static String Get(String guildId, String memberId) {
                return Constants.API_URL + "/guilds/" + guildId + "/bans/" + memberId;
            }

            public static String All(String guildId) {
                return Constants.API_URL + "/guilds/" + guildId + "/bans";
            }
        }

        public static class Role {
            public static String Get(String guildId, String roleId) {
                return Constants.API_URL + "/guilds/" + guildId + "/roles/" + roleId;
            }

            public static String All(String guildId) {
                return Constants.API_URL + "/guilds/" + guildId + "/roles";
            }
        }

        public static class Integration {
            public static String Get(String guildId, String integrationId) {
                return Constants.API_URL + "/guilds/" + guildId + "/integrations/" + integrationId;
            }

            public static String All(String guildId) {
                return Constants.API_URL + "/guilds/" + guildId + "/invites";
            }
        }

        public static class AutoMod {
            public static String Get(String guildId, String ruleId) {
                return Constants.API_URL + "/guilds/" + guildId + "/auto-moderation/rules/" + ruleId;
            }

            public static String All(String guildId) {
                return Constants.API_URL + "/guilds/" + guildId + "/auto-moderation/rules";
            }
        }

        public static String Get(String guildId) {
            return Constants.API_URL + "/guilds/" + guildId;
        }

        public static String All() {
            return Constants.API_URL + "/guilds";
        }

        public static String Channels(String guildId) {
            return Constants.API_URL + "/guilds/" + guildId + "/channels";
        }

        public static String Mfa(String guildId) {
            return Constants.API_URL + "/guilds/" + guildId + "/mfa";
        }

        public static String ActiveThreads(String guildId) {
            return Constants.API_URL + "/guilds/" + guildId + "/threads/active";
        }

        public static String VoiceRegions(String guildId) {
            return Constants.API_URL + "/guilds/" + guildId + "/regions";
        }

        public static String Invites(String guildId) {
            return Constants.API_URL + "/guilds/" + guildId + "/invites";
        }

        public static String Webhooks(String guildId) {
            return Constants.API_URL + "/guilds/" + guildId + "/webhooks";
        }

        public static String AuditLog(String guildId) {
            return Constants.API_URL + "/guilds/" + guildId + "/audit-logs";
        }

        public static String Preview(String guildId) {
            return Constants.API_URL + "/guilds/" + guildId + "/preview";
        }

        public static String Onboarding(String guildId) {
            return Constants.API_URL + "/guilds/" + guildId + "/onboarding";
        }

        public static String WelcomeScreen(String guildId) {
            return Constants.API_URL + "/guilds/" + guildId + "/welcome-screen";
        }
    }

    public static class Channel {
        public static class Message {
            public static class Reaction {
                public static String Get(String channelId, String messageId, String emojiIdOrName) {
                    return Constants.API_URL + "/channels/" + channelId + "/messages/" + messageId + "/reactions/" + emojiIdOrName;
                }

                public static String All(String channelId, String messageId) {
                    return Constants.API_URL + "/channels/" + channelId + "/messages/" + messageId + "/reactions";
                }

                public static String ByUser(String channelId, String messageId, String emojiIdOrName, String userId) {
                    return Constants.API_URL + "/channels/" + channelId + "/messages/" + messageId + "/reactions/" + emojiIdOrName + "/" + userId;
                }
            }

            public static String Get(String channelId, String messageId) {
                return Constants.API_URL + "/channels/" + channelId + "/messages/" + messageId;
            }

            public static String All(String channelId) {
                return Constants.API_URL + "/channels/" + channelId + "/messages";
            }

            public static String Threads(String channelId, String messageId) {
                return Constants.API_URL + "/channels/" + channelId + "/messages/" + messageId + "/threads";
            }

            public static String ToDelete(String channelId) {
                return Constants.API_URL + "/channels/" + channelId + "/messages/bulk-delete";
            }

            public static class Pin {
                public static String Get(String channelId, String messageId) {
                    return Constants.API_URL + "/channels/" + channelId + "/pins/" + messageId;
                }

                public static String All(String channelId) {
                    return Constants.API_URL + "/channels/" + channelId + "/pins";
                }
            }
        }

        public static class Invite {
            public static String All(String channelId) {
                return Constants.API_URL + "/channels/" + channelId + "/invites";
            }

            public static String Get(String code) {
                return Constants.API_URL + "/channels/" + code;
            }
        }

        public static class Thread {
            public static String All(String channelId) {
                return Constants.API_URL + "/channels/" + channelId + "/threads";
            }

            public static class Archive {
                public static String Public(String channelId) {
                    return Constants.API_URL + "/channels/" + channelId + "/threads/archived/public";
                }

                public static String Private(String channelId) {
                    return Constants.API_URL + "/channels/" + channelId + "/threads/archived/private";
                }

                public static String JoinedPrivate(String channelId, String userId) {
                    return Constants.API_URL + "/channels/" + channelId + "/users/" + userId + "/threads/archived/private";
                }
            }

            public static class Member {
                public static String Get(String channelId, String userId) {
                    return Constants.API_URL + "/channels/" + channelId + "/thread-members/" + userId;
                }

                public static String All(String channelId) {
                    return Constants.API_URL + "/channels/" + channelId + "/thread-members";
                }
            }
        }

        public static String Get(String channelId) {
            return Constants.API_URL + "/channels/" + channelId;
        }

        public static String Webhooks(String channelId) {
            return Constants.API_URL + "/channels/" + channelId + "/webhooks";
        }

        public static String Permission(String channelId, String permissionId) {
            return Constants.API_URL + "/channels/" + channelId + "/permissions/" + permissionId;
        }
    }

    public static class Icon {
        public static String GuildUserAvatar(String guildId, String userId, String hash, String format) {
            return Constants.CDN_URL + "/guilds/" + guildId + "/users/" + userId + "/avatars/" + hash + format;
        }

        public static String GuildEventImage(String eventId, String hash, String format) {
            return Constants.CDN_URL + "/guild-events/" + eventId + "/" + hash + format;
        }

        public static String GuildIcon(String guildId, String hash, String format) {
            return Constants.CDN_URL + "/icons/" + guildId + "/" + hash + format;
        }

        public static String ActivityImage(String applicationId, String imageId, String format) {
            return Constants.CDN_URL + "/app-assets/" + applicationId + "/" + imageId + "." + format;
        }

        public static String ApplicationIcon(String applicationId, String hash, String format) {
            return Constants.CDN_URL + "/app-icons/" + applicationId + "/" + hash + format;
        }

        public static String UserAvatar(String userId, String hash, String format) {
            return Constants.CDN_URL + "/avatars/" + userId + "/" + hash + format;
        }

        public static String DiscoverySplash(String guildId, String hash, String format) {
            return Constants.CDN_URL + "/discovery-splashes/" + guildId + "/" + hash + format;
        }

        public static String RoleIcon(String roleId, String hash, String format) {
            return Constants.CDN_URL + "/role-icons/" + roleId + "/" + hash + format;
        }

        public static String Splash(String guildId, String hash, String format) {
            return Constants.CDN_URL + "/splashes/" + guildId + "/" + hash + format;
        }

        public static String TeamIcon(String teamId, String hash, String format) {
            return Constants.CDN_URL + "/team-icons/" + teamId + "/" + hash + format;
        }
    }

    public static class SlashCommand {
        public static String Get(String commandId) {
            return Constants.API_URL + "/applications/" + Constants.APPLICATION_ID + "/commands/" + commandId;
        }

        public static String All() {
            return Constants.API_URL + "/applications/" + Constants.APPLICATION_ID + "/commands";
        }
    }

    public static class Webhook {
        public static String ById(String webhookId) {
            return Constants.API_URL + "/webhooks/" + webhookId;
        }

        public static String ByToken(String webhookId, String webhookToken) {
            return Constants.API_URL + "/webhooks/" + webhookId + "/" + webhookToken;
        }
    }

    public static String OriginalMessage(String interactionToken) {
        return Constants.API_URL + "/webhooks/" + Constants.APPLICATION_ID + "/" + interactionToken + "/messages/@original";
    }

    public static String Application() {
        return Constants.API_URL + "/applications/@me";
    }

    public static String Reply(String interactionId, String interactionToken) {
        return Constants.API_URL + "/interactions/" + interactionId + "/" + interactionToken + "/callback";
    }

    public static String StageInstances() {
        return Constants.API_URL + "/stage-instances";
    }

    public static String Typing(String channelId) {
        return Constants.API_URL + "/channels/" + channelId + "/typing";
    }

    public static String StageInstance(String channelId) {
        return Constants.API_URL + "/stage-instances/" + channelId;
    }

    public static String CurrentMember(String guildId) {
        return Constants.API_URL + "/guilds/" + guildId + "/members/@me";
    }

    public static String User(String userId) {
        return Constants.API_URL + "/users/" + userId;
    }

    public static String VoiceRegions() {
        return Constants.API_URL + "/voice/regions";
    }
}
