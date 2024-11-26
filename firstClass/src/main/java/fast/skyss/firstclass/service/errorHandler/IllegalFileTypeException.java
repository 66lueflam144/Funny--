package fast.skyss.firstclass.service.errorHandler;

public class IllegalFileTypeException extends RuntimeException {
    public IllegalFileTypeException(String message) {
        super(message);
    }
}
