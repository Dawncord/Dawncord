package org.dimas4ek.dawncord.entities.message.component;

import org.dimas4ek.dawncord.types.ChannelType;
import org.dimas4ek.dawncord.types.SelectMenuType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SelectMenuBuilder implements ComponentBuilder {
    private final SelectMenuType type;
    private final String customId;
    private String placeholder;
    private boolean isDisabled;
    private List<ChannelType> channelTypes;
    private List<DefaultValue> defaultValues;
    private int minValues = 1;
    private int maxValues = 1;
    private List<SelectOption> options;

    public SelectMenuBuilder(SelectMenuType type, String customId) {
        this.type = type;
        this.customId = customId;
    }

    @Override
    public int getType() {
        return type.getValue();
    }

    public String getCustomId() {
        return customId;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public boolean isDisabled() {
        return isDisabled;
    }

    public List<ChannelType> getChannelTypes() {
        return channelTypes;
    }

    public List<DefaultValue> getDefaultValues() {
        return defaultValues;
    }

    public int getMinValues() {
        return minValues;
    }

    public int getMaxValues() {
        return maxValues;
    }

    public List<SelectOption> getOptions() {
        return options != null && !options.isEmpty()
                ? options
                : Collections.emptyList();
    }

    public SelectMenuBuilder addPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        return this;
    }

    public SelectMenuBuilder enabled() {
        this.isDisabled = false;
        return this;
    }

    public SelectMenuBuilder disabled() {
        this.isDisabled = true;
        return this;
    }

    public SelectMenuBuilder setChannelTypes(ChannelType... channelTypes) {
        this.channelTypes = List.of(channelTypes);
        return this;
    }

    public SelectMenuBuilder setDefaultValues(List<DefaultValue> defaultValues) {
        if (this.defaultValues == null) {
            this.defaultValues = new ArrayList<>();
        }
        defaultValues.forEach(defaultValue -> {
            if (defaultValue.getType() != SelectMenuType.TEXT) {
                this.defaultValues.add(defaultValue);
            }
        });
        return this;
    }

    public SelectMenuBuilder setMinValues(int minValues) {
        this.minValues = minValues;
        return this;
    }

    public SelectMenuBuilder setMaxValues(int maxValues) {
        this.maxValues = maxValues;
        return this;
    }

    public SelectMenuBuilder addOptions(List<SelectOption> options) {
        if (this.options == null) {
            this.options = new ArrayList<>();
        }
        this.options.addAll(options);
        return this;
    }

    public static class DefaultValue {
        private final String id;
        private final SelectMenuType type;

        public DefaultValue(String id, SelectMenuType type) {
            this.id = id;
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public SelectMenuType getType() {
            return type;
        }
    }
}
