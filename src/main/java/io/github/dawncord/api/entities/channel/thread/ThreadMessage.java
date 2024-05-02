package io.github.dawncord.api.entities.channel.thread;

import io.github.dawncord.api.entities.message.component.ComponentBuilder;
import io.github.dawncord.api.entities.message.embed.Embed;
import io.github.dawncord.api.entities.message.sticker.Sticker;
import io.github.dawncord.api.types.AllowedMention;

import java.util.List;
import java.util.Map;

/**
 * Represents a message within a thread.
 */
public class ThreadMessage {
    private final String content;
    private List<Embed> embeds;
    private List<AllowedMention> allowedMentions;
    private List<ComponentBuilder> components;
    private List<Sticker> stickers;
    private Map<String, String> attachments;
    private boolean suppressEmbeds;

    /**
     * Constructs a new ThreadMessage instance with the provided content.
     *
     * @param content The content of the message.
     */
    public ThreadMessage(String content) {
        this.content = content;
        embeds = null;
        allowedMentions = null;
        components = null;
        stickers = null;
        attachments = null;
        suppressEmbeds = false;
    }

    /**
     * Retrieves the content of the message.
     *
     * @return The content.
     */
    public String getContent() {
        return content;
    }

    /**
     * Retrieves the embeds attached to the message.
     *
     * @return The list of embeds.
     */
    public List<Embed> getEmbeds() {
        return embeds;
    }

    /**
     * Retrieves the components attached to the message.
     *
     * @return The list of components.
     */
    public List<ComponentBuilder> getComponents() {
        return components;
    }

    /**
     * Retrieves the stickers attached to the message.
     *
     * @return The list of stickers.
     */
    public List<Sticker> getStickers() {
        return stickers;
    }

    /**
     * Retrieves the attachments attached to the message.
     *
     * @return The map of attachments (filename to URL).
     */
    public Map<String, String> getAttachments() {
        return attachments;
    }

    /**
     * Checks if embeds are suppressed in the message.
     *
     * @return True if embeds are suppressed, false otherwise.
     */
    public boolean isSuppressEmbeds() {
        return suppressEmbeds;
    }

    /**
     * Sets the embeds attached to the message.
     *
     * @param embeds The list of embeds.
     * @return The updated ThreadMessage instance.
     */
    public ThreadMessage setEmbeds(List<Embed> embeds) {
        this.embeds = embeds;
        return this;
    }

    /**
     * Sets the stickers attached to the message.
     *
     * @param stickers The list of stickers.
     * @return The updated ThreadMessage instance.
     */
    public ThreadMessage setStickers(List<Sticker> stickers) {
        this.stickers = stickers;
        return this;
    }

    /**
     * Sets the attachments attached to the message.
     *
     * @param attachments The map of attachments (filename to URL).
     * @return The updated ThreadMessage instance.
     */
    public ThreadMessage setAttachments(Map<String, String> attachments) {
        this.attachments = attachments;
        return this;
    }

    /**
     * Sets whether embeds are suppressed in the message.
     *
     * @param suppressEmbeds True to suppress embeds, false otherwise.
     * @return The updated ThreadMessage instance.
     */
    public ThreadMessage setSuppressEmbeds(boolean suppressEmbeds) {
        this.suppressEmbeds = suppressEmbeds;
        return this;
    }

    /**
     * Sets the allowed mentions in the message.
     *
     * @param allowedMentions The allowed mentions.
     * @return The updated ThreadMessage instance.
     */
    public ThreadMessage setAllowedMentions(AllowedMention... allowedMentions) {
        this.allowedMentions = List.of(allowedMentions);
        return this;
    }

    /**
     * Sets the components attached to the message.
     *
     * @param components The list of components.
     * @return The updated ThreadMessage instance.
     */
    public ThreadMessage setComponents(ComponentBuilder... components) {
        this.components = List.of(components);
        return this;
    }

    /**
     * Retrieves the allowed mentions in the message.
     *
     * @return The allowed mentions, or null if not set.
     */
    public AllowedMention[] getAllowedMentions() {
        return allowedMentions != null
                ? allowedMentions.toArray(new AllowedMention[0])
                : null;
    }
}
