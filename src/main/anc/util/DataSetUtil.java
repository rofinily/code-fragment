package anc.util;

import anc.constant.Const;

import java.io.*;
import java.util.Scanner;

public class DataSetUtil {
    public static int[] getIntArray(InputStream is) {
        Scanner sc = null;
        int[] a = null;
        try {
            sc = new Scanner(is);
            if (!sc.hasNextInt()) {
                return Const.EMPTY_INT_ARRAY;
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

    public static InputStream getInputStream(File f) {
        try {
            return new FileInputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static InputStream getInputStream(String cmd) {
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            return p.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
