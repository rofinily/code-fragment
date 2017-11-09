package anc.util;

import java.io.File;
import java.nio.file.Path;

/**
 * @author anchore
 * @date 2017/11/9
 */
public final class FileUtil {
    public static void delete(File file) {
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                delete(f);
            }
        }
        file.delete();
    }

    public static void delete(Path p) {
        delete(p.toFile());
    }

}
