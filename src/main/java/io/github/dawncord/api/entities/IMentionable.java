package io.github.dawncord.api.entities;

/**
 * An interface representing objects that can be mentioned in a message.
 */
public interface IMentionable {
    /**
     * Retrieves a string that mentions the object.
     *
     * @return A string that mentions the object.
     */
    String getAsMention();
}

