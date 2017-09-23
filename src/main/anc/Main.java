package anc;

import java.io.IOException;

public class Main {
    Main() {
        throw new RuntimeException();
    }

    public static void main(String[] args) throws IOException {
        Process p = Runtime.getRuntime().exec(new String[]{"java", "-version", " | d:/pipe.txt"});
    }
}