package app.qrme.lib.exception;

public class SCException extends RuntimeException {

    public SCException() {
    }

    public SCException(String message) {
        super(message);
    }

    public SCException(String message, Throwable cause) {
        super(message, cause);
    }

    public SCException(Throwable cause) {
        super(cause);
    }
}
