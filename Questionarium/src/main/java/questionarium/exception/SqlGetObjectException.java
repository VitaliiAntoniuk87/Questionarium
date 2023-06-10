package questionarium.exception;

public class SqlGetObjectException extends RuntimeException {

    public SqlGetObjectException() {
    }

    public SqlGetObjectException(String message) {
        super(message);
    }
}
