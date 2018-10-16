package me.anchore.transaction.demo;

import me.anchore.transaction.Transactional;

import java.util.Random;

/**
 * @author anchore
 * @date 2018/8/19
 */
public class SumAccumulator {
    int sum = -1;

    @Transactional
    public void accumulate(int i) {
        sum += i;
        if (new Random(hashCode()).nextBoolean()) {
            throw new RuntimeException();
        }
    }
}