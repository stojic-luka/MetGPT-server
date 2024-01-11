package app.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MetLogger {
    
    private static MetLogger instance = null;
    private final Logger logger = LoggerFactory.getLogger(MetLogger.class);
    
    public static MetLogger getInstance() {
        if (instance == null) {
            instance = new MetLogger();
        }
        return instance;
    }
    
    public void log(String message, MetLoggerLevel level) {
        switch (level) {
            case TRACE ->
                trace(message);
            case DEBUG ->
                debug(message);
            case INFO ->
                info(message);
            case WARN ->
                warn(message);
            case ERROR ->
                error(message);
        }
    }

    public void trace(String message) {
        logger.trace(message);
    }

    public void debug(String message) {
        logger.debug(message);
    }

    public void info(String message) {
        logger.info(message);
    }

    public void warn(String message) {
        logger.warn(message);
    }

    public void error(String message) {
        logger.error(message);
    }
}
