package me.anchore.ioc.impl;

import java.util.HashMap;
import java.util.Map;

/**
 * @author anchore
 * @date 2019/2/27
 */
public class AnnotationInfo extends ReflectItem {

    private Map<String, Object> properties = new HashMap<>();

    public AnnotationInfo(String name) {
        super(name);
    }

    void setProperty(String name, Object value) {
        properties.put(name, value);
    }

    <T> T getProperty(String name) {
        return (T) properties.get(name);
    }

    boolean containsProperty(String name) {
        return properties.containsKey(name);
    }
}