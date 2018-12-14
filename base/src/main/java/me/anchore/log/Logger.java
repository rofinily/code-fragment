package me.anchore.log;

/**
 * @author anchore
 * @date 2018/10/9
 */
public interface Logger {

    void info(Throwable t);

    void error(Throwable t);
}