package me.anchore.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.function.Consumer;

/**
 * @author anchore
 * @date 2018/1/4
 */
public class FileUtil {
    /**
     * CommonUtils.deleteFile删文件有问题。。。
     *
     * @param f 文件或目录
     */
    public static void delete(final File f) {
        walk(f, File::delete);
    }

    public static void delete(String path) {
        delete(new File(path));
    }

    public static void walk(File f, Consumer<File> consumer) {
        if (f == null || !f.exists()) {
            return;
        }

        if (f.isDirectory()) {
            File[] children = f.listFiles();

            if (children != null) {
                for (File child : children) {
                    walk(child, consumer);
                }
            }
        }

        consumer.accept(f);
    }

    public static boolean exists(String path) {
        return new File(path).exists();
    }

    public static boolean exists(File f) {
        return f != null && f.exists();
    }

    public static void move(File src, File dest, boolean destIsDir) throws IOException {
        if (!exists(src)) {
            return;
        }
        if (equals(src, dest)) {
            return;
        }
        if (src.renameTo(dest)) {
            return;
        }

        copy(src, dest, destIsDir);

        delete(src);
    }

    private static boolean equals(File f1, File f2) throws IOException {
        return f1.getCanonicalPath().equals(f2.getCanonicalPath());
    }

    private static void copy(File src, File dest, boolean destIsDir) {
        if (src.isDirectory()) {
            copyDir(src, dest);
            return;
        }

        File destFile;
        if (destIsDir) {
            if (!dest.exists()) {
                dest.mkdirs();
            }
            destFile = new File(dest, src.getName());
        } else {
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            destFile = dest;
        }
        copyFile(src, destFile);
    }

    private static void copyDir(File src, File dest) {
        if (!src.exists()) {
            return;
        }
        File[] srcChildren = src.listFiles();
        if (Checker.isEmpty(srcChildren)) {
            return;
        }
        for (File srcChild : srcChildren) {
            File destChild = new File(dest, srcChild.getName());
            if (srcChild.isDirectory()) {
                if (!destChild.exists()) {
                    destChild.mkdirs();
                }
                copyDir(srcChild, destChild);
            } else {
                if (!dest.getParentFile().exists()) {
                    dest.getParentFile().mkdirs();
                }
                copyFile(srcChild, destChild);
            }
        }
    }

    private static void copyFile(File src, File dest) {
        try (FileChannel ich = new FileInputStream(src).getChannel();
             FileChannel och = new FileOutputStream(dest).getChannel()) {
            ich.transferTo(0, ich.size(), och);

            dest.setLastModified(src.lastModified());
        } catch (IOException e) {
            delete(dest);
        }
    }
}
