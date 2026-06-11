package io.github.dawncord.api.entities;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.Constants;
import io.github.dawncord.api.entities.image.Avatar;
import io.github.dawncord.api.types.NitroType;
import io.github.dawncord.api.types.UserFlag;
import io.github.dawncord.api.utils.EnumUtils;

import java.util.List;

/**
 * Represents a Discord user.
 */
public class User implements ISnowflake {
    private final JsonNode user;
    private String id;
    private String globalName;
    private String username;
    private Avatar avatar;
    private List<UserFlag> flags;
    private List<UserFlag> publicFlags;
    private Boolean hasNitro;
    private NitroType nitroType;
    private Boolean isBot;

    /**
     * Constructs a new User instance with the given JSON node representing a user.
     *
     * @param user The JSON node representing a user.
     */
    public User(JsonNode user) {
        this.user = user;
    }

    @Override
    public String getId() {
        if (id == null) {
            id = user.get("id").asText();
        }
        return id;
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
    }

    public String getGlobalName() {
        if (globalName == null) {
            globalName = user.get("global_name").asText();
        }
        return globalName;
    }

    public String getUsername() {
        if (username == null) {
            username = user.get("username").asText();
        }
        return username;
    }

    public Avatar getAvatar() {
        if (avatar == null) {
            avatar = user.has("avatar") && user.hasNonNull("avatar")
                    ? new Avatar(getId(), user.get("avatar").asText())
                    : null;
        }
        return avatar;
    }

    public List<UserFlag> getFlags() {
        if (flags == null) {
            flags = EnumUtils.getEnumListFromLong(user, "flags", UserFlag.class);
        }
        return flags;
    }

    public List<UserFlag> getPublicFlags() {
        if (publicFlags == null) {
            publicFlags = EnumUtils.getEnumListFromLong(user, "public_flags", UserFlag.class);
        }
        return publicFlags;
    }

    public boolean hasNitro() {
        if (hasNitro == null) {
            hasNitro = user.has("premium_type") || user.get("premium_type") != null;
        }
        return hasNitro;
    }

    public NitroType getNitroType() {
        if (nitroType == null) {
            nitroType = EnumUtils.getEnumObject(user, "premium_type", NitroType.class);
        }
        return nitroType;
    }

    public boolean isBot() {
        if (isBot == null) {
            isBot = Constants.APPLICATION_ID.equals(user.get("id").asText());
        }
        return isBot;
    }

    public String getAsMention() {
        return "<@" + getId() + ">";
    }
}
