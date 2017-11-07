package anc;

import anc.util.Command;
import anc.util.DataSetUtil;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * @author anchore
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println(
                Arrays.toString(
                        DataSetUtil.getIntArray(
                                DataSetUtil.getInputStream(Command.create()
                                        .path(Paths.get("D:\\workspace\\code-fragment\\out\\production\\code-fragment"))
                                        .cmdArray("java", "anc.util.DataSetUtil"))
                        )
                )
        );
    }
}