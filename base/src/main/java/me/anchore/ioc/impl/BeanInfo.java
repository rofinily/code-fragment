package me.anchore.ioc.impl;

import me.anchore.annotation.EqAndHashBuilder;
import me.anchore.annotation.ToStringBuilder;
import me.anchore.util.json.JsonException;

/**
 * @author anchore
 * @date 2019/2/22
 */
public class BeanInfo {

    private String id;

    private String className;

    public BeanInfo(String id, String className) {
        this.id = id;
        this.className = className;
    }

    static BeanInfo ofClassName(String className) {
        return new BeanInfo(className, className);
    }

    public String getId() {
        return id;
    }

    public String getClassName() {
        return className;
    }

    @Override
    public int hashCode() {
        return EqAndHashBuilder.hashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqAndHashBuilder.equals(this, obj);
    }

    @Override
    public String toString() {
        try {
            return ToStringBuilder.toString(this);
        } catch (JsonException e) {
            return "";
        }
    }

    public static void main(String[] args) {
        System.out.println(new BeanInfo("1", "2"));
    }
}