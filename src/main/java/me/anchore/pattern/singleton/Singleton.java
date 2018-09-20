package me.anchore.pattern.singleton;

/**
 * @author anchore
 * @date 2017/11/9
 */
public class Singleton {
    /**
     * instance init on class loaded
     */
    private static final Singleton INSTANCE = new Singleton();

    public static Singleton getInstance() {
        return INSTANCE;
    }
}

