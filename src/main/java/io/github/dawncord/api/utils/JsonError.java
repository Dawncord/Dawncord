package io.github.dawncord.api.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a JSON error with details such as message, URL, and HTTP method.
 */
public class JsonError {
    private static final Logger logger = LoggerFactory.getLogger(JsonError.class.getName());

    private final String message;
    private final String url;
    private final String method;

    /**
     * Constructs a JsonError object with the specified details.
     *
     * @param code    The error code.
     * @param message The error message.
     * @param url     The URL associated with the error.
     * @param method  The HTTP method used when the error occurred.
     */
    public JsonError(String code, String message, String url, String method) {
        this.message = message;
        this.url = url;
        this.method = method;
    }

    /**
     * Logs the JSON error message, URL, and HTTP method at the error level.
     */

    public void log() {
        logger.error(message + ": [" + method + "] -> " + url);
    }
}
