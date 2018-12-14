package me.anchore.struct;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

/**
 * @author anchore
 * @date 2017/11/21
 */
public class AvlTree<V> {
    NodeKey treeRoot = new NodeKey();
    Comparator<V> c;

    Map<NodeKey, Node> nodes = new HashMap<>();

    public AvlTree(Comparator<V> c) {
        this.c = c;
    }

    public static void main(String[] args) {
        Random r = new Random();
        AvlTree<Integer> avl = new AvlTree<>(Integer::compareTo);
        Integer[] integers = Stream.generate(() -> r.nextInt(15)).distinct().limit(10).toArray(Integer[]::new);
        Stream.of(integers).forEach(avl::insert);
        System.out.println();
    }

    private static <T> void delete() {

    }

    private static int max(int a, int b) {
        return a > b ? a : b;
    }

    void insert(V v) {
        if (!has(treeRoot)) {
            Node n = new Node(treeRoot, v);
            put(treeRoot, n);
            return;
        }
        Node n = new Node(new NodeKey(), v);
        insert(get(treeRoot), n);
    }

    private void insert(Node r, Node n) {
        int cmp = c.compare(r.value(), n.value());
        if (cmp > 0) {
            if (!r.hasLeft()) {
                r.setLeft(n);
                r.setHeight(height(r));
                return;
            }
            insert(r.left(), n);
            int bf = balanceFactor(r.left());
            if (bf == 2) {
                r.setLeft(llRotate(r.left()));
            } else if (bf == -2) {
                r.setLeft(lrRotate(r.left()));
            }
        } else {
            if (!r.hasRight()) {
                r.setRight(n);
                r.setHeight(height(r));
                return;
            }
            insert(r.right(), n);
            int bf = balanceFactor(r.right());
            if (bf == 2) {
                r.setRight(rlRotate(r.right()));
            } else if (bf == -2) {
                r.setRight(rrRotate(r.right()));
            }
        }

        r.setHeight(height(r));

        int bf = balanceFactor(r);
        if (bf == 2) {
            int lbf = balanceFactor(r.left());
            if (lbf == 1) {
                r.setSelf(llRotate(r));
            } else if (lbf == -1) {
                r.setSelf(lrRotate(r));
            }
        } else if (bf == -2) {
            int rbf = balanceFactor(r.right());
            if (rbf == 1) {
                r.setSelf(rlRotate(r));
            } else if (rbf == -1) {
                r.setSelf(rrRotate(r));
            }
        }

        r.setHeight(height(r));
    }

    private Node llRotate(Node root) {
        if (root == null) {
            return null;
        }
        Node newRoot = root.left();
        root.setLeft(newRoot.right());
        newRoot.setRight(root);

        root.setHeight(height(root));
        newRoot.setHeight(height(newRoot));

        return newRoot;
    }

    private Node rrRotate(Node root) {
        if (root == null) {
            return null;
        }
        Node newRoot = root.right();
        root.setRight(newRoot.left());
        newRoot.setRight(root);

        root.setHeight(height(root));
        newRoot.setHeight(height(newRoot));

        return newRoot;
    }

    private Node lrRotate(Node root) {
        if (root == null) {
            return null;
        }
        Node lc = root.left();
        Node tmp = lc.right();
        lc.setRight(root.right());
        root.setRight(tmp);

        lc.setHeight(height(lc));
        root.setHeight(height(root));

        return root;
    }

    private Node rlRotate(Node root) {
        if (root == null) {
            return null;
        }
        Node rc = root.right();
        Node tmp = rc.left();
        rc.setLeft(root.left());
        root.setLeft(tmp);

        rc.setHeight(height(rc));
        root.setHeight(height(root));

        return root;
    }

    private int height(Node n) {
        if (n == null) {
            return 0;
        }
        int lh = n.hasLeft() ? n.left().height() : 0;
        int rh = n.hasRight() ? n.right().height() : 0;
        return Math.max(lh, rh) + 1;
    }

    private int balanceFactor(Node n) {
        if (n == null) {
            return 0;
        }
        int lh = n.hasLeft() ? n.left().height() : 0;
        int rh = n.hasRight() ? n.right().height() : 0;
        return lh - rh;
    }

    boolean has(NodeKey nodeKey) {
        return nodes.containsKey(nodeKey);
    }

    Node get(NodeKey nodeKey) {
        return nodes.get(nodeKey);
    }

    void put(NodeKey nodeKey, Node node) {
        nodes.put(nodeKey, node);
    }

    static class NodeKey {
        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }
    }

    class Node {
        V value;
        int height = 1;
        NodeKey self;
        NodeKey left;
        NodeKey right;

        Node(NodeKey nodeKey, V value) {
            this.value = value;
//            this.nodeKey = nodeKey;
            left = new NodeKey();
            right = new NodeKey();
        }

        V value() {
            return value;
        }

        NodeKey key() {
            return self;
        }

        void setValue(V value) {
            this.value = value;
        }

        int height() {
            return height;
        }

        void setHeight(int height) {
            this.height = height;
        }

        void setSelf(Node node) {
            self = node.key();
        }

        boolean hasLeft() {
            return has(left) && get(left) != null;
        }

        Node left() {
            return get(left);
        }

        void setLeft(Node node) {
            left = node.key();
        }

        boolean hasRight() {
            return has(right) && get(right) != null;
        }

        Node right() {
            return get(right);
        }

        void setRight(Node node) {
            right = node.key();
        }

        @Override
        public String toString() {
            return "{ v=" + value +
                    ", h=" + height +
                    " }";
        }
    }
}