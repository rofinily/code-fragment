package me.anchore.io.wal;

public class MetaImpl implements Meta {
    Type[] types = {Type.BYTE, Type.LONG, Type.STRING, Type.DOUBLE, Type.INT};

    @Override
    public Type getType(int index) {
        return types[index];
    }

    @Override
    public int getColumnCount() {
        return types.length;
    }
}
