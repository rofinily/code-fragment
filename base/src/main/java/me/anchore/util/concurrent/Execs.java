package me.anchore.util.concurrent;

import me.anchore.draft.util.StackUtil;
import me.anchore.log.Loggers;

import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author anchore
 */
public class Execs {
    private static final Set<ExecutorService> EXECUTORS = new ConcurrentHashSet<>();

    private static final Set<Thread> THREADS = new ConcurrentHashSet<>();

    public static ExecutorService newFixedThreadPool(int nThreads, ThreadFactory threadFactory) {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(nThreads, threadFactory);
        EXECUTORS.add(fixedThreadPool);
        return fixedThreadPool;
    }

    public static ExecutorService newFixedThreadPool(int nThreads) {
        String simpleClassName = BaseThreadFactory.getSimpleName(StackUtil.getCaller().getClassName());
        return newFixedThreadPool(nThreads, new PoolThreadFactory(simpleClassName));
    }

    public static ExecutorService newSingleThreadExecutor(ThreadFactory threadFactory) {
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor(threadFactory);
        EXECUTORS.add(singleThreadExecutor);
        return singleThreadExecutor;
    }

    public static ExecutorService newSingleThreadExecutor() {
        String simpleClassName = BaseThreadFactory.getSimpleName(StackUtil.getCaller().getClassName());
        return newSingleThreadExecutor(new PoolThreadFactory(simpleClassName));
    }

    private static ExecutorService newCachedThreadPool(ThreadFactory threadFactory) {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool(threadFactory);
        EXECUTORS.add(cachedThreadPool);
        return cachedThreadPool;
    }

    public static ExecutorService newCachedThreadPool() {
        String simpleClassName = BaseThreadFactory.getSimpleName(StackUtil.getCaller().getClassName());
        return newCachedThreadPool(new PoolThreadFactory(simpleClassName));
    }

    private static ScheduledExecutorService newSingleThreadScheduledExecutor(ThreadFactory threadFactory) {
        ScheduledExecutorService singleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor(threadFactory);
        EXECUTORS.add(singleThreadScheduledExecutor);
        return singleThreadScheduledExecutor;
    }

    public static ScheduledExecutorService newSingleThreadScheduledExecutor() {
        String simpleClassName = BaseThreadFactory.getSimpleName(StackUtil.getCaller().getClassName());
        return newSingleThreadScheduledExecutor(new PoolThreadFactory(simpleClassName));
    }

    private static ScheduledExecutorService newScheduledThreadPool(int corePoolSize, ThreadFactory threadFactory) {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(corePoolSize, threadFactory);
        EXECUTORS.add(scheduledThreadPool);
        return scheduledThreadPool;
    }

    public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize) {
        String simpleClassName = BaseThreadFactory.getSimpleName(StackUtil.getCaller().getClassName());
        return newScheduledThreadPool(corePoolSize, new PoolThreadFactory(simpleClassName));
    }

    public static ExecutorService newThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
                                                        long keepAliveTime, TimeUnit unit,
                                                        BlockingQueue<Runnable> workQueue,
                                                        ThreadFactory threadFactory) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                keepAliveTime, unit,
                workQueue,
                threadFactory);

        EXECUTORS.add(executor);
        return executor;
    }

    private static Thread newThread(Runnable runnable, String name) {
        return newThread(BaseThreadFactory.THREAD_GROUP, runnable, name, 0);
    }

    public static Thread newThread(Runnable runnable) {
        return newThread(runnable, BaseThreadFactory.getSimpleName(runnable.getClass()));
    }

    private static Thread newThread(ThreadGroup group, Runnable target, String name, long stackSize) {
        Thread t = BaseThreadFactory.newThread(group, target, name, stackSize);
        THREADS.add(t);
        return t;
    }

    public static void shutdownAll() {
        for (ExecutorService exec : EXECUTORS) {
            try {
                exec.shutdown();
            } catch (Exception e) {
                Loggers.getLogger().error(e);
            }
        }

        for (Thread thread : THREADS) {
            try {
                thread.interrupt();
            } catch (Exception e) {
                Loggers.getLogger().error(e);
            }
        }
    }

    public static void shutdownAllNow() {
        for (ExecutorService exec : EXECUTORS) {
            try {
                exec.shutdownNow();
            } catch (Exception e) {
                Loggers.getLogger().error(e);
            }
        }

        for (Thread thread : THREADS) {
            try {
                thread.interrupt();
            } catch (Exception e) {
                Loggers.getLogger().error(e);
            }
        }
    }
}