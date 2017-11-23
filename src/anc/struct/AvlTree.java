package anc.struct;

import java.util.Comparator;
import java.util.Random;
import java.util.stream.Stream;

/**
 * @author anchore
 * @date 2017/11/21
 */
public class AvlTree<T> {
    NodeImpl<T> treeRoot;
    Comparator<T> c;

    /**
     * handle the always value-reference mechanism of method's parameter in Java
     */
    NodeImpl<T> tmp;

    public AvlTree(Comparator<T> c) {
        this.c = c;
    }

    void insert(T v) {
        NodeImpl<T> n = new NodeImpl<>(v);
        n.thiz = n;
        if (treeRoot == null) {
            treeRoot = n;
            return;
        }
        insert(treeRoot, n, c);
    }

    public static void main(String[] args) {
        Random r = new Random();
        AvlTree<Integer> avl = new AvlTree<>(Integer::compareTo);
        Integer[] integers = Stream.generate(() -> r.nextInt(10)).limit(15).distinct().toArray(Integer[]::new);
        Stream.of(integers).forEach(avl::insert);
        System.out.println();
    }


    private static <T> void insert(NodeImpl<T> r, NodeImpl<T> n, Comparator<T> c) {
        int cmp = c.compare(r.v, n.v);
        if (cmp > 0) {
            if (r.left == null) {
                r.left = n;
                r.h = height(r);
                return;
            }
            insert(r.left, n, c);
            int bf = balanceFactor(r.left);
            if (bf == 2) {
                r.left = llRotate(r.left);
            } else if (bf == -2) {
                r.left = lrRotate(r.left);
            }
        } else {
            if (r.right == null) {
                r.right = n;
                r.h = height(r);
                return;
            }
            insert(r.right, n, c);
            int bf = balanceFactor(r.right);
            if (bf == 2) {
                r.right = rlRotate(r.right);
            } else if (bf == -2) {
                r.right = rrRotate(r.right);
            }
        }

        r.h = height(r);

        int bf = balanceFactor(r);
        if (bf == 2) {
            int lbf = balanceFactor(r.left);
            if (lbf == 1) {
                r.thiz = llRotate(r);
            } else if (lbf == -1) {
                r.thiz = lrRotate(r);
            }
        } else if (bf == -2) {
            int rbf = balanceFactor(r.right);
            if (rbf == 1) {
                r.thiz = rlRotate(r);
            } else if (rbf == -1) {
                r.thiz = rrRotate(r);
            }
        }

        r.h = height(r);
    }

    private static <T> void delete() {

    }

    private static <T> NodeImpl<T> llRotate(NodeImpl<T> root) {
        if (root == null) {
            return null;
        }
        NodeImpl<T> newRoot = root.left;
        root.left = newRoot.right;
        newRoot.right = root;

        root.h = height(root);
        newRoot.h = height(newRoot);
        return newRoot;
    }

    private static <T> NodeImpl<T> rrRotate(NodeImpl<T> root) {
        if (root == null) {
            return null;
        }
        NodeImpl<T> newRoot = root.right;
        root.right = newRoot.left;
        newRoot.left = root;

        root.h = height(root);
        newRoot.h = height(newRoot);
        return newRoot;
    }


    private static <T> NodeImpl<T> lrRotate(NodeImpl<T> root) {
        if (root == null) {
            return null;
        }
        NodeImpl<T> lc = root.left;
        NodeImpl<T> tmp = lc.right;
        lc.right = root.right;
        root.right = tmp;

        lc.h = height(lc);
        root.h = height(root);
        return root;
    }

    private static <T> NodeImpl<T> rlRotate(NodeImpl<T> root) {
        if (root == null) {
            return null;
        }
        NodeImpl<T> rc = root.right;
        NodeImpl<T> tmp = rc.left;
        rc.left = root.left;
        root.left = tmp;

        rc.h = height(rc);
        root.h = height(root);
        return root;
    }

    private static <T> int height(NodeImpl<T> n) {
        if (n == null) {
            return 0;
        }
        int lh = n.left == null ? 0 : n.left.h;
        int rh = n.right == null ? 0 : n.right.h;
        return max(lh, rh) + 1;
    }

    private static <T> int balanceFactor(NodeImpl<T> n) {
        if (n == null) {
            return 0;
        }
        int lh = n.left == null ? 0 : n.left.h;
        int rh = n.right == null ? 0 : n.right.h;
        return lh - rh;
    }

    private static int max(int a, int b) {
        return a > b ? a : b;
    }


    static class NodeImpl<T> implements Node<T> {
        T v;
        int h = 1;
        Node<T> left;
        Node<T> right;

        Node<T> thiz;

        NodeImpl(T v) {
            this.v = v;
            thiz = this;
        }

        @Override
        public T value() {
            return thiz.v;
        }

        @Override
        public void value(T t) {
            thiz.v = t;
        }

        @Override
        public int height() {
            return thiz.h;
        }

        @Override
        public void height(int h) {
            thiz.h = h;
        }

        @Override
        public Node<T> left() {
            return thiz.left.thiz;
        }

        @Override
        public void left(Node<T> node) {
            thiz.left.thiz = node;
        }

        @Override
        public Node<T> right() {
            return null;
        }

        @Override
        public void right(Node<T> node) {

        }

        @Override
        public String toString() {
            return "Node{" +
                    "v=" + thiz.v +
                    ", h=" + thiz.h +
                    '}';
        }
    }

    interface Node<T> {
        T value();

        void value(T t);

        int height();

        void height(int h);

        Node<T> left();

        void left(Node<T> node);

        Node<T> right();

        void right(Node<T> node);

        @Override
        String toString();
    }
}
