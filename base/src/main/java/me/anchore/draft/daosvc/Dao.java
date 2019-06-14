package me.anchore.draft.daosvc;

import java.util.List;

/**
 * @author anchore
 * @date 2019/4/9
 */
public interface Dao<T> {
    void insert(List<T> data);

    void delete();

    void update();

    List<T> select();
}