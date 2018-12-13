package me.anchore.util.json.impl;

import me.anchore.util.json.Json;
import me.anchore.util.json.JsonObject;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author anchore
 * @date 2018/10/19
 */
public class JsonObjectImpl extends BaseJson implements JsonObject {

    private Map<String, Json> properties = new LinkedHashMap<>();

    public JsonObjectImpl() {
    }

    public JsonObjectImpl(Entry<String, Json>... properties) {
        Arrays.stream(properties).forEach(entry -> this.properties.put(entry.getKey(), entry.getValue()));
    }

    @Override
    public <J extends Json> J getProperty(String name) {
        return (J) properties.get(name);
    }

    @Override
    public <J extends Json> J setProperty(String name, J j) {
        return (J) properties.put(name, j);
    }

    @Override
    public Type getType() {
        return Type.OBJECT;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (Entry<String, Json> entry : properties.entrySet()) {
            sb.append("\"").append(entry.getKey()).append("\":").append(entry.getValue()).append(",");
        }
        if (sb.length() > 1) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.append("}").toString();
    }
}