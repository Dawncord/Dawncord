package io.github.dawncord.api.entities.application;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.ISnowflake;
import io.github.dawncord.api.entities.User;
import io.github.dawncord.api.entities.image.ApplicationIcon;
import io.github.dawncord.api.utils.JsonUtils;
import io.github.dawncord.api.utils.LazyLoader;

public abstract class AbstractApplication implements ISnowflake {
    private final LazyLoader loader;
    private final JsonNode application;
    private String id;
    private String name;
    private ApplicationIcon icon;
    private String description;
    private User bot;

    public AbstractApplication(JsonNode application) {
        this.application = application;
        loader = new LazyLoader(application);
    }

    @Override
    public String getId() {
        id = loader.loadString(id, "id");
        return id;
    }

    @Override
    public long getIdLong() {
        return Long.parseLong(getId());
    }

    public String getName() {
        name = loader.loadString(name, "name");
        return name;
    }

    public String getDescription() {
        description = loader.loadString(description, "description");
        return description;
    }

    public ApplicationIcon getIcon() {
        icon = loader.loadIfExists(icon, "icon",
                () -> new ApplicationIcon(getId(), application.get("icon").asText()));
        return icon;
    }

    public User getBot() {
        bot = loader.loadIfExists(bot, "bot",
                () -> new User(JsonUtils.fetch(Routes.User(getBotId()))));
        return bot;
    }

    private String getBotId() {
        return application.get("bot").get("id").asText();
    }
}
