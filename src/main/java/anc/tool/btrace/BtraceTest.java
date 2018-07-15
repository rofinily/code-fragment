//package anc.tool.btrace;
//
//import com.sun.btrace.annotations.BTrace;
//import com.sun.btrace.annotations.Kind;
//import com.sun.btrace.annotations.Location;
//import com.sun.btrace.annotations.OnMethod;
//
//import static com.sun.btrace.BTraceUtils.Strings;
//import static com.sun.btrace.BTraceUtils.println;
//
///**
// * @author anchore
// */
//@BTrace
//public class BtraceTest {
//    @OnMethod(clazz = "anc.tool.btrace.Main", method = "f", location = @Location(Kind.CALL))
//    public static void onMain(String s) {
//        println(Strings.concat(s, "btrace"));
//    }
//}
