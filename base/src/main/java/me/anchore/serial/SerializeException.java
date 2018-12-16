package me.anchore.serial;

/**
 * @author anchore
 * @date 2018/12/16
 */
public class SerializeException extends Exception {

    public SerializeException() {
    }

    public SerializeException(String message) {
        super(message);
    }

    public SerializeException(String message, Throwable cause) {
        super(message, cause);
    }

    public SerializeException(Throwable cause) {
        super(cause);
    }
}