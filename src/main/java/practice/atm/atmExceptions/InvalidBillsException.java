package practice.atm.atmExceptions;

public class InvalidBillsException extends AtmException {
    public InvalidBillsException(String message) {
        super(message);
    }
    public InvalidBillsException(String message, Throwable cause) {
        super(message, cause);
    }
}
