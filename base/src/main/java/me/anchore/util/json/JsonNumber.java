package me.anchore.util.json;

/**
 * @author anchore
 * @date 2018/10/19
 */
public interface JsonNumber extends Json {

    Number get();

    @Override
    default Type getType() {
        return Type.NUMBER;
    }
}