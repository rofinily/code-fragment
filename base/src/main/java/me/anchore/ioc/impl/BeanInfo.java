package me.anchore.ioc.impl;

import me.anchore.annotation.EqAndHashBuilder;

/**
 * @author anchore
 * @date 2019/2/22
 */
public class BeanInfo {

    private String id;

    private ClassInfo classInfo;

    public BeanInfo(ClassInfo classInfo) {
        this(classInfo.getName(), classInfo);
    }

    public BeanInfo(String id, ClassInfo classInfo) {
        this.id = id;
        this.classInfo = classInfo;
    }

    public String getId() {
        return id;
    }

    public ClassInfo getClassInfo() {
        return classInfo;
    }

    @Override
    public int hashCode() {
        return EqAndHashBuilder.hashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqAndHashBuilder.equals(this, obj);
    }
}