package anc;

import java.util.Collection;

public interface TestAction {

    @FunctionalInterface
    interface Op {
        boolean operate();
    }

    default void beforeOperate() {
        initDataSet();
    }

    default void afterOperate() {
        System.out.println("execute nothing after an operation");
    }

    default boolean perform(Collection<Op> ops) {
        for (Op op : ops) {
            boolean isSuccess = op.operate();
            if (!isSuccess) {
                return false;
            }
        }
        return true;
    }

    default boolean perform(Op... ops) {
        for (Op op : ops) {
            boolean isSuccess = op.operate();
            if (!isSuccess) {
                return false;
            }
        }
        return true;
    }

    default boolean perform(Op op) {
        boolean isSuccess = op.operate();
        return isSuccess;
    }

    void initDataSet();
}