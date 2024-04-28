package org.dimas4ek.dawncord.entities.message.embed;


import java.time.ZonedDateTime;
import java.util.List;


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

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public int getColor() {
        return color;
    }

    public Footer getFooter() {
        return footer;
    }

    public EmbedImage getImage() {
        return image;
    }

    public EmbedImage getThumbnail() {
        return thumbnail;
    }

    public Author getAuthor() {
        return author;
    }

    public List<Field> getFields() {
        return fields;
    }

    public static class Footer {
        private final String text;
        private String iconUrl;

        protected Footer(String text) {
            this.text = text;
        }

        public Footer(String text, String iconUrl) {
            this.text = text;
            this.iconUrl = iconUrl;
        }

        public String getText() {
            return text;
        }

        public String getIconUrl() {
            return iconUrl;
        }
    }

    public static class Author {
        private final String name;
        private String url;
        private String iconUrl;

        protected Author(String name) {
            this.name = name;
        }

        public Author(String name, String url) {
            this.name = name;
            this.url = url;
        }

        public Author(String name, String url, String iconUrl) {
            this.name = name;
            this.url = url;
            this.iconUrl = iconUrl;
        }

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }

        public String getIconUrl() {
            return iconUrl;
        }
    }

    public static class EmbedImage {
        private final String url;
        private String proxyUrl;
        private int width;
        private int height;

        public EmbedImage(String url) {
            this.url = url;
        }

        public EmbedImage(String url, String proxyUrl, int width, int height) {
            this.url = url;
            this.proxyUrl = proxyUrl;
            this.width = width;
            this.height = height;
        }

        public String getUrl() {
            return url;
        }

        public String getProxyUrl() {
            return proxyUrl;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
    }

    public static class Field {
        private final String name;
        private final String value;
        private final boolean isInline;

        public Field(String name, String value) {
            this.name = name;
            this.value = value;
            this.isInline = false;
        }

        public Field(String name, String value, boolean isInline) {
            this.name = name;
            this.value = value;
            this.isInline = isInline;
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }

        public boolean isInline() {
            return isInline;
        }
    }
}
