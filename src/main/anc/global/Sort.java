package anc.global;

public class Sort {
    public static boolean checkResult(int[] a, Type type) {
        if (a.length < 2) {
            return true;
        }
        for (int i = 0, len = a.length; i < len - 1; i++) {
            if (type.compare(a[i], a[i + 1]) < 0) {
                return false;
            }
        }
        return true;
    }

    public enum Type {
        ASC() {
            @Override
            public int compare(int a, int b) {
                return b - a;
            }
        },
        DESC() {
            @Override
            public int compare(int a, int b) {
                return a - b;
            }
        };

        public int compare(int a, int b) {
            throw new UnsupportedOperationException();
        }

        public Type reverse() {
            return this == ASC ? DESC : this;
        }
    }
}
