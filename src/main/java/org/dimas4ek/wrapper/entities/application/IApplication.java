package org.dimas4ek.wrapper.entities.application;

import org.dimas4ek.wrapper.entities.ISnowflake;
import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.image.ApplicationIcon;

public interface IApplication extends ISnowflake {
    String getName();

    String getDescription();

    ApplicationIcon getIcon();

    User getBot();
}
