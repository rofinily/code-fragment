package anc.pattern;

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

enum EnumSingleton {
    /**
     * instance init on class loaded
     */
    INSTANCE
}

class HolderSingleton {
    private static class Holder {
        /**
         * instance init on out-method getInstance called
         */
        static final Singleton INSTANCE = new Singleton();
    }

    public static Singleton getInstance() {
        return Holder.INSTANCE;
    }
}