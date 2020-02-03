package me.anchore.io.wal;

import java.util.Arrays;
import java.util.List;

public class ListRow implements Row {
    List<?> list;

    public ListRow(List<?> list) {
        this.list = list;
    }

    public ListRow(Object... objects) {
        this.list = Arrays.asList(objects);
    }

    @Override
    public <V> V get(int index) {
        return (V) list.get(index);
    }

    @Override
    public int size() {
        return list.size();
    }
}
