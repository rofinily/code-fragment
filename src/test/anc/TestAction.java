package anc;

import java.util.Collection;

@Deprecated
public interface TestAction {

    class FailureException extends RuntimeException {
        FailureException(String err) {
            super(err);
        }
    }

    interface IOperation {
        String getDesc();

        boolean operate();
    }

    default void beforeOperate() {
        initDataSet();
    }

    default void afterOperate() {
        System.out.println("execute nothing after an operation");
    }

    default void perform(Collection<IOperation> ops) {
        boolean isSuccess;
        for (IOperation op : ops) {
            isSuccess = op.operate();
            if (!isSuccess) {
                throw new FailureException(op.getDesc());
            }
        }
    }

    default void perform(IOperation op0, IOperation... ops) throws FailureException {
        perform(op0);
        perform(ops);
    }

    default void perform(IOperation op) throws FailureException {
        boolean isSuccess = op.operate();
        if (!isSuccess) {
            throw new FailureException(op.getDesc());
        }
    }

    default void perform(IOperation[] ops) throws FailureException {
        boolean isSuccess;
        for (IOperation op : ops) {
            isSuccess = op.operate();
            if (!isSuccess) {
                throw new FailureException(op.getDesc());
            }
        }
    }

    void initDataSet();
}