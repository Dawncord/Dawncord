package org.dimas4ek.wrapper.entities;

import org.dimas4ek.wrapper.entities.image.ApplicationIcon;
import org.dimas4ek.wrapper.types.ActivityType;

public interface Application {
    String getId();

    String getName();

    String getDescription();

    ApplicationIcon getIcon();

    ActivityType getType();

    ApplicationIcon getCoverImage();
}
