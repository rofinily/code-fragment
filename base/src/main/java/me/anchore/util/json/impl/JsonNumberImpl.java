package me.anchore.util.json.impl;

import me.anchore.util.json.JsonNumber;

/**
 * @author anchore
 * @date 2018/10/19
 */
public class JsonNumberImpl extends BaseJson implements JsonNumber {

    private Number number;

    public JsonNumberImpl(Number number) {
        this.number = number;
    }

    @Override
    public Number get() {
        return number;
    }

    public Number set(Number number) {
        Number prev = this.number;
        this.number = number;
        return prev;
    }

    @Override
    public String toString() {
        return String.valueOf(number);
    }
}