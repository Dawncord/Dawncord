package io.github.dawncord.api.entities.message.embed;

import java.awt.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Builder class for constructing Embed objects.
 */
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

    /**
     * Constructs an EmbedBuilder with the specified title.
     *
     * @param title The title of the embed.
     */
    public EmbedBuilder(String title) {
        this.title = title;
    }

    /**
     * Constructs an EmbedBuilder with the specified title and description.
     *
     * @param title       The title of the embed.
     * @param description The description of the embed.
     */
    public EmbedBuilder(String title, String description) {
        this.title = title;
        this.description = description;
    }

    /**
     * Sets the description of the embed.
     *
     * @param description The description of the embed.
     * @return The EmbedBuilder instance.
     */
    public EmbedBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Sets the URL of the embed.
     *
     * @param url The URL of the embed.
     * @return The EmbedBuilder instance.
     */
    public EmbedBuilder setUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * Sets the timestamp of the embed.
     *
     * @param timestamp The timestamp of the embed.
     * @return The EmbedBuilder instance.
     */
    public EmbedBuilder setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    /**
     * Sets the color of the embed.
     *
     * @param color The color of the embed.
     * @return The EmbedBuilder instance.
     */
    public EmbedBuilder setColor(Color color) {
        this.color = color == null ? 536870911 : color.getRGB();
        return this;
    }

    /**
     * Sets the footer of the embed.
     *
     * @param text The text of the footer.
     * @return The EmbedBuilder instance.
     */
    public EmbedBuilder setFooter(String text) {
        this.footer = new Embed.Footer(text);
        return this;
    }

    /**
     * Sets the footer of the embed with an icon.
     *
     * @param text    The text of the footer.
     * @param iconUrl The URL of the icon.
     * @return The EmbedBuilder instance.
     */
    public EmbedBuilder setFooter(String text, String iconUrl) {
        this.footer = new Embed.Footer(text, iconUrl);
        return this;
    }

    /**
     * Sets the image of the embed.
     *
     * @param url The URL of the image.
     * @return The EmbedBuilder instance.
     */
    public EmbedBuilder setImage(String url) {
        this.image = new Embed.EmbedImage(url, null, 0, 0);
        return this;
    }

    /**
     * Sets the thumbnail of the embed.
     *
     * @param url The URL of the thumbnail.
     * @return The EmbedBuilder instance.
     */
    public EmbedBuilder setThumbnail(String url) {
        this.thumbnail = new Embed.EmbedImage(url, null, 0, 0);
        return this;
    }

    /**
     * Sets the author of the embed.
     *
     * @param author The name of the author.
     * @return The EmbedBuilder instance.
     */
    public EmbedBuilder setAuthor(String author) {
        this.author = new Embed.Author(author);
        return this;
    }

    /**
     * Sets the author of the embed with a URL.
     *
     * @param author The name of the author.
     * @param url    The URL of the author.
     * @return The EmbedBuilder instance.
     */
    public EmbedBuilder setAuthor(String author, String url) {
        this.author = new Embed.Author(author, url);
        return this;
    }

    /**
     * Sets the author of the embed with a URL and an icon.
     *
     * @param author  The name of the author.
     * @param url     The URL of the author.
     * @param iconUrl The URL of the author's icon.
     * @return The EmbedBuilder instance.
     */
    public EmbedBuilder setAuthor(String author, String url, String iconUrl) {
        this.author = new Embed.Author(author, url, iconUrl);
        return this;
    }

    /**
     * Adds a field to the embed.
     *
     * @param field The field to add.
     * @return The EmbedBuilder instance.
     */
    public EmbedBuilder addField(Embed.Field field) {
        this.fields.add(field);
        return this;
    }

    /**
     * Adds a field to the embed with the specified name, value, and inline status.
     *
     * @param name     The name of the field.
     * @param value    The value of the field.
     * @param isInline Whether the field should be displayed inline.
     * @return The EmbedBuilder instance.
     */
    public EmbedBuilder addField(String name, String value, boolean isInline) {
        this.fields.add(new Embed.Field(name, value, isInline));
        return this;
    }

    /**
     * Adds multiple fields to the embed.
     *
     * @param fields The fields to add.
     * @return The EmbedBuilder instance.
     */
    public EmbedBuilder addFields(Embed.Field... fields) {
        this.fields.addAll(List.of(fields));
        return this;
    }

    /**
     * Builds the embed.
     *
     * @return The constructed Embed object.
     */
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
