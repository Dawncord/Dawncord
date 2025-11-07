package io.github.dawncord.api.entities;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.dawncord.api.Routes;
import io.github.dawncord.api.entities.application.Application;
import io.github.dawncord.api.types.SkuFlags;
import io.github.dawncord.api.types.SkuType;
import io.github.dawncord.api.utils.JsonUtils;
import io.github.dawncord.api.utils.LazyLoader;

import java.util.List;

public class Sku implements ISnowflake {
    private final LazyLoader loader;
    private final JsonNode sku;
    private String id;
    private SkuType type;
    private Application application;
    private String name;
    private String slug;
    private List<SkuFlags> flags;

    public Sku(JsonNode sku) {
        this.sku = sku;
        loader = new LazyLoader(sku);
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

    public SkuType getType() {
        type = loader.loadEnumObject(type, "type", SkuType.class);
        return type;
    }

    public Application getApplication() {
        application = loader.loadIfExists(application, "application",
                () -> new Application(JsonUtils.fetch(Routes.Application(sku.get("application_id").asText()))));
        return application;
    }

    public String getName() {
        name = loader.loadString(name, "name");
        return name;
    }

    public String getSlug() {
        slug = loader.loadString(slug, "slug");
        return slug;
    }

    public List<SkuFlags> getFlags() {
        flags = loader.loadEnumListFromLong(flags, "flags", SkuFlags.class);
        return flags;
    }
}
