package org.dimas4ek.dawncord.entities.application;

import org.dimas4ek.dawncord.entities.ISnowflake;
import org.dimas4ek.dawncord.entities.User;
import org.dimas4ek.dawncord.entities.image.ApplicationIcon;

public interface IApplication extends ISnowflake {
    String getName();

    String getDescription();

    ApplicationIcon getIcon();

    User getBot();
}
