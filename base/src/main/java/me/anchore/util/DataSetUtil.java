package me.anchore.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import java.util.Scanner;

/**
 * @author anchore
 */
public class DataSetUtil {
    private static final Random R = new Random();

    public static int[] getIntArray(int size) {
        return getIntArray(R, size, 0, size << 1);
    }

    public static int[] getIntArray(Random r, int size, int from, int to) {
        return r.ints(size, from, to).toArray();
    }

    public static int[] getIntArray(InputStream is) {
        Assert.notNull(is);

        Scanner sc = null;
        int[] a;
        try {
            sc = new Scanner(is);
            if (!sc.hasNextInt()) {
                return new int[]{};
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

    public static InputStream getInputStream(Path p) {
        try {
            return Files.newInputStream(p);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}