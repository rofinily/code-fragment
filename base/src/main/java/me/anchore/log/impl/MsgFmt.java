package me.anchore.log.impl;

import me.anchore.util.Checker;
import me.anchore.util.Pair;
import me.anchore.util.Strings;

import java.util.Optional;

/**
 * @author anchore
 * @date 2018/12/30
 */
class MsgFmt {

    private static final String PLACEHOLDER = "{}";

    static Pair<String, Optional<Throwable>> fmt(String msg, Object... args) {
        if (Checker.isEmpty(args)) {
            return Pair.of(msg, Optional.empty());
        }

        boolean hasThrowable = false;
        if (args[args.length - 1] instanceof Throwable) {
            hasThrowable = true;
        }

        if (msg == null) {
            msg = Strings.EMPTY;
        }

        StringBuilder sb = new StringBuilder(msg.length());
        int head = 0;
        for (int indexOfBrace, argI = 0; (indexOfBrace = msg.indexOf(PLACEHOLDER, head)) != -1; ) {
            for (int i = head; i < indexOfBrace; i++) {
                sb.append(msg.charAt(i));
            }
            if (argI < args.length) {
                sb.append(args[argI++]);
            } else {
                sb.append(PLACEHOLDER);
            }
            head = indexOfBrace + 2;
        }

        if (head < msg.length()) {
            for (int i = head; i < msg.length(); i++) {
                sb.append(msg.charAt(i));
            }
        }

        return Pair.of(sb.toString(), hasThrowable ? Optional.of((Throwable) args[args.length - 1]) : Optional.empty());
    }
}