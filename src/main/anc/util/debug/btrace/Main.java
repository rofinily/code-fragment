package anc.util.debug.btrace;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        new Main().f("asd");
    }

    public void f(String s) throws InterruptedException {
        TimeUnit.SECONDS.sleep(20);
        System.out.println(s);
    }
}