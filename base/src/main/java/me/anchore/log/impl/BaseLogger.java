package me.anchore.log.impl;

import me.anchore.log.Logger;
import me.anchore.util.Pair;
import me.anchore.util.Strings;

import java.util.Optional;

/**
 * @author anchore
 * @date 2019/2/23
 */
abstract class BaseLogger implements Logger {

    protected Pair<String, Optional<Throwable>> format(String s, Object... objects) {
        return MsgFmt.fmt(s, objects);
    }

    @Override
    public boolean isDebugEnabled() {
        return true;
    }

    @Override
    public void debug(String msg) {
        debug(msg, (Object[]) null);
    }

    @Override
    public void debug(Throwable t) {
        debug(Strings.EMPTY, t);
    }

    @Override
    public boolean isInfoEnabled() {
        return true;
    }

    @Override
    public void info(String msg) {
        info(msg, (Object[]) null);
    }

    @Override
    public boolean isWarnEnabled() {
        return true;
    }

    @Override
    public void warn(String msg) {
        warn(msg, (Object[]) null);
    }

    @Override
    public void warn(Throwable t) {
        warn(Strings.EMPTY, t);
    }

    @Override
    public boolean isErrorEnabled() {
        return true;
    }

    @Override
    public void error(String msg) {
        error(msg, (Object[]) null);
    }

    @Override
    public void error(Throwable t) {
        error(Strings.EMPTY, t);
    }
}