package anc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Util {
    public static int[] getIntArrayDataSet(String path) {
        Scanner sc = null;
        int[] a = null;
        try {
            sc = new Scanner(new FileInputStream(path));
            if (!sc.hasNextInt()) {
                return new int[0];
            }
            a = new int[sc.nextInt()];
            for (int i = 0; sc.hasNextInt(); ) {
                a[i++] = sc.nextInt();
            }
            return a;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new int[0];
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

}
