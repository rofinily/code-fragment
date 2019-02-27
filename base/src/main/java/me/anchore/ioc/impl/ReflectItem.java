package me.anchore.ioc.impl;

import me.anchore.annotation.EqAndHashBuilder;
import me.anchore.annotation.IgnoreEqAndHash;

import java.util.ArrayList;
import java.util.List;

/**
 * @author anchore
 * @date 2019/2/26
 */
class ReflectItem {

    private String name;

    @IgnoreEqAndHash
    private List<AnnotationInfo> annotationInfos = new ArrayList<>();

    public ReflectItem() {
    }

    public ReflectItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    void addAnnotationInfo(AnnotationInfo annotationInfo) {
        annotationInfos.add(annotationInfo);
    }

    AnnotationInfo getAnnotationInfo(Class<?> annotationClass) {
        for (AnnotationInfo annotationInfo : annotationInfos) {
            if (annotationInfo.getName().equals(annotationClass.getName())) {
                return annotationInfo;
            }
        }
        return null;
    }

    boolean isAnnotationPresent(Class<?> annotationClass) {
        return getAnnotationInfo(annotationClass) != null;
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