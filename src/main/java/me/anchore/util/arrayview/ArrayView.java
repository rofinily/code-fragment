package me.anchore.util.arrayview;

import java.util.function.Consumer;

/**
 * @author anchore
 * @date 2018/10/16
 */
public interface ArrayView<T> {

    T get(int index);

    T set(int index, T t);

    int length();

    default void forEach(Consumer<T> consumer) {
        for (int i = 0; i < length(); i++) {
            consumer.accept(get(i));
        }
    }
}