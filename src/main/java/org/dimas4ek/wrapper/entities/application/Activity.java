package org.dimas4ek.wrapper.entities.application;

import org.dimas4ek.wrapper.types.ActivityType;

public interface Activity {
    ActivityType getType();

    String getPartyId();
}
