package org.dimas4ek.enities;

import java.awt.*;
import java.util.List;

public interface GuildRole {
    String getId();
    String getName();
    String getDescription();
    Color getColor();
    List<String> getPermissions();
}
