package com.sanjeevnode.rms.orderservice.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppLogger {

    private final Logger logger;
    private final String prefix;

    // Constructor with optional prefix
    public AppLogger(Class<?> clazz) {
        this(clazz, null);
    }

    public AppLogger(Class<?> clazz, String prefix) {
        this.logger = LoggerFactory.getLogger(clazz);
        this.prefix = (prefix != null && !prefix.isBlank()) ? "[" + prefix + "] " : "";
    }

    private String prepareMessage(String message, Object... args) {
        return prefix + String.format(message, args);
    }

    public void info(String message, Object... args) {
        logger.info(prepareMessage(message, args));
    }

    public void debug(String message, Object... args) {
        logger.debug(prepareMessage(message, args));
    }

    public void warn(String message, Object... args) {
        logger.warn(prepareMessage(message, args));
    }

    public void error(String message, Throwable throwable, Object... args) {
        logger.error(prepareMessage(message, args), throwable);
    }
}
