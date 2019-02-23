package me.anchore.ioc;

import me.anchore.ioc.impl.BeanInfo;

import java.util.Set;

/**
 * @author anchore
 * @date 2019/2/22
 */
public interface BeanScanner {

    Set<BeanInfo> scan(String packageName);
}