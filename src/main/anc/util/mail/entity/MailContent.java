package anc.util.mail.entity;

import java.util.HashMap;
import java.util.Map;

public class MailContent<T> {
    private Map<String, T> type2Content = new HashMap<>();

    public T put(String type, T content) {
        return type2Content.put(type, content);
    }

    public T get(String type) {
        return type2Content.get(type);
    }

    public T getOrDefault(String type, T defaultVal) {
        T val = type2Content.get(type);
        return val != null ? val : defaultVal;
    }
}
