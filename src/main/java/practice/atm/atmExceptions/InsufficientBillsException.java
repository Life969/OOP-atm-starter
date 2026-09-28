package practice.atm.atmExceptions;

public class InsufficientBillsException extends AtmException {
    public InsufficientBillsException(String message) {
        super(message);
    }
    public InsufficientBillsException(String message, Throwable cause) {
        super(message, cause);
    }
}
