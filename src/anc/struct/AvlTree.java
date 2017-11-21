package anc.struct;

import java.util.Comparator;
import java.util.Random;
import java.util.stream.Stream;

/**
 * @author anchore
 * @date 2017/11/21
 */
public class AvlTree<T> {
    Node<T> treeRoot;
    Comparator<T> c;

    public AvlTree(Comparator<T> c) {
        this.c = c;
    }

    void insert(T v) {
        Node<T> n = new Node<>(v);
        if (treeRoot == null) {
            treeRoot = n;
            return;
        }
        insert(treeRoot, n, c);
    }

    public static void main(String[] args) {
        Random r = new Random();
        AvlTree<Integer> avl = new AvlTree<>(Integer::compareTo);
        Stream.generate(() -> r.nextInt(10)).limit(10).distinct().forEach(avl::insert);
        System.out.println(avl);
    }

    static class Node<T> {
        T v;
        int h = 1;
        Node<T> left;
        Node<T> right;

        Node(T v) {
            this.v = v;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "v=" + v +
                    ", h=" + h +
                    '}';
        }
    }

    private static <T> void insert(Node<T> r, Node<T> n, Comparator<T> c) {
        int cmp = c.compare(r.v, n.v);
        if (cmp > 0) {
            if (r.left == null) {
                r.left = n;
                r.h = height(r);
                return;
            }
            insert(r.left, n, c);
            int bf = balanceFactor(r.left);
            if (bf > 1) {
                r.left = llRotate(r.left);
            } else if (bf < -1) {
                r.left = lrRotate(r.left);
            }
        } else {
            if (r.right == null) {
                r.right = n;
                r.h = 2;
                return;
            }
            insert(r.right, n, c);
            int bf = balanceFactor(r.right);
            if (bf > 1) {
                r.right = rlRotate(r.right);
            } else if (bf < -1) {
                r.right = rrRotate(r.right);
            }
        }

        r.h = height(r);
    }

    private static <T> void delete() {

    }

    private static <T> Node<T> llRotate(Node<T> root) {
        Node<T> newRoot = root.left;
        root.left = newRoot.right;
        newRoot.right = root;

        root.h = max(root.left.h, root.right.h) + 1;
        newRoot.h = max(newRoot.left.h, newRoot.right.h) + 1;
        return newRoot;
    }

    private static <T> Node<T> rrRotate(Node<T> root) {
        Node<T> newRoot = root.right;
        root.right = newRoot.left;
        newRoot.left = root;

        root.h = max(root.left.h, root.right.h) + 1;
        newRoot.h = max(newRoot.left.h, newRoot.right.h) + 1;
        return newRoot;
    }


    private static <T> Node<T> lrRotate(Node<T> root) {
        Node<T> lc = root.left;
        Node<T> tmp = lc.right;
        lc.right = root.right;
        root.right = tmp;

        lc.h = max(lc.left.h, lc.right.h);
        root.h = max(root.left.h, root.right.h);
        return root;
    }

    private static <T> Node<T> rlRotate(Node<T> root) {
        Node<T> rc = root.right;
        Node<T> tmp = rc.left;
        rc.left = root.left;
        root.left = tmp;

        rc.h = max(rc.left.h, rc.right.h);
        root.h = max(root.right.h, root.right.h);
        return root;
    }

    private static <T> int height(Node<T> n) {
        if (n == null) {
            return 0;
        }
        int lh = n.left == null ? 0 : n.left.h;
        int rh = n.right == null ? 0 : n.right.h;
        return max(lh, rh) + 1;
    }

    private static <T> int balanceFactor(Node<T> n) {
        int lh = n.left == null ? 0 : n.left.h;
        int rh = n.right == null ? 0 : n.right.h;
        return lh - rh;
    }

    private static int max(int a, int b) {
        return a > b ? a : b;
    }
}
