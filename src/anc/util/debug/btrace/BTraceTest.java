package anc.util.debug.btrace;

import com.sun.btrace.annotations.*;

import static com.sun.btrace.BTraceUtils.*;

@BTrace
public class BTraceTest {
    @OnMethod(clazz = "anc.util.debug.btrace.Main", method = "f", location = @Location(Kind.CALL))
    public static void onMain(String s) {
        println(Strings.concat(s, "btrace"));
    }
}
