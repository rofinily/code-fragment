package me.anchore.transaction.demo;

import me.anchore.transaction.Transaction;
import me.anchore.transaction.TransactionHandler;
import me.anchore.transaction.TransactionManager;
import me.anchore.transaction.TransactionProxy;

/**
 * @author anchore
 * @date 2018/8/19
 */
public class SumAccTransactionManager implements TransactionManager<SumAccumulator> {
    public static void main(String[] args) {
        SumAccumulator proxyee = new SumAccumulator() {

            int sum;

            @Override
            public int getSum() {
                return sum;
            }

            @Override
            public void setSum(int sum) {
                this.sum = sum;
            }

            @Override
            public void accumulate(int i) {
                throw new RuntimeException();
            }
        };
        SumAccumulator sumAcc = new TransactionProxy(new TransactionHandler(proxyee, new SumAccTransactionManager().getTransaction(proxyee))).proxy(SumAccumulator.class);
        sumAcc.accumulate(100);
        sumAcc.accumulate(100);
        sumAcc.accumulate(100);
        System.out.println(sumAcc.getSum());
    }

    @Override
    public Transaction getTransaction(SumAccumulator attach) {
        return new Transaction() {

            int oldSum;

            @Override
            public void begin() {
                oldSum = attach.getSum();
            }

            @Override
            public void commit() {
            }

            @Override
            public void rollback() {
                attach.setSum(oldSum);
            }

            @Override
            public void close() {

            }
        };
    }
}