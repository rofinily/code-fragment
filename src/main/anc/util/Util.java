package anc.util;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class Util {

    public static Unsafe getUnsafe() {
        return UnsafeHolder.unsafe;
    }

    private static class UnsafeHolder {
        static Unsafe unsafe;

        static {
            try {
                Field field = Unsafe.class.getDeclaredField("theUnsafe");
                field.setAccessible(true);
                unsafe = (Unsafe) field.get(null);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

}