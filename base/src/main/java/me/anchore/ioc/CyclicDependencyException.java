package me.anchore.ioc;

import java.util.Set;

/**
 * @author anchore
 * @date 2019/2/22
 */
public class CyclicDependencyException extends BeanException {
    public CyclicDependencyException() {
    }

    public CyclicDependencyException(String message) {
        super(message);
    }

    public CyclicDependencyException(String message, Throwable cause) {
        super(message, cause);
    }

    public CyclicDependencyException(Throwable cause) {
        super(cause);
    }

    public CyclicDependencyException(Set<String> beanIds) {
        this(String.format("cyclis dependencies among %s", beanIds));
    }
}