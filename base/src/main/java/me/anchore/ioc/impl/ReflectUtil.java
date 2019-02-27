package me.anchore.ioc.impl;

import me.anchore.ioc.BeanException;

/**
 * @author anchore
 * @date 2019/2/27
 */
public class ReflectUtil {
    static String toTypeName(String desc) {
        switch (desc.charAt(0)) {
            case 'Z':
                return boolean.class.getName();
            case 'B':
                return byte.class.getName();
            case 'S':
                return short.class.getName();
            case 'C':
                return char.class.getName();
            case 'I':
                return int.class.getName();
            case 'J':
                return long.class.getName();
            case 'F':
                return float.class.getName();
            case 'D':
                return double.class.getName();
            case 'L':
                return desc.substring("L".length(), desc.length() - ";".length()).replace('/', '.');
            case '[':
                return toTypeName(desc.substring(1)) + "[]";
            default:
                throw new BeanException(String.format("wrong type desc %s", desc));
        }
    }
}