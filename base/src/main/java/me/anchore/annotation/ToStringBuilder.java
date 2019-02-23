package me.anchore.annotation;

import me.anchore.util.json.JsonException;
import me.anchore.util.json.JsonUtil;

/**
 * @author anchore
 * @date 2019/2/23
 */
public class ToStringBuilder {

    public static String toString(Object o) throws JsonException {
        // todo 将属性过滤拆出来
        return JsonUtil.stringify(o);
    }
}