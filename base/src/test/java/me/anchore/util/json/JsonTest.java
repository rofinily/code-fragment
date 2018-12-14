package me.anchore.util.json;

import me.anchore.util.json.impl.JsonArrayImpl;
import me.anchore.util.json.impl.JsonBooleanImpl;
import me.anchore.util.json.impl.JsonNumberImpl;
import me.anchore.util.json.impl.JsonObjectImpl;
import me.anchore.util.json.impl.JsonStringImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author anchore
 * @date 2018/10/20
 */
public class JsonTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testToString() {
        JsonObject jo = new JsonObjectImpl();
        jo.setProperty("archer", new JsonObjectImpl());
        jo.setProperty("classes", new JsonArrayImpl(
                new JsonArrayImpl(new JsonBooleanImpl(true), new JsonStringImpl("archer"))));
        jo.setProperty("price", new JsonNumberImpl(1D));
        jo.setProperty("arr", new JsonArrayImpl());
        System.out.println(jo);
    }
}