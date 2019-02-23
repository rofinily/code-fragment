package me.anchore.annotation;

import me.anchore.util.Checker;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author anchore
 * @date 2018/12/23
 */
public class EqAndHashBuilder {

    public static boolean equals(Object o1, Object o2) {
        if (o1 == o2) {
            return true;
        }
        if (o1 == null || o2 == null) {
            return false;
        }
        if (o1.getClass() != o2.getClass()) {
            return false;
        }
        return thisEquals(o1, o2);
    }

    private static boolean thisEquals(Object o1, Object o2) {
        ObjProps objProps = new ObjProps(o1.getClass(), member -> !member.isAnnotationPresent(IgnoreEqAndHash.class));

        List<Object> props1 = objProps.propStream().map(prop -> prop.getValue(o1)).collect(Collectors.toList());
        List<Object> props2 = objProps.propStream().map(prop -> prop.getValue(o2)).collect(Collectors.toList());

        for (int i = 0; i < props1.size(); i++) {
            if (!Checker.equals(props1.get(i), props2.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static int hashCode(Object o) {
        if (o == null) {
            return 0;
        }

        Object[] props = new ObjProps(o.getClass(), member -> !member.isAnnotationPresent(IgnoreEqAndHash.class))
                .propStream().map(prop -> prop.getValue(o)).toArray();
        return Arrays.hashCode(props);
    }
}