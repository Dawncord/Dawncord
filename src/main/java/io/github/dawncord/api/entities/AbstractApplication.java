package io.github.dawncord.api.entities;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.image.ApplicationIcon;
import io.github.dawncord.api.utils.JsonUtils;
import io.github.dawncord.api.utils.LazyLoader;

public class AbstractApplication implements IApplication {
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

    @Override
    public String getName() {
        name = loader.loadString(name, "name");
        return name;
    }

    @Override
    public String getDescription() {
        description = loader.loadString(description, "description");
        return description;
    }

    public ApplicationIcon getIcon() {
        icon = loader.loadIfExists(icon, "icon",
                () -> new ApplicationIcon(getId(), application.get("icon").asText()));
        return icon;
    }

    @Override
    public User getBot() {
        bot = loader.loadIfExists(bot, "bot",
                () -> new UserImpl(JsonUtils.fetch(Routes.User(getBotId()))));
        return bot;
    }

    private String getBotId() {
        return application.get("bot").get("id").asText();
    }
}
