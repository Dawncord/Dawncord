package org.dimas4ek.wrapper.entities;

import org.dimas4ek.wrapper.entities.image.Avatar;

import java.util.List;

public interface User {
    String getId();

    long getIdLong();

    String getGlobalName();

    String getUsername();

    Avatar getAvatar();

    List<String> getFlags();

    List<String> getPublicFlags();

    boolean hasNitro();

    String getNitroType();

    boolean isBot();
}
