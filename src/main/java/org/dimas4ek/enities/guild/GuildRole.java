package org.dimas4ek.enities.guild;

import java.awt.*;
import java.util.List;

public interface GuildRole {
    String getId();
    String getName();
    String getDescription();
    Color getColor();
    List<String> getPermissions();
}
