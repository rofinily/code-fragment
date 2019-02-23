package me.anchore.ioc;

/**
 * @author anchore
 * @date 2019/2/23
 */
public class DuplicateBeanException extends BeanException {
    public DuplicateBeanException() {
    }

    public DuplicateBeanException(String message) {
        super(message);
    }

    public DuplicateBeanException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateBeanException(Throwable cause) {
        super(cause);
    }

    public static DuplicateBeanException ofBeanId(String beanId) {
        return new DuplicateBeanException(String.format("duplicated bean id %s", beanId));
    }
}