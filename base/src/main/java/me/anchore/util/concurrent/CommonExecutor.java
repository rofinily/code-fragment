package me.anchore.util.concurrent;

import java.util.concurrent.ExecutorService;

/**
 * @author anchore
 * @date 2018/6/14
 */
public class CommonExecutor {

    private static final CommonExecutor INSTANCE = new CommonExecutor();
    private ExecutorService exec = SwiftExecutors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), new PoolThreadFactory(getClass()));

    private CommonExecutor() {
    }

    public static ExecutorService get() {
        return INSTANCE.exec;
    }
}