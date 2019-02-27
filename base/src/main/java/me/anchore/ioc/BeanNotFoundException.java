package me.anchore.ioc;

/**
 * @author anchore
 * @date 2019/2/27
 */
public class BeanNotFoundException extends BeanException {
    public BeanNotFoundException() {
    }

    public BeanNotFoundException(String message) {
        super(message);
    }

    public BeanNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeanNotFoundException(Throwable cause) {
        super(cause);
    }

    public static BeanNotFoundException ofId(String beanId) {
        return new BeanNotFoundException(String.format("bean with id %s not found", beanId));
    }

    public static BeanNotFoundException ofType(String typeName) {
        return new BeanNotFoundException(String.format("bean with type %s not found", typeName));
    }
}