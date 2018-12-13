package me.anchore.util.json;

/**
 * @author anchore
 * @date 2018/10/19
 */
public interface Json {

    Type getType();

    enum Type {
        //
        BOOLEAN, NUMBER, STRING, OBJECT, ARRAY
    }

    @Override
    String toString();
}