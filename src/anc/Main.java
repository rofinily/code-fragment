package anc;

import anc.util.DataSetUtil;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author anchore
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println(Arrays.toString(DataSetUtil.getIntArray(10)));
    }
}