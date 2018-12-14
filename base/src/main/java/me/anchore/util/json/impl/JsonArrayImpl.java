package me.anchore.util.json.impl;

import me.anchore.util.json.Json;
import me.anchore.util.json.JsonArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author anchore
 * @date 2018/10/19
 */
public class JsonArrayImpl extends BaseJson implements JsonArray {

    private List<Json> items = new ArrayList<>();

    public JsonArrayImpl() {
    }

    public JsonArrayImpl(Json... items) {
        this.items.addAll(Arrays.asList(items));
    }

    @Override
    public <J extends Json> void add(J j) {
        items.add(j);
    }

    @Override
    public <J extends Json> J get(int index) {
        return (J) items.get(index);
    }

    @Override
    public <J extends Json> J set(int index, J j) {
        return (J) items.set(index, j);
    }

    @Override
    public int length() {
        return items.size();
    }

    @Override
    public Type getType() {
        return Type.ARRAY;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (Json item : items) {
            sb.append(item.toString()).append(",");
        }
        if (sb.length() > 1) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.append("]").toString();
    }
}