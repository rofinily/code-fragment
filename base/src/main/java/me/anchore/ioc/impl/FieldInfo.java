package me.anchore.ioc.impl;

/**
 * @author anchore
 * @date 2019/2/26
 */
public class FieldInfo extends ReflectItem {

    private String typeName;

    public FieldInfo() {
    }

    public FieldInfo(String name, String typeName) {
        super(name);
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
}