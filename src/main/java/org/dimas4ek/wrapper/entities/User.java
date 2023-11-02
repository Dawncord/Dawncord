package org.dimas4ek.wrapper.entities;

import org.dimas4ek.wrapper.entities.image.Avatar;
import org.dimas4ek.wrapper.types.NitroType;
import org.dimas4ek.wrapper.types.UserFlag;

import java.util.List;

public interface User {
    String getId();

    long getIdLong();

    String getGlobalName();

    String getUsername();

    Avatar getAvatar();

    List<UserFlag> getFlags();

    List<UserFlag> getPublicFlags();

    boolean hasNitro();

    NitroType getNitroType();

    boolean isBot();
}
