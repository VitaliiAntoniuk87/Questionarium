package questionarium.exception;

public class SqlRemovalException extends RuntimeException {

    public SqlRemovalException() {
    }

    public SqlRemovalException(String message) {
        super(message);
    }
}
