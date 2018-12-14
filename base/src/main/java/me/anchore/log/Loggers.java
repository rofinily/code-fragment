package me.anchore.log;

import me.anchore.log.impl.SystemLogger;

/**
 * @author anchore
 * @date 2018/10/9
 */
public class Loggers {

    private static LoggerFactory loggerFactory = new SystemLogger.Factory();

    public static Logger getLogger() {
        return loggerFactory.getLogger();
    }
}