package me.anchore.ioc;

/**
 * @author anchore
 * @date 2019/2/13
 */
public interface BeanFactory {

    <T> T getBean(Class<T> c) throws BeanException;

    <T> T getBean(String beanId) throws BeanException;

    <T> T getBean(String beanId, Object... args) throws BeanException;
}