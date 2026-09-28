package practice.atm.atmExceptions;

public class InvalidAmountException extends AtmException {
    public InvalidAmountException(String message) {
        super(message);
    }
    public InvalidAmountException(String message, Throwable cause) {
        super(message, cause);
    }
}
