package io.github.dawncord.api.entities.message.embed;


import java.time.ZonedDateTime;
import java.util.List;

/**
 * Represents an embed in a message.
 */
public record Embed(String title, String description, String url, ZonedDateTime timestamp, int color, Footer footer,
                    Image image, Image thumbnail, Author author, List<Field> fields) {
    /**
     * Represents the footer of an embed.
     */
    public record Footer(String text, String iconUrl) {

        public Footer(String text) {
            this(text, null);
        }
    }

    /**
     * Represents the author of an embed.
     */
    public record Author(String name, String url, String iconUrl) {

        public Author(String name) {
            this(name, null, null);
        }

        public Author(String name, String url) {
            this(name, url, null);
        }
    }

    /**
     * Represents an image in an embed.
     */
    public record Image(String url, String proxyUrl, int width, int height) {

        public Image(String url) {
            this(url, null, 0, 0);
        }
    }

    /**
     * Represents a field in an embed.
     */
    public record Field(String name, String value, boolean isInline) {

        public Field(String name, String value) {
            this(name, value, false);
        }
    }
}
