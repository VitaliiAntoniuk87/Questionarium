package questionarium.exception;

public class SqlUpdateException extends RuntimeException {

    public SqlUpdateException() {
    }

    public SqlUpdateException(String message) {
        super(message);
    }
}
