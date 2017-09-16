package anc.global;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Util {

    public static Unsafe getUnsafe() {
        return UnsafeHolder.unsafe;
    }

    public static void execute(Runnable r) {
        ExecHolder.execute(r);
    }

    /**
     * a = A, b = B
     * a ^ a = 0
     * b = b ^ 0 = b ^ (a^a) = (a^b) ^ a
     * <p>
     * a = a ^ b; a: A^B, b: B
     * b = a ^ b; a: A^B, b: A^B^B = A
     * a = a ^ b; a: A^B^A = B, b: A
     */
    public static void swap(int[] a, int p1, int p2) {
        a[p1] = a[p1] ^ a[p2];
        a[p2] = a[p1] ^ a[p2];
        a[p1] = a[p1] ^ a[p2];
    }

    private static class ExecHolder {
        static final Executor EXEC = Executors.newFixedThreadPool(5);

        static void execute(Runnable r) {
            EXEC.execute(r);
        }
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
