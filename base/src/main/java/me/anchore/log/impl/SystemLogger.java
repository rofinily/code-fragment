package me.anchore.log.impl;

import me.anchore.log.Logger;
import me.anchore.log.LoggerFactory;

/**
 * @author anchore
 * @date 2018/10/20
 */
public class SystemLogger implements Logger {

    @Override
    public void info(Throwable t) {
        if (t != null) {
            t.printStackTrace(System.out);
        }
    }

    @Override
    public void error(Throwable t) {
        if (t != null) {
            t.printStackTrace(System.err);
        }
    }

    public static class Factory implements LoggerFactory {

        @Override
        public Logger getLogger() {
            return new SystemLogger();
        }
    }
}