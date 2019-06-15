package me.anchore.draft.daosvc;

import java.util.List;

/**
 * @author anchore
 * @date 2019/4/9
 */
public interface EntityDao<T> {
    void insert(T entity);

    default void insert(List<T> entities) {
        if (entities != null) {
            entities.forEach(this::insert);
        }
    }

    int delete();

    int update();

    List<T> select();
}