package org.dimas4ek.enities.types;

public enum GuildChannelType {
    GUILD_TEXT(0),
    DM(1),
    GUILD_VOICE(2),
    GROUP_DM(3),
    GUILD_CATEGORY(4),
    GUILD_ANNOUNCEMENT(5),
    ANNOUNCEMENT_THREAD(10),
    PUBLIC_THREAD(11),
    PRIVATE_THREAD(12),
    GUILD_STAGE_VOICE(13),
    GUILD_DIRECTORY(14),
    GUILD_FORUM(15);
    
    private final int value;
    GuildChannelType(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        String name = this.name();
        if (name.startsWith("GUILD_")) {
            return name.substring("GUILD_".length());
        }
        return name;
    }
}
