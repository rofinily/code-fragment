package me.anchore.log;

/**
 * @author anchore
 * @date 2018/10/9
 */
public interface Logger {

    void debug(String msg, Object... args);

    void debug(Throwable t);

    void info(String msg, Object... args);

    void warn(String msg, Object... args);

    void warn(Throwable t);

    void error(String msg, Object... args);

    void error(Throwable t);
}