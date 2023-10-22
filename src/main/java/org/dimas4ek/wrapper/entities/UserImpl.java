package org.dimas4ek.wrapper.entities;

import org.dimas4ek.wrapper.Constants;
import org.dimas4ek.wrapper.types.NitroType;
import org.dimas4ek.wrapper.types.UserFlag;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserImpl implements User {
    private final JSONObject user;

    public UserImpl(JSONObject user) {
        this.user = user;
    }

    @Override
    public String getId() {
        return user.getString("id");
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
    }

    @Override
    public String getGlobalName() {
        return user.getString("global_name");
    }

    @Override
    public String getUsername() {
        return user.getString("username");
    }

    @Override
    public Avatar getAvatar() {
        return !user.isNull("avatar")
                ? new Avatar(getId(), user.getString("avatar"))
                : null;
    }

    @Override
    public List<String> getFlags() {
        return getUserFlags("flags");
    }

    @Override
    public List<String> getPublicFlags() {
        return getUserFlags("public_flags");
    }

    @Override
    public boolean hasNitro() {
        return user.has("premium_type") || user.get("premium_type") != null;
    }

    @Override
    public String getNitroType() {
        for (NitroType type : NitroType.values()) {
            if (user.getInt("premium_type") == type.getValue()) {
                return type.getName();
            }
        }
        return null;
    }

    @NotNull
    private List<String> getUserFlags(String flagsJson) {
        if (user.has(flagsJson) || user.getInt(flagsJson) != 0) {
            List<String> flags = new ArrayList<>();
            long flagsFromJson = Long.parseLong(String.valueOf(user.getInt(flagsJson)));
            for (UserFlag flag : UserFlag.values()) {
                if ((flagsFromJson & flag.getValue()) != 0) {
                    flags.add(flag.name());
                }
            }
            return flags;
        }
        return Collections.emptyList();
    }

    @Override
    public boolean isBot() {
        return Constants.APPLICATION_ID.equals(user.getString("id"));
    }
}
