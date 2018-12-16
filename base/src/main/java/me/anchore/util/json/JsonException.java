package me.anchore.util.json;

/**
 * @author anchore
 * @date 2018/12/16
 */
public class JsonException extends Exception {

    public JsonException() {
    }

    public JsonException(String message) {
        super(message);
    }

    public JsonException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsonException(Throwable cause) {
        super(cause);
    }
}