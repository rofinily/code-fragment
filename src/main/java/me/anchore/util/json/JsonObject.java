package me.anchore.util.json;

/**
 * @author anchore
 * @date 2018/10/19
 */
public interface JsonObject extends Json {

    <J extends Json> J getProperty(String name);

    <J extends Json> J setProperty(String name, J j);

    @Override
    default Type getType() {
        return Type.OBJECT;
    }
}