package anc.global;

import java.io.InputStream;
import java.util.Scanner;

public class DataSetUtil {
    public static int[] getIntArray(InputStream is) {
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
}
