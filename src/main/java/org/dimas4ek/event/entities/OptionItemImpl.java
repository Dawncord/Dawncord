package org.dimas4ek.event.entities;

import org.dimas4ek.api.ApiClient;
import org.dimas4ek.enities.guild.*;
import org.dimas4ek.enities.types.OptionType;
import org.dimas4ek.enities.user.User;
import org.dimas4ek.event.option.OptionDataResolver;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class OptionItemImpl implements OptionItem {
    private final Guild guild;
    private final OptionType type;
    private final String name;
    private final String description;
    private final boolean required;
    private final List<OptionChoice> choices;
    private final List<OptionDataResolver> resolvers;
    
    public OptionItemImpl(Guild guild, OptionType type, String name, String description, boolean required, List<OptionChoice> choices, List<OptionDataResolver> resolvers) {
        this.guild = guild;
        this.type = type;
        this.name = name;
        this.description = description;
        this.required = required;
        this.choices = choices;
        this.resolvers = resolvers;
    }
    
    @Override
    public Guild getGuild() {
        return guild;
    }
    
    @Override
    public OptionType getType() {
        return type;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public String getDescription() {
        return description;
    }
    
    @Override
    public boolean required() {
        return required;
    }
    
    @Override
    public List<OptionChoice> getChoices() {
        return choices;
    }
    
    private Optional<OptionDataResolver> getResolver() {
        return resolvers.stream().filter(r -> r.getName().equals(name)).findFirst();
    }
    
    @Override
    public String asText() {
        return getResolver()
            .map(OptionDataResolver::getValue)
            .orElse(null);
    }
    
    @Override
    public int asInt() {
        return Math.toIntExact(asLong());
    }
    
    @Override
    public double asDouble() {
        return switch (type) {
            case STRING, INTEGER, NUMBER -> getResolver()
                .map(resolver -> Double.parseDouble(resolver.getValue()))
                .orElseThrow(() -> new IllegalArgumentException("Cannot cast " + type + " to double"));
            default -> throw new IllegalArgumentException("Cannot cast " + type + " to double");
        };
    }
    
    @Override
    public long asLong() {
        return switch (type) {
            case STRING, MENTIONABLE, CHANNEL, ROLE, USER, INTEGER, ATTACHMENT -> getResolver()
                .map(resolver -> Long.parseLong(resolver.getValue()))
                .orElseThrow(() -> new IllegalArgumentException("Cannot cast " + type + " to long"));
            default -> throw new IllegalArgumentException("Cannot cast " + type + " to long");
        };
    }
    
    @Override
    public boolean asBoolean() {
        if (type == OptionType.BOOLEAN) {
            return getResolver()
                .map(resolver -> Boolean.parseBoolean(resolver.getValue()))
                .orElseThrow(() -> new IllegalArgumentException("Cannot cast " + type + " to boolean"));
        }
        throw new IllegalArgumentException("Cannot cast " + type + " to boolean");
    }
    
    @Override
    public User asUser() {
        if (type == OptionType.USER || type == OptionType.MENTIONABLE) {
            return getResolver().map(resolver -> new UserImpl(ApiClient.getApiResponseObject("/users/" + resolver.getValue())))
                .orElseThrow(() -> new IllegalArgumentException("Cannot cast " + type + " to user"));
        }
        throw new IllegalArgumentException("Cannot cast " + type + " to user");
    }
    
    @Override
    public GuildChannel asChannel() {
        return castTo(GuildChannelImpl::new, "/guilds/" + guild.getId() + "/channels", "Cannot cast " + type + " to channel");
    }
    
    @Override
    public GuildRole asRole() {
        return castTo(GuildRoleImpl::new, "/guilds/" + guild.getId() + "/roles", "Cannot cast " + type + " to role");
    }
    
    private <T> T castTo(Function<JSONObject, T> constructor, String url, String errorMessage) {
        return findMatch(url)
            .map(constructor)
            .orElseThrow(() -> new IllegalArgumentException(errorMessage));
    }
    
    private Optional<JSONObject> findMatch(String url) {
        for (OptionDataResolver resolver : resolvers) {
            if (name.equals(resolver.getName())) {
                JSONArray jsonArray = ApiClient.getApiResponseArray(url);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (jsonObject.getString("id").equals(resolver.getValue())) {
                        return Optional.of(jsonObject);
                    }
                }
            }
        }
        return Optional.empty();
    }
}
