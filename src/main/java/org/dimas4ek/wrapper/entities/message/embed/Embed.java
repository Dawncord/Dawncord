package org.dimas4ek.wrapper.entities.message.embed;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class Embed {
    private String title;
    private String description;
    private String url;
    private ZonedDateTime timestamp;
    private int color;
    private Footer footer;
    private EmbedImage image;
    private EmbedImage thumbnail;
    private Author author;
    private List<Field> fields;

    @Getter
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
    }

    @Getter
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
    }

    @Getter
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
    }

    @Getter
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
    }
}
