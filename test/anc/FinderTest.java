//package anc;
//
//import org.junit.After;
//import org.junit.Test;
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Arrays;
//import java.util.Scanner;
//
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.fail;
//
//public class FinderTest {
//    Finder finder = new Finder();
//    Scanner sc;
//    int[] a;
//
//    @Test
//    public void testFindMth2Nth() throws Exception {
//        prepare();
//
//        int m = a.length / 10,
//                n = a.length / 2;
//        finder.findMth2Nth(0, a.length - 1, m, n);
//
//        assertTrue(checkResult(a, m, n));
//    }
//
//    @Test
//    public void testFindKth() throws FileNotFoundException {
//        prepare();
//
//        int k = a.length / 2;
//        finder.findMth2Nth(0, a.length - 1, k, k);
//
//        assertTrue(checkResult(a, k, k));
//    }
//
//    @Test
//    public void testGenDataSet() throws IOException, InterruptedException {
//        genDataSet();
//    }
//
//    void genDataSet() throws IOException, InterruptedException {
//        Process p = Runtime.getRuntime().exec("python d:/batch.py");
//        InputStream is = p.getInputStream();
//        p.waitFor();
////        byte[] bytes = new byte[1024];
////        for (int len = 0; (len = is.read(bytes)) != -1; ) {
////            System.out.print(new String(bytes, 0, len));
////        }
//    }
//
//    @After
//    public void end() {
//        if (sc != null) {
//            sc.close();
//        }
//    }
//
//    void prepare() throws FileNotFoundException {
//        sc = new Scanner(new FileInputStream("d:/dataset"));
//        if (!sc.hasNextInt()) {
//            fail();
//        }
//        a = new int[sc.nextInt()];
//        int i = 0;
//        while (sc.hasNextInt()) {
//            a[i++] = sc.nextInt();
//        }
//    }
//
//    boolean checkResult(int[] a, int m, int n) {
//        int[] aCopy = Arrays.copyOf(a, a.length);
//        Arrays.sort(aCopy);
//        for (int i = m; i <= n; i++) {
//            if (a[i] != aCopy[i]) {
//                return false;
//            }
//        }
//        return true;
//    }
//}
