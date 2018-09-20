package me.anchore.algorithm;

/**
 * @author anchore
 * @date 2017/11/7
 */
public class UnionFind {
    class Node {
        Node parent;
        int rank;
    }

    static Node findRoot(Node n) {
        while (n.parent != n) {
            n = n.parent;
        }
        return n;
    }

    static void join(Node m, Node n) {
        Node mr = findRoot(m),
                nr = findRoot(n);
        if (mr != nr) {
            nr.parent = mr;
        }
    }
}
