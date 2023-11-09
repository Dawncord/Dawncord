package org.dimas4ek.wrapper.entities.guild.integration;

import org.dimas4ek.wrapper.entities.User;
import org.dimas4ek.wrapper.entities.UserImpl;
import org.dimas4ek.wrapper.entities.application.IApplication;
import org.dimas4ek.wrapper.entities.image.ApplicationIcon;
import org.json.JSONObject;

public class IntegrationApplication implements IApplication {
    private final JSONObject application;

    public IntegrationApplication(JSONObject application) {
        this.application = application;
    }

    @Override
    public String getId() {
        return application.getString("id");
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
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
        return application.optString("icon") != null ? new ApplicationIcon(getId(), application.optString("icon")) : null;
    }

    @Override
    public User getBot() {
        return application.optJSONObject("bot") != null ? new UserImpl(application.getJSONObject("bot")) : null;
    }
}
