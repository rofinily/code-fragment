package me.anchore.io.wal;

public interface Row {
    <V> V get(int index);

    int size();
}
