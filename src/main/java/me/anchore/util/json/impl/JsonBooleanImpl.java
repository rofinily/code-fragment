package me.anchore.util.json.impl;

import me.anchore.util.json.JsonBoolean;

/**
 * @author anchore
 * @date 2018/10/19
 */
public class JsonBooleanImpl extends BaseJson implements JsonBoolean {

    private Boolean b;

    public JsonBooleanImpl(Boolean b) {
        this.b = b;
    }

    @Override
    public Boolean get() {
        return b;
    }

    public Boolean set(Boolean b) {
        Boolean prev = this.b;
        this.b = b;
        return prev;
    }

    @Override
    public String toString() {
        return String.valueOf(b);
    }
}