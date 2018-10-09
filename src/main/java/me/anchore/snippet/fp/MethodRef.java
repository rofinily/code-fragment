package me.anchore.snippet.fp;

import java.util.Comparator;
import java.util.function.IntFunction;

/**
 * @author anchore
 * @date 2017/11/12
 */
public class MethodRef {

    /**
     * Reference to a static method:
     * ContainingClass::staticMethodName.
     * <p>
     * the following method body is equivalent to:
     * Comparator<Integer> c = (a, b) -> Integer.compare(a, b);
     */
    void kind1() {
        Comparator<Integer> c = Integer::compare;
        System.out.println(c.compare(1, 2));
    }

    /**
     * Reference to an instance method of a particular object:
     * containingObject::instanceMethodName.
     * <p>
     * the following method body is  equivalent to:
     * Comparable<Integer> c = (a, b) -> a.compareTo(b);
     */
    void kind2() {
        Comparable<Integer> c = Integer.valueOf(1)::compareTo;
        System.out.println(c.compareTo(2));
    }

    /**
     * Reference to an instance method of an arbitrary object of a particular type:
     * ContainingType::methodName.
     * <p>
     * the following method body is equivalent to:
     * Comparator<Integer> c = (a, b) -> a.compareTo(b);
     */
    void kind3() {
        Comparator<Integer> c = Integer::compareTo;
        System.out.println(c.compare(1, 2));
    }

    /**
     * Reference to a constructor(in fact, constructor is static):
     * ClassName::new.
     * <p>
     * the following method body is equivalent to:
     * Integer[] integers = new Integer[10];
     */
    void kind4() {
        IntFunction<Integer[]> arrayNewer = Integer[]::new;
        Integer[] integers = arrayNewer.apply(10);
        System.out.println(integers.length);
    }
}

