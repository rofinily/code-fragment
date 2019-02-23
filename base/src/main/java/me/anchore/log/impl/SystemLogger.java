package me.anchore.log.impl;

import me.anchore.log.Logger;
import me.anchore.log.LoggerFactory;
import me.anchore.log.impl.Ansi.Color;
import me.anchore.util.Pair;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * @author anchore
 * @date 2018/10/20
 */
public class SystemLogger extends BaseLogger {

    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("yy-M-d H:m:s.S");

    @Override
    public void debug(String msg, Object... args) {
        Pair<String, Optional<Throwable>> pair = MsgFmt.fmt(msg, args);
        System.out.printf("%s %s %s %s%n", now(), Thread.currentThread().getName(), Color.DEFAULT.highlightForeground("DEBUG"), pair.getKey());
        pair.getValue().ifPresent(t -> t.printStackTrace(System.out));
    }

    private static String now() {
        return DF.format(LocalDateTime.now());
    }

    @Override
    public void info(String msg, Object... args) {
        Pair<String, Optional<Throwable>> pair = MsgFmt.fmt(msg, args);
        System.out.printf("%s %s %s %s%n", now(), Thread.currentThread().getName(), Color.CYAN.highlightForeground("INFO"), pair.getKey());
        pair.getValue().ifPresent(t -> t.printStackTrace(System.out));
    }

    @Override
    public void warn(String msg, Object... args) {
        Pair<String, Optional<Throwable>> pair = MsgFmt.fmt(msg, args);
        System.out.printf("%s %s %s %s%n", now(), Thread.currentThread().getName(), Color.YELLOW.highlightForeground("WARN"), pair.getKey());
        pair.getValue().ifPresent(t -> t.printStackTrace(System.out));
    }

    @Override
    public void error(String msg, Object... args) {
        Pair<String, Optional<Throwable>> pair = MsgFmt.fmt(msg, args);
        System.out.printf("%s %s %s %s%n", now(), Thread.currentThread().getName(), Color.RED.highlightForeground("ERROR"), pair.getKey());
        pair.getValue().ifPresent(t -> t.printStackTrace(System.err));
    }

    public static class Factory implements LoggerFactory {

        @Override
        public Logger getLogger() {
            return new SystemLogger();
        }
    }
}