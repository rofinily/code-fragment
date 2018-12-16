package me.anchore.transaction.demo;

import me.anchore.transaction.Transactional;

/**
 * @author anchore
 * @date 2018/8/19
 */
public interface SumAccumulator {

    int getSum();

    void setSum(int sum);

    @Transactional
    void accumulate(int i);
}