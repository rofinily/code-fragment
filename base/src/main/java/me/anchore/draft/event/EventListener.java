package me.anchore.draft.event;

/**
 * @author anchore
 * @date 12/4/2018
 */
public interface EventListener<D> {

    void on(D data);
}