package org.dimas4ek.wrapper.entities.role;

import java.awt.*;
import java.util.List;

public interface GuildRole {
    String getId();
    String getName();
    String getDescription();
    List<String> getPermissions();
    int getPosition();
    Color getColor();
    boolean isPinned();
    boolean isManaged();
    boolean isMentionable();
    List<Tag> getTags();
    List<String> getFlags();
}
