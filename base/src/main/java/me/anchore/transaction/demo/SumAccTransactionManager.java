package me.anchore.transaction.demo;

import me.anchore.transaction.Transaction;
import me.anchore.transaction.TransactionInterceptor;
import me.anchore.transaction.TransactionManager;
import me.anchore.transaction.TransactionProxy;

/**
 * @author anchore
 * @date 2018/8/19
 */
public class SumAccTransactionManager implements TransactionManager<SumAccumulator> {
    public static void main(String[] args) {
        SumAccumulator sumAcc = new SumAccumulator();
        SumAccumulator proxy = new TransactionProxy(new TransactionInterceptor(sumAcc, new SumAccTransactionManager().getTransaction(sumAcc))).proxy(SumAccumulator.class);
        proxy.accumulate(100);
        proxy.accumulate(100);
        proxy.accumulate(100);
        System.out.println(sumAcc.sum);
    }

    @Override
    public Transaction getTransaction(SumAccumulator old) {
        return new Transaction() {
            int oldSum;

            @Override
            public void begin() {
                oldSum = old.sum;
            }

            @Override
            public void commit() {
            }

            @Override
            public void rollback() {
                old.sum = oldSum;
            }
        };
    }
}