package anc.util.mail.entity;

import anc.util.StrUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author anchore
 */
public class MailContent {
    private Map<String, String> type2Content = new HashMap<>();

    private static final String[] TYPE_PRIORITY = new String[]{"text/html", "text/plain"};

    public String put(String type, String content) {
        return type2Content.put(type, content);
    }

    public String get(String type) {
        return type2Content.get(type);
    }

    public String getDefault() {
        for (String type : TYPE_PRIORITY) {
            if (type2Content.containsKey(type)) {
                return type2Content.get(type);
            }
        }
        return StrUtil.EMPTY;
    }
}
