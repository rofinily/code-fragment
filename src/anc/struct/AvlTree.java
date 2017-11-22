package anc.struct;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
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
    Node<T> tmp;

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
        Stream.generate(() -> r.nextInt(10)).limit(15).distinct().forEach(avl::insert);
        avl.bfsTraversal();
    }


    private static <T> void insert(NodeWrapper<T> r, Node<T> n, Comparator<T> c) {
        int cmp = c.compare(r.node.v, n.v);
        if (cmp > 0) {
            if (r.node.left == null) {
                r.node.left = n;
                r.node.h = height(r.node);
                return;
            }
            insert(new NodeWrapper<>(r.node.left), n, c);
            int bf = balanceFactor(r.node.left);
            if (bf > 1) {
                r.node.left = llRotate(r.node.left);
            } else if (bf < -1) {
                r.node.left = lrRotate(r.node.left);
            }
        } else {
            if (r.node.right == null) {
                r.node.right = n;
                r.node.h = height(r);
                return;
            }
            insert(r.node.right, n, c);
            int bf = balanceFactor(r.node.right);
            if (bf > 1) {
                r.node.right = rlRotate(r.node.right);
            } else if (bf < -1) {
                r.node.right = rrRotate(r.node.right);
            }
        }

        r.node.h = height(r);
        int bf = balanceFactor(r);
        if (bf > 1) {
            int lbf = balanceFactor(r.node.left);
            if (lbf > 1) {
                r = llRotate(r);
            } else if (lbf < -1) {
                r = lrRotate(r);
            }
        } else if (bf < -1) {
            int rbf = balanceFactor(r.node.right);
            if (rbf > 1) {
                r = rlRotate(r);
            } else if (rbf < -1) {
                r = rrRotate(r);
            }
        }
        r.node.h = height(r);
    }

    private static <T> void delete() {

    }

    private static <T> Node<T> llRotate(Node<T> root) {
        Node<T> newRoot = root.left;
        root.left = newRoot.right;
        newRoot.right = root;

        root.h = height(root);
        newRoot.h = height(newRoot);
        return newRoot;
    }

    private static <T> Node<T> rrRotate(Node<T> root) {
        Node<T> newRoot = root.right;
        root.right = newRoot.left;
        newRoot.left = root;

        root.h = height(root);
        newRoot.h = height(newRoot);
        return newRoot;
    }


    private static <T> Node<T> lrRotate(Node<T> root) {
        Node<T> lc = root.left;
        Node<T> tmp = lc.right;
        lc.right = root.right;
        root.right = tmp;

        lc.h = height(lc);
        root.h = height(root);
        return root;
    }

    private static <T> Node<T> rlRotate(Node<T> root) {
        Node<T> rc = root.right;
        Node<T> tmp = rc.left;
        rc.left = root.left;
        root.left = tmp;

        rc.h = height(rc);
        root.h = height(root);
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

    void bfsTraversal() {
        Queue<Node<T>> q = new LinkedList<>();
        q.add(treeRoot);
        while (q.size() != 0) {
            Node<T> n = q.poll();
            if (n.left != null) {
                q.add(n.left);
            }
            if (n.right != null) {
                q.add(n.right);
            }
            System.out.printf("%s ", n.v);
        }
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

    static class NodeWrapper<T> {
        Node<T> node;

    }
}
