package anc;

public class Main {
    Main() {
        throw new RuntimeException();
    }

    public static void main(String[] args) {
        synchronized (new Object()) {
            System.out.println("args = [" + args + "]");
        }
    }
}