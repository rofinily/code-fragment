package anc.util;

import anc.constant.Const;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Scanner;

public class DataSetUtil {
    public static int[] getIntArray(InputStream is) {
        Objects.requireNonNull(is);

        Scanner sc = null;
        int[] a;
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

    public static InputStream getInputStream(Path p) {
        try {
            return Files.newInputStream(p);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static InputStream getInputStream(Command command) {
        try {
            return command.exec().getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}