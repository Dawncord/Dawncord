package org.dimas4ek.dawncord.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JsonError {
    private static final Logger logger = LoggerFactory.getLogger(JsonError.class.getName());

    private final String code;
    private final String message;
    private final String url;
    private final String method;

    public JsonError(String code, String message, String url, String method) {
        this.code = code;
        this.message = message;
        this.url = url;
        this.method = method;
    }

    public void log() {
        logger.error(message + ": [" + method + "] -> " + url);
    }
}
