package me.anchore.util.concurrent;

import me.anchore.util.Assert;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author anchore
 * @date 2018/5/5
 */
abstract class BaseThreadFactory implements ThreadFactory {

    private static final ThreadGroup THREAD_GROUP = new ThreadGroup("SwiftThreads");

    private final AtomicInteger threadNumber = new AtomicInteger(1);

    private final String namePrefix;

    BaseThreadFactory(Class<?> c) {
        this(getSimpleName(c));
    }

    BaseThreadFactory(String namePrefix) {
        Assert.notNull(namePrefix);

        this.namePrefix = namePrefix;
    }

    static Thread newThread(ThreadGroup group, Runnable target, String name, long stackSize) {
        Thread t = new Thread(group, target, name, stackSize);
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }

    static String getSimpleName(Class<?> c) {
        String name = c.getName();
        return name.substring(name.lastIndexOf('.') + 1);
    }

    @Override
    public Thread newThread(Runnable r) {
        if (r == null) {
            return null;
        }
        return newThread(THREAD_GROUP, r, String.format("%s-%d", namePrefix, threadNumber.getAndIncrement()), 0);
    }
}