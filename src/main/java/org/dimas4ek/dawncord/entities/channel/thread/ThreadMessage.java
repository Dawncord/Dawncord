package org.dimas4ek.dawncord.entities.channel.thread;

import lombok.Getter;
import org.dimas4ek.dawncord.entities.message.component.ComponentBuilder;
import org.dimas4ek.dawncord.entities.message.embed.Embed;
import org.dimas4ek.dawncord.entities.message.sticker.Sticker;
import org.dimas4ek.dawncord.types.AllowedMention;

import java.util.List;
import java.util.Map;

@Getter
public class ThreadMessage {
    private final String content;
    private List<Embed> embeds;
    private List<AllowedMention> allowedMentions;
    private List<ComponentBuilder> components;
    private List<Sticker> stickers;
    private Map<String, String> attachments;
    private boolean suppressEmbeds;

    public ThreadMessage(String content) {
        this.content = content;
        embeds = null;
        allowedMentions = null;
        components = null;
        stickers = null;
        attachments = null;
        suppressEmbeds = false;
    }

    public ThreadMessage setEmbeds(List<Embed> embeds) {
        this.embeds = embeds;
        return this;
    }

    public ThreadMessage setStickers(List<Sticker> stickers) {
        this.stickers = stickers;
        return this;
    }

    public ThreadMessage setAttachments(Map<String, String> attachments) {
        this.attachments = attachments;
        return this;
    }

    public ThreadMessage setSuppressEmbeds(boolean suppressEmbeds) {
        this.suppressEmbeds = suppressEmbeds;
        return this;
    }

    public ThreadMessage setAllowedMentions(AllowedMention... allowedMentions) {
        this.allowedMentions = List.of(allowedMentions);
        return this;
    }

    public ThreadMessage setComponents(ComponentBuilder... components) {
        this.components = List.of(components);
        return this;
    }

    public AllowedMention[] getAllowedMentions() {
        return allowedMentions != null
                ? allowedMentions.toArray(new AllowedMention[0])
                : null;
    }
}
