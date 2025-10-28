package io.github.dawncord.api.command.builder;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.dawncord.api.command.Command;
import io.github.dawncord.api.command.SubCommand;
import io.github.dawncord.api.command.option.Option;
import io.github.dawncord.api.types.Locale;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A builder class for creating {@link Command} instances.
 *
 * <p>This class provides a fluent API for setting the properties of a {@link Command} instance.
 * The {@link #build()} method is used to create an immutable {@link Command} instance.
 */
public abstract class CommandBuilder<T, V> {
    protected static final ObjectMapper mapper = new ObjectMapper();
    protected String name;
    protected String description;
    protected Map<Locale, String> nameLocalizations;
    protected Map<Locale, String> descriptionLocalizations;
    protected final List<Option> optionList = new ArrayList<>();
    protected final List<SubCommand> subCommandList = new ArrayList<>();

    public CommandBuilder(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public List<Option> getOptionList() {
        return optionList;
    }

    public List<SubCommand> getSubCommandList() {
        return subCommandList;
    }

    /**
     * Sets the name of the command.
     *
     * @param name the name of the command
     * @return this {@link CommandBuilder} instance
     */
    public T setName(String name) {
        this.name = name;
        return (T) this;
    }

    /**
     * Sets the description of the command.
     *
     * @param description the description of the command
     * @return this {@link CommandBuilder} instance
     */
    public T setDescription(String description) {
        this.description = description;
        return (T) this;
    }

    /**
     * Sets the localized name of the command for a specific locale.
     *
     * @param locale the locale for which to set the localized name
     * @param name   the localized name of the command
     * @return this {@link CommandBuilder} instance
     */
    public T setLocalizedName(Locale locale, String name) {
        nameLocalizations.put(locale, name);
        return (T) this;
    }

    /**
     * Sets the localized names of the command for multiple locales.
     *
     * @param localizedNames a map of locales to their corresponding localized names
     * @return this {@link CommandBuilder} instance
     */
    public T setLocalizedNames(Map<Locale, String> localizedNames) {
        nameLocalizations.putAll(localizedNames);
        return (T) this;
    }

    /**
     * Sets the localized description of the command for a specific locale.
     *
     * @param locale      the locale for which to set the localized description
     * @param description the localized description of the command
     * @return this {@link CommandBuilder} instance
     */
    public T setLocalizedDescription(Locale locale, String description) {
        descriptionLocalizations.put(locale, description);
        return (T) this;
    }

    /**
     * Sets the localized descriptions of the command for multiple locales.
     *
     * @param localizedDescriptions a map of locales to their corresponding localized descriptions
     * @return this {@link CommandBuilder} instance
     */
    public T setLocalizedDescriptions(Map<Locale, String> localizedDescriptions) {
        descriptionLocalizations.putAll(localizedDescriptions);
        return (T) this;
    }

    /**
     * Builds an immutable {@link Command} instance using the properties set on this {@link CommandBuilder} instance.
     *
     * @return an immutable {@link Command} instance
     */
    public abstract V build();
}
