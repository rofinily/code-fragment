package me.anchore.ioc.impl;

import me.anchore.annotation.EqAndHashBuilder;
import me.anchore.annotation.IgnoreEqAndHash;
import me.anchore.ioc.CyclicDependencyException;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author anchore
 * @date 2019/2/19
 */
public class DirectedAcyclicGraph<T> {

    private Set<Node<T>> nodes;

    public DirectedAcyclicGraph(Set<Node<T>> nodes) {
        this.nodes = nodes;
    }

    public void topoSort(Consumer<Node<T>> consumer) {
        int markedCount = 0, lastMarkedCount;
        while (true) {
            lastMarkedCount = markedCount;

            for (Node<T> node : nodes) {
                if (node.isAllSourcesMarked()) {
                    if (node.isMarked()) {
                        continue;
                    }
                    node.mark();
                    consumer.accept(node);
                    markedCount++;
                }
            }
            if (markedCount == nodes.size()) {
                return;
            }
            if (markedCount == lastMarkedCount) {
                throw new CyclicDependencyException(nodes.stream().filter(tNode -> !tNode.isMarked()).map(Node::getKey).map(Objects::toString).collect(Collectors.toSet()));
            }
        }
    }

    static class Node<Key> {
        @IgnoreEqAndHash
        private boolean marked = false;

        private Key key;

        @IgnoreEqAndHash
        private Set<Node<Key>> sources;

        Node(Key key) {
            this(key, new HashSet<>());
        }

        Node(Key key, Set<Node<Key>> sources) {
            this.key = key;
            this.sources = sources;
        }

        Key getKey() {
            return key;
        }

        void addSource(Node<Key> node) {
            sources.add(node);
        }

        void mark() {
            marked = true;
        }

        boolean isMarked() {
            return marked;
        }

        boolean isAllSourcesMarked() {
            for (Node<Key> node : sources) {
                if (!node.marked) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public int hashCode() {
            return EqAndHashBuilder.hashCode(this);
        }

        @Override
        public boolean equals(Object obj) {
            return EqAndHashBuilder.equals(this, obj);
        }

        @Override
        public String toString() {
            return key.toString();
        }
    }
}