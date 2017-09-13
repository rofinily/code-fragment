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

    public static boolean checkSortResult(int[] a, Sort.Type type) {
        if (a.length == 1) {
            return true;
        }
        for (int i = 0, len = a.length; i < len - 1; i++) {
            if (type.compare(a[i], a[i + 1]) < 0) {
                return false;
            }
        }
        return true;
    }

    public static class Sort {
        public enum Type {
            ASC() {
                @Override
                public int compare(int a, int b) {
                    return a - b;
                }
            },
            DESC() {
                @Override
                public int compare(int a, int b) {
                    return b - a;
                }
            };

            public int compare(int a, int b) {
                throw new UnsupportedOperationException();
            }
        }
    }
}
