package me.anchore.io.wal;

public interface Meta {

    Type getType(int index);

    int getColumnCount();

    enum Type {
        BYTE, INT, LONG, DOUBLE, STRING
    }
}
