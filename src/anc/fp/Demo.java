package anc.fp;

import java.io.UnsupportedEncodingException;
import java.util.function.IntFunction;

/**
 * @author anchore
 * @date 2017/11/11
 */
public class Demo {

    public static void main(String[] args) throws UnsupportedEncodingException {
        IntFunction<int[]> f = int[]::new;
        // crazy!!!
        int[] ints = f.apply(12);

        NewStringFunc newStringFunc = String::new;
        System.out.println(newStringFunc.apply(new byte[]{'a', 'c'}, "utf8"));
    }

    @FunctionalInterface
    interface NewStringFunc {
        String apply(byte[] bytes, String charset) throws UnsupportedEncodingException;
    }

}
