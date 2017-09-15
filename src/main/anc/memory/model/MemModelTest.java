package anc.memory.model;

import anc.global.Util;

public class MemModelTest {
    FinalFieldTest finalFieldTest;

    class FinalFieldTest {
        final int i;

        FinalFieldTest() throws InterruptedException {
            finalFieldTest = this;
            Thread.sleep(10);
            i = 1;
        }
    }

    void test() throws InterruptedException {
        Util.execute(() -> {
            try {
                new FinalFieldTest();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread.sleep(1);
        Util.execute(() -> {
            System.out.print(finalFieldTest.i);
        });
    }

    public static void main(String[] args) throws InterruptedException {
        MemModelTest memModelTest = new MemModelTest();
        memModelTest.test();
    }

}