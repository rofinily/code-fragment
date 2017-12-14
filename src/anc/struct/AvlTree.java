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

    /**
     * handle the always value-reference mechanism of method's parameter in Java
     */
    NodeImpl<T> tmp;

    public AvlTree(Comparator<T> c) {
        this.c = c;
    }

    void insert(T v) {
        Node<T> n = new NodeImpl<>(v);
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


    private static <T> void insert(Node<T> r, Node<T> n, Comparator<T> c) {
        int cmp = c.compare(r.value(), n.value());
        if (cmp > 0) {
            if (r.left() == null) {
                r.left(n);
                r.height(height(r));
                return;
            }
            insert(r.left(), n, c);
            int bf = balanceFactor(r.left());
            if (bf == 2) {
                r.left(llRotate(r.left()));
            } else if (bf == -2) {
                r.left(lrRotate(r.left()));
            }
        } else {
            if (r.right() == null) {
                r.right(n);
                r.height(height(r));
                return;
            }
            insert(r.right(), n, c);
            int bf = balanceFactor(r.right());
            if (bf == 2) {
                r.right(rlRotate(r.right()));
            } else if (bf == -2) {
                r.right(rrRotate(r.right()));
            }
        }

        r.height(height(r));

        int bf = balanceFactor(r);
        if (bf == 2) {
            int lbf = balanceFactor(r.left());
            if (lbf == 1) {
                r.self(llRotate(r));
            } else if (lbf == -1) {
                r.self(lrRotate(r));
            }
        } else if (bf == -2) {
            int rbf = balanceFactor(r.right());
            if (rbf == 1) {
                r.self(rlRotate(r));
            } else if (rbf == -1) {
                r.self(rrRotate(r));
            }
        }

        r.height(height(r));
    }

    private static <T> void delete() {

    }

    private static <T> Node<T> llRotate(Node<T> root) {
        if (root == null) {
            return null;
        }
        Node<T> newRoot = root.left();
        root.left(newRoot.right());
        newRoot.right(root);

        root.height(height(root));
        newRoot.height(height(newRoot));
        return newRoot;
    }

    private static <T> Node<T> rrRotate(Node<T> root) {
        if (root == null) {
            return null;
        }
        Node<T> newRoot = root.right();
        root.right(newRoot.left());
        newRoot.left(root);

        root.height(height(root));
        newRoot.height(height(newRoot));
        return newRoot;
    }


    private static <T> Node<T> lrRotate(Node<T> root) {
        if (root == null) {
            return null;
        }
        Node<T> lc = root.left();
        Node<T> tmp = lc.right();
        lc.right(root.right());
        root.right(tmp);

        lc.height(height(lc));
        root.height(height(root));
        return root;
    }

    private static <T> Node<T> rlRotate(Node<T> root) {
        if (root == null) {
            return null;
        }
        Node<T> rc = root.right();
        Node<T> tmp = rc.left();
        rc.left(root.left());
        root.left(tmp);

        rc.height(height(rc));
        root.height(height(root));
        return root;
    }

    private static <T> int height(Node<T> n) {
        if (n == null) {
            return 0;
        }
        int lh = n.left() == null ? 0 : n.left().height();
        int rh = n.right() == null ? 0 : n.right().height();
        return max(lh, rh) + 1;
    }

    private static <T> int balanceFactor(Node<T> n) {
        if (n == null) {
            return 0;
        }
        int lh = n.left() == null ? 0 : n.left().height();
        int rh = n.right() == null ? 0 : n.right().height();
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
        public Node<T> self() {
            return thiz;
        }

        @Override
        public void self(Node<T> node) {
            thiz = node.self();
        }

        @Override
        public T value() {
            return self().value();
        }

        @Override
        public void value(T t) {
            self().value(t);
        }

        @Override
        public int height() {
            return self().height();
        }

        @Override
        public void height(int h) {
            self().height(h);
        }

        @Override
        public Node<T> left() {
            return self().left().self();
        }

        @Override
        public void left(Node<T> node) {
            self().left().self(node);
        }

        @Override
        public Node<T> right() {
            return self().right().self();
        }

        @Override
        public void right(Node<T> node) {
            self().right().self(node);
        }

        @Override
        public String toString() {
            return "{ v=" + self().value() +
                    ", h=" + self().height() +
                    " }";
        }
    }

    interface Node<T> {
        Node<T> self();

        void self(Node<T> node);

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
