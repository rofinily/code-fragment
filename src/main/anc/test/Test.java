package anc.test;

import anc.global.Util;
import sun.misc.Unsafe;

public class Test {
    public static void main(String[] args) throws ClassNotFoundException {
        Unsafe unsafe = Util.getUnsafe();
    }
}