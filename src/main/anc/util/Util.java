package anc.util;

public class Util {

//    public static Unsafe getUnsafe() {
//        return UnsafeHolder.unsafe;
//    }
//
//    private static class UnsafeHolder {
//        static Unsafe unsafe;
//
//        static {
//            try {
//                Field field = Unsafe.class.getDeclaredField("theUnsafe");
//                field.setAccessible(true);
//                unsafe = (Unsafe) field.get(null);
//            } catch (NoSuchFieldException | IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public static <T> void requireNonNull(T t) {
        if (t == null) {
            throw new NullPointerException();
        }
    }

    public static <T> void requireNonNull(T[] ts) {
        for (T t : ts) {
            requireNonNull(t);
        }
    }

    public static <T> void requireNonNull(T t0, T... ts) {
        requireNonNull(t0);
        requireNonNull(ts);
    }

}