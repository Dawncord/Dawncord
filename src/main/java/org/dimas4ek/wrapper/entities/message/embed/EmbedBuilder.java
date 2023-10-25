package org.dimas4ek.wrapper.entities.message.embed;

import java.awt.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class EmbedBuilder {
    private final String title;
    private String description;
    private String url;
    private ZonedDateTime timestamp;
    private int color = 536870911;
    private Embed.Footer footer;
    private Embed.EmbedImage image;
    private Embed.EmbedImage thumbnail;
    private Embed.Author author;
    private final List<Embed.Field> fields = new ArrayList<>();

    public EmbedBuilder(String title) {
        this.title = title;
    }

    public EmbedBuilder(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public EmbedBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public EmbedBuilder setUrl(String url) {
        this.url = url;
        return this;
    }

    public EmbedBuilder setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public EmbedBuilder setColor(Color color) {
        this.color = color == null ? 536870911 : color.getRGB();
        return this;
    }

    public EmbedBuilder setFooter(String text) {
        this.footer = new Embed.Footer(text);
        return this;
    }

    public EmbedBuilder setFooter(String text, String iconUrl) {
        this.footer = new Embed.Footer(text, iconUrl);
        return this;
    }

    public EmbedBuilder setImage(String url) {
        this.image = new Embed.EmbedImage(url, null, 0, 0);
        return this;
    }

    public EmbedBuilder setThumbnail(String url) {
        this.thumbnail = new Embed.EmbedImage(url, null, 0, 0);
        return this;
    }

    public EmbedBuilder setAuthor(String author) {
        this.author = new Embed.Author(author);
        return this;
    }

    public EmbedBuilder setAuthor(String author, String url) {
        this.author = new Embed.Author(author, url);
        return this;
    }

    public EmbedBuilder setAuthor(String author, String url, String iconUrl) {
        this.author = new Embed.Author(author, url, iconUrl);
        return this;
    }

    public EmbedBuilder addField(Embed.Field field) {
        this.fields.add(field);
        return this;
    }

    public EmbedBuilder addField(String name, String value, boolean isInline) {
        this.fields.add(new Embed.Field(name, value, isInline));
        return this;
    }

    public EmbedBuilder addFields(Embed.Field field, Embed.Field... fields) {
        this.fields.add(field);
        this.fields.addAll(List.of(fields));
        return this;
    }

    public Embed build() {
        return new Embed(
                title,
                description,
                url,
                timestamp,
                color,
                footer,
                image,
                thumbnail,
                author,
                fields
        );
    }
}
