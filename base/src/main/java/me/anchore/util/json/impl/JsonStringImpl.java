package me.anchore.util.json.impl;

import me.anchore.util.json.JsonString;

/**
 * @author anchore
 * @date 2018/10/20
 */
public class JsonStringImpl extends BaseJson implements JsonString {

    private String s;

    public JsonStringImpl() {
    }

    public JsonStringImpl(String s) {
        this.s = s;
    }

    @Override
    public String get() {
        return s;
    }

    @Override
    public String toString() {
        return "\"" + s + "\"";
    }
}