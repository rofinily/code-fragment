package anc.stream;

import java.util.stream.IntStream;

public class StreamTest {
    public static void main(String[] args) {
        int[] a = {3, 2, 5, 7, 9, 3, 1, 1};
        IntStream.of(a).filter(x -> x > 2).sorted().toArray();
    }
}
