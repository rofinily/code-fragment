package me.anchore.util.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.anchore.util.json.annotation.IgnoreJson;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author anchore
 * @date 2018/12/15
 */
public class JsonUtilTest {

    @Test
    public void testStringify() throws JsonException, JsonProcessingException {

        Assert.assertEquals(
                "{\"l\":1,\"strings\":[\"1\",\"2\"],\"map\":{\"key1\":{},\"key2\":\"value2\",\"key3\":3},\"numbers\":[2,3],\"singleton\":false,\"s\":\"str\"}",
                JsonUtil.stringify(new B()));
    }

    @Test(expected = JsonException.class)
    public void testStringifyCircular() throws JsonException {
        JsonUtil.stringify(new Exception());
    }
}

class A {
    private static int i = -1;

    long l = 1;

    List<String> strings = Arrays.asList("1", "2");

    Map<Object, Object> map = new HashMap<Object, Object>() {{
        put("key1", new Object());
        put("key2", "value2");
        put("key3", 3);
    }};

    Number[] numbers = {2, 3};

    transient char c = 't';

    @IgnoreJson
    Set<?> set = Collections.singleton("ignore");

    public Number[] getNumbers() {
        return numbers;
    }

    public void getA() {
    }

    boolean isSingleton() {
        return false;
    }
}

class B extends A {

    String s = "str";
}