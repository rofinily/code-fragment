package anc.global;

import sun.misc.Unsafe;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Util {

    public static Unsafe getUnsafe() {
        return UnsafeHolder.unsafe;
    }

    public static void execute(Runnable r) {
        ExecHolder.execute(r);
    }

    public static int[] getIntArrayDataSet(InputStream is) {
        Scanner sc = null;
        int[] a = null;
        try {
            sc = new Scanner(is);
            if (!sc.hasNextInt()) {
                return new int[0];
            }
            a = new int[sc.nextInt()];
            for (int i = 0; sc.hasNextInt(); ) {
                a[i++] = sc.nextInt();
            }
            return a;
        } finally {
            if (sc != null) {
                sc.close();
            }
        }
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
