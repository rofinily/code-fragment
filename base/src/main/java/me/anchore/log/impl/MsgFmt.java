package me.anchore.log.impl;

import me.anchore.util.Checker;
import me.anchore.util.Pair;

import java.util.Optional;

/**
 * @author anchore
 * @date 2018/12/30
 */
class MsgFmt {

    static Pair<String, Optional<Throwable>> format(String msg, Object... args) {
        if (Checker.isEmpty(args)) {
            return Pair.of(msg, Optional.empty());
        }

        boolean hasThrowable = false;
        if (args[args.length - 1] instanceof Throwable) {
            hasThrowable = true;
        }

        StringBuilder builder = new StringBuilder();
        String[] strings = msg.split("\\{\\}", hasThrowable ? args.length : args.length + 1);
        int i;
        for (i = 0; i < strings.length - 1; i++) {
            builder.append(strings[i]).append(args[i]);
        }
        String formated = builder.append(strings[i]).toString();

        return Pair.of(formated, hasThrowable ? Optional.of((Throwable) args[args.length - 1]) : Optional.empty());
    }
}