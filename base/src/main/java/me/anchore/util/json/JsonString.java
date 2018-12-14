package me.anchore.util.json;

/**
 * @author anchore
 * @date 2018/10/20
 */
public interface JsonString extends Json {

    String get();

    @Override
    default Type getType() {
        return Type.STRING;
    }
}