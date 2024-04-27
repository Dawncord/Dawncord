package org.dimas4ek.dawncord.entities;

import org.dimas4ek.dawncord.entities.image.Avatar;
import org.dimas4ek.dawncord.types.NitroType;
import org.dimas4ek.dawncord.types.UserFlag;

import java.util.List;

public interface User extends ISnowflake, IMentionable {
    String getGlobalName();

    String getUsername();

    Avatar getAvatar();

    List<UserFlag> getFlags();

    List<UserFlag> getPublicFlags();

    boolean hasNitro();

    NitroType getNitroType();

    boolean isBot();
}
