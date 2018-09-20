package me.anchore.beta.transaction.demo;

import me.anchore.beta.transaction.Transaction;
import me.anchore.beta.transaction.TransactionInterceptor;
import me.anchore.beta.transaction.TransactionProxy;
import me.anchore.beta.transaction.TransationManager;

/**
 * @author anchore
 * @date 2018/8/19
 */
public class SumAccTransactionManager implements TransationManager<SumAccumulator> {
    @Override
    public Transaction<SumAccumulator> getTransaction(SumAccumulator old) {
        return new Transaction<SumAccumulator>() {
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

    public static void main(String[] args) {
        SumAccumulator sumAcc = new SumAccumulator();
        SumAccumulator proxy = new TransactionProxy(new TransactionInterceptor(new SumAccTransactionManager().getTransaction(sumAcc))).proxy(SumAccumulator.class);
        proxy.accumulate(100);
        proxy.accumulate(100);
        proxy.accumulate(100);
        System.out.println(sumAcc.sum);
    }
}