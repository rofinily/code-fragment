package me.anchore.util.concurrent;

import me.anchore.log.Loggers;

import java.security.PrivilegedAction;
import java.security.PrivilegedExceptionAction;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
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

    private final static Set<ExecutorService> EXECUTORS = new ConcurrentHashSet<>();

    private final static Set<Thread> THREADS = new ConcurrentHashSet<>();

    public static ExecutorService newFixedThreadPool(int nThreads) {
        return newFixedThreadPool(nThreads, new PoolThreadFactory(Execs.class));
    }

    public static ExecutorService newFixedThreadPool(int nThreads, ThreadFactory threadFactory) {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(nThreads, threadFactory);
        EXECUTORS.add(fixedThreadPool);
        return fixedThreadPool;
    }

    public static ExecutorService newSingleThreadExecutor() {
        return newSingleThreadExecutor(new PoolThreadFactory(Execs.class));
    }

    private static ExecutorService newSingleThreadExecutor(ThreadFactory threadFactory) {
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor(threadFactory);
        EXECUTORS.add(singleThreadExecutor);
        return singleThreadExecutor;
    }

    public static ExecutorService newCachedThreadPool() {
        return newCachedThreadPool(new PoolThreadFactory(Execs.class));
    }

    private static ExecutorService newCachedThreadPool(ThreadFactory threadFactory) {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool(threadFactory);
        EXECUTORS.add(cachedThreadPool);
        return cachedThreadPool;
    }

    public static ScheduledExecutorService newSingleThreadScheduledExecutor() {
        return newSingleThreadScheduledExecutor(new PoolThreadFactory(Execs.class));
    }

    private static ScheduledExecutorService newSingleThreadScheduledExecutor(ThreadFactory threadFactory) {
        ScheduledExecutorService singleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor(threadFactory);
        EXECUTORS.add(singleThreadScheduledExecutor);
        return singleThreadScheduledExecutor;
    }

    public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize) {
        return newScheduledThreadPool(corePoolSize, new PoolThreadFactory(Execs.class));
    }

    private static ScheduledExecutorService newScheduledThreadPool(int corePoolSize, ThreadFactory threadFactory) {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(corePoolSize, threadFactory);
        EXECUTORS.add(scheduledThreadPool);
        return scheduledThreadPool;
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

    public static Thread newThread(Runnable runnable) {
        return newThread(runnable, BaseThreadFactory.getSimpleName(runnable.getClass()));
    }

    private static Thread newThread(Runnable runnable, String name) {
        return newThread(BaseThreadFactory.THREAD_GROUP, runnable, name, 0);
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

    public static ExecutorService unconfigurableExecutorService(ExecutorService executor) {
        return Executors.unconfigurableExecutorService(executor);
    }

    public static ScheduledExecutorService unconfigurableScheduledExecutorService(ScheduledExecutorService executor) {
        return Executors.unconfigurableScheduledExecutorService(executor);
    }

    public static <T> Callable<T> callable(Runnable task, T result) {
        return Executors.callable(task, result);
    }

    public static Callable<Object> callable(Runnable task) {
        return Executors.callable(task);
    }

    public static Callable<Object> callable(final PrivilegedAction<?> action) {
        return Executors.callable(action);
    }

    public static Callable<Object> callable(final PrivilegedExceptionAction<?> action) {
        return Executors.callable(action);
    }

    public static <T> Callable<T> privilegedCallable(Callable<T> callable) {
        return Executors.privilegedCallable(callable);
    }

    public static <T> Callable<T> privilegedCallableUsingCurrentClassLoader(Callable<T> callable) {
        return Executors.privilegedCallableUsingCurrentClassLoader(callable);
    }
}