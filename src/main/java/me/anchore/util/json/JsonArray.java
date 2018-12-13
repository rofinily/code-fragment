package me.anchore.util.json;

/**
 * @author anchore
 * @date 2018/10/19
 */
public interface JsonArray extends Json {

    <J extends Json> void add(J j);

    <J extends Json> J get(int index);

    <J extends Json> J set(int index, J j);

    int length();

    @Override
    default Type getType() {
        return Type.ARRAY;
    }
}