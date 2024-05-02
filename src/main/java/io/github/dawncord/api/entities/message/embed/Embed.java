package io.github.dawncord.api.entities.message.embed;


import java.time.ZonedDateTime;
import java.util.List;

/**
 * Represents an embed in a message.
 */
public class Embed {
    private final String title;
    private final String description;
    private final String url;
    private final ZonedDateTime timestamp;
    private final int color;
    private final Footer footer;
    private final EmbedImage image;
    private final EmbedImage thumbnail;
    private final Author author;
    private final List<Field> fields;

    /**
     * Constructs an Embed object with the specified parameters.
     *
     * @param title       The title of the embed.
     * @param description The description of the embed.
     * @param url         The URL of the embed.
     * @param timestamp   The timestamp of the embed.
     * @param color       The color of the embed.
     * @param footer      The footer of the embed.
     * @param image       The image of the embed.
     * @param thumbnail   The thumbnail of the embed.
     * @param author      The author of the embed.
     * @param fields      The fields of the embed.
     */
    public Embed(String title, String description, String url, ZonedDateTime timestamp, int color, Footer footer, EmbedImage image, EmbedImage thumbnail, Author author, List<Field> fields) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.timestamp = timestamp;
        this.color = color;
        this.footer = footer;
        this.image = image;
        this.thumbnail = thumbnail;
        this.author = author;
        this.fields = fields;
    }

    /**
     * Retrieves the title of the embed.
     *
     * @return The title of the embed.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Retrieves the description of the embed.
     *
     * @return The description of the embed.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Retrieves the URL of the embed.
     *
     * @return The URL of the embed.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Retrieves the timestamp of the embed.
     *
     * @return The timestamp of the embed.
     */
    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Retrieves the color of the embed.
     *
     * @return The color of the embed.
     */
    public int getColor() {
        return color;
    }

    /**
     * Retrieves the footer of the embed.
     *
     * @return The footer of the embed.
     */
    public Footer getFooter() {
        return footer;
    }

    /**
     * Retrieves the image of the embed.
     *
     * @return The image of the embed.
     */
    public EmbedImage getImage() {
        return image;
    }

    /**
     * Retrieves the thumbnail of the embed.
     *
     * @return The thumbnail of the embed.
     */
    public EmbedImage getThumbnail() {
        return thumbnail;
    }

    /**
     * Retrieves the author of the embed.
     *
     * @return The author of the embed.
     */
    public Author getAuthor() {
        return author;
    }

    /**
     * Retrieves the fields of the embed.
     *
     * @return The fields of the embed.
     */
    public List<Field> getFields() {
        return fields;
    }

    /**
     * Represents the footer of an embed.
     */
    public static class Footer {
        private final String text;
        private String iconUrl;

        protected Footer(String text) {
            this.text = text;
        }

        /**
         * Constructs a footer with the specified text and icon URL.
         *
         * @param text    The text of the footer.
         * @param iconUrl The URL of the footer icon.
         */
        public Footer(String text, String iconUrl) {
            this.text = text;
            this.iconUrl = iconUrl;
        }

        /**
         * Retrieves the text of the footer.
         *
         * @return The text of the footer.
         */
        public String getText() {
            return text;
        }

        /**
         * Retrieves the URL of the footer icon.
         *
         * @return The URL of the footer icon.
         */
        public String getIconUrl() {
            return iconUrl;
        }
    }

    /**
     * Represents the author of an embed.
     */
    public static class Author {
        private final String name;
        private String url;
        private String iconUrl;

        protected Author(String name) {
            this.name = name;
        }

        /**
         * Constructs an author with the specified name and URL.
         *
         * @param name The name of the author.
         * @param url  The URL of the author.
         */
        public Author(String name, String url) {
            this.name = name;
            this.url = url;
        }

        /**
         * Constructs an author with the specified name, URL, and icon URL.
         *
         * @param name    The name of the author.
         * @param url     The URL of the author.
         * @param iconUrl The URL of the author's icon.
         */
        public Author(String name, String url, String iconUrl) {
            this.name = name;
            this.url = url;
            this.iconUrl = iconUrl;
        }

        /**
         * Retrieves the name of the author.
         *
         * @return The name of the author.
         */
        public String getName() {
            return name;
        }

        /**
         * Retrieves the URL of the author.
         *
         * @return The URL of the author.
         */
        public String getUrl() {
            return url;
        }

        /**
         * Retrieves the URL of the author's icon.
         *
         * @return The URL of the author's icon.
         */
        public String getIconUrl() {
            return iconUrl;
        }
    }

    /**
     * Represents an image in an embed.
     */
    public static class EmbedImage {
        private final String url;
        private String proxyUrl;
        private int width;
        private int height;

        /**
         * Constructs an embed image with the specified URL.
         *
         * @param url The URL of the image.
         */
        public EmbedImage(String url) {
            this.url = url;
        }

        /**
         * Constructs an embed image with the specified URL, proxy URL, width, and height.
         *
         * @param url      The URL of the image.
         * @param proxyUrl The proxy URL of the image.
         * @param width    The width of the image.
         * @param height   The height of the image.
         */
        public EmbedImage(String url, String proxyUrl, int width, int height) {
            this.url = url;
            this.proxyUrl = proxyUrl;
            this.width = width;
            this.height = height;
        }

        /**
         * Retrieves the URL of the image.
         *
         * @return The URL of the image.
         */
        public String getUrl() {
            return url;
        }

        /**
         * Retrieves the proxy URL of the image.
         *
         * @return The proxy URL of the image.
         */
        public String getProxyUrl() {
            return proxyUrl;
        }

        /**
         * Retrieves the width of the image.
         *
         * @return The width of the image.
         */
        public int getWidth() {
            return width;
        }

        /**
         * Retrieves the height of the image.
         *
         * @return The height of the image.
         */
        public int getHeight() {
            return height;
        }
    }

    /**
     * Represents a field in an embed.
     */
    public static class Field {
        private final String name;
        private final String value;
        private final boolean isInline;

        /**
         * Constructs a field with the specified name and value.
         *
         * @param name  The name of the field.
         * @param value The value of the field.
         */
        public Field(String name, String value) {
            this.name = name;
            this.value = value;
            this.isInline = false;
        }

        /**
         * Constructs a field with the specified name, value, and inline status.
         *
         * @param name     The name of the field.
         * @param value    The value of the field.
         * @param isInline Whether the field should be displayed inline.
         */
        public Field(String name, String value, boolean isInline) {
            this.name = name;
            this.value = value;
            this.isInline = isInline;
        }

        /**
         * Retrieves the name of the field.
         *
         * @return The name of the field.
         */
        public String getName() {
            return name;
        }

        /**
         * Retrieves the value of the field.
         *
         * @return The value of the field.
         */
        public String getValue() {
            return value;
        }

        /**
         * Checks if the field should be displayed inline.
         *
         * @return true if the field should be displayed inline, otherwise false.
         */
        public boolean isInline() {
            return isInline;
        }
    }
}
