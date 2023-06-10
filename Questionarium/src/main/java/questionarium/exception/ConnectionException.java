package questionarium.exception;

public class ConnectionException extends RuntimeException {

    public ConnectionException() {
    }

    public ConnectionException(String message) {
        super(message);
    }
}
