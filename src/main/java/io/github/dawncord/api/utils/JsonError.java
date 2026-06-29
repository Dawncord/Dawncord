package io.github.dawncord.api.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a JSON error with details such as HTTP status, Discord error code, message,
 * field-level validation details, URL, and HTTP method.
 */
public class JsonError {
    private static final Logger logger = LoggerFactory.getLogger(JsonError.class.getName());

    private final int statusCode;
    private final String code;
    private final String message;
    private final String details;
    private final String url;
    private final String method;

    /**
     * Constructs a JsonError object with the specified details.
     *
     * @param statusCode The HTTP status code of the failed response.
     * @param code       The Discord error code, or an empty string if absent.
     * @param message    The error message.
     * @param details    Flattened field-level validation details, or an empty string if absent.
     * @param url        The URL associated with the error.
     * @param method     The HTTP method used when the error occurred.
     */
    public JsonError(int statusCode, String code, String message, String details, String url, String method) {
        this.statusCode = statusCode;
        this.code = code;
        this.message = message;
        this.details = details;
        this.url = url;
        this.method = method;
    }

    /**
     * Logs the HTTP status, Discord error code, message, validation details, HTTP method,
     * and URL at the error level.
     */
    public void log() {
        StringBuilder sb = new StringBuilder("HTTP ").append(statusCode);
        if (code != null && !code.isEmpty()) {
            sb.append(' ').append(code);
        }
        if (message != null && !message.isEmpty()) {
            sb.append(' ').append(message);
        }
        if (details != null && !details.isEmpty()) {
            sb.append(" (").append(details).append(')');
        }
        sb.append(": [").append(method).append("] -> ").append(url);
        logger.error("{}", sb);
    }
}
