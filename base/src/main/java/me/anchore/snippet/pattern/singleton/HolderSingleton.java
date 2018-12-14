package me.anchore.snippet.pattern.singleton;

/**
 * @author anchore
 * @date 2018/7/15
 */
class HolderSingleton {
    public static Singleton getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        /**
         * instance init on out-method getInstance called
         */
        static final Singleton INSTANCE = new Singleton();
    }
}