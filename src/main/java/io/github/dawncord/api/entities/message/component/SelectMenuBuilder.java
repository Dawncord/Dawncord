package io.github.dawncord.api.entities.message.component;

import io.github.dawncord.api.types.ChannelType;
import io.github.dawncord.api.types.SelectMenuType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A builder class for creating select menu components.
 */
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

    /**
     * Constructs a new SelectMenuBuilder object with the specified type and custom ID.
     *
     * @param type     The type of the select menu.
     * @param customId The custom ID of the select menu.
     */
    public SelectMenuBuilder(SelectMenuType type, String customId) {
        this.type = type;
        this.customId = customId;
    }

    @Override
    public int getType() {
        return type.getValue();
    }

    /**
     * Retrieves the custom ID of the select menu.
     *
     * @return The custom ID of the select menu.
     */
    public String getCustomId() {
        return customId;
    }

    /**
     * Retrieves the placeholder text of the select menu.
     *
     * @return The placeholder text of the select menu.
     */
    public String getPlaceholder() {
        return placeholder;
    }

    /**
     * Checks if the select menu is disabled.
     *
     * @return true if the select menu is disabled, false otherwise.
     */
    public boolean isDisabled() {
        return isDisabled;
    }

    /**
     * Retrieves the types of channels the select menu is applicable to.
     *
     * @return A list of ChannelType enum values representing the applicable channel types.
     */
    public List<ChannelType> getChannelTypes() {
        return channelTypes;
    }

    /**
     * Retrieves the default values of the select menu.
     *
     * @return A list of DefaultValue objects representing the default values.
     */
    public List<DefaultValue> getDefaultValues() {
        return defaultValues;
    }

    /**
     * Retrieves the minimum number of values that can be selected in the select menu.
     *
     * @return The minimum number of values that can be selected.
     */
    public int getMinValues() {
        return minValues;
    }

    /**
     * Retrieves the maximum number of values that can be selected in the select menu.
     *
     * @return The maximum number of values that can be selected.
     */
    public int getMaxValues() {
        return maxValues;
    }

    /**
     * Retrieves the options available in the select menu.
     *
     * @return A list of SelectOption objects representing the options.
     */
    public List<SelectOption> getOptions() {
        return options != null && !options.isEmpty()
                ? options
                : Collections.emptyList();
    }

    /**
     * Sets the placeholder text of the select menu.
     *
     * @param placeholder The placeholder text to set.
     * @return The updated SelectMenuBuilder object.
     */
    public SelectMenuBuilder addPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        return this;
    }

    /**
     * Enables the select menu.
     *
     * @return The updated SelectMenuBuilder object.
     */
    public SelectMenuBuilder enabled() {
        this.isDisabled = false;
        return this;
    }

    /**
     * Disables the select menu.
     *
     * @return The updated SelectMenuBuilder object.
     */
    public SelectMenuBuilder disabled() {
        this.isDisabled = true;
        return this;
    }

    /**
     * Sets the types of channels the select menu is applicable to.
     *
     * @param channelTypes The channel types to set.
     * @return The updated SelectMenuBuilder object.
     */
    public SelectMenuBuilder setChannelTypes(ChannelType... channelTypes) {
        this.channelTypes = List.of(channelTypes);
        return this;
    }

    /**
     * Sets the default values of the select menu.
     *
     * @param defaultValues The default values to set.
     * @return The updated SelectMenuBuilder object.
     */
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

    /**
     * Sets the minimum number of values that can be selected in the select menu.
     *
     * @param minValues The minimum number of values to set.
     * @return The updated SelectMenuBuilder object.
     */
    public SelectMenuBuilder setMinValues(int minValues) {
        this.minValues = minValues;
        return this;
    }

    /**
     * Sets the maximum number of values that can be selected in the select menu.
     *
     * @param maxValues The maximum number of values to set.
     * @return The updated SelectMenuBuilder object.
     */
    public SelectMenuBuilder setMaxValues(int maxValues) {
        this.maxValues = maxValues;
        return this;
    }

    /**
     * Adds options to the select menu.
     *
     * @param options The options to add.
     * @return The updated SelectMenuBuilder object.
     */
    public SelectMenuBuilder addOptions(List<SelectOption> options) {
        if (this.options == null) {
            this.options = new ArrayList<>();
        }
        this.options.addAll(options);
        return this;
    }

    /**
     * Represents a default value for a select menu.
     */
    public static class DefaultValue {
        private final String id;
        private final SelectMenuType type;

        /**
         * Constructs a DefaultValue object with the specified ID and type.
         *
         * @param id   The ID of the default value.
         * @param type The type of the default value.
         */
        public DefaultValue(String id, SelectMenuType type) {
            this.id = id;
            this.type = type;
        }

        /**
         * Retrieves the ID of the default value.
         *
         * @return The ID of the default value.
         */
        public String getId() {
            return id;
        }

        /**
         * Retrieves the type of the default value.
         *
         * @return The type of the default value.
         */
        public SelectMenuType getType() {
            return type;
        }
    }
}
