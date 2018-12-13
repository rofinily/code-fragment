package me.anchore.util.json;

/**
 * @author anchore
 * @date 2018/10/19
 */
public interface JsonBoolean extends Json {

    Boolean get();

    @Override
    default Type getType() {
        return Type.BOOLEAN;
    }
}