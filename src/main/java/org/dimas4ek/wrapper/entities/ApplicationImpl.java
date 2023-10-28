package org.dimas4ek.wrapper.entities;

import org.dimas4ek.wrapper.entities.image.ApplicationIcon;
import org.dimas4ek.wrapper.types.ActivityType;
import org.json.JSONObject;

public class ApplicationImpl implements Application {
    private final JSONObject application;

    public ApplicationImpl(JSONObject application) {
        this.application = application;
    }

    @Override
    public String getId() {
        return application.getString("id");
    }

    @Override
    public String getName() {
        return application.getString("name");
    }

    @Override
    public String getDescription() {
        return application.getString("description");
    }

    @Override
    public ApplicationIcon getIcon() {
        return new ApplicationIcon(getId(), application.getString("icon"));
    }

    @Override
    public ActivityType getType() {
        for (ActivityType type : ActivityType.values()) {
            if (type.getValue() == application.getInt("type")) {
                return type;
            }
        }
        return null;
    }

    @Override
    public ApplicationIcon getCoverImage() {
        return new ApplicationIcon(getId(), application.getString("cover_image"));
    }
}
