package me.anchore.ioc.impl;

import me.anchore.annotation.IgnoreEqAndHash;

import java.util.HashSet;
import java.util.Set;

/**
 * @author anchore
 * @date 2019/2/24
 */
class ClassInfo extends ReflectItem {

    @IgnoreEqAndHash
    Set<FieldInfo> fieldInfos = new HashSet<>();

    public ClassInfo() {
    }

    public ClassInfo(String name) {
        super(name);
    }

    void addFieldInfo(FieldInfo fieldInfo) {
        fieldInfos.add(fieldInfo);
    }

    Set<FieldInfo> getFieldInfos() {
        return fieldInfos;
    }
}