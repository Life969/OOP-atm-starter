package practice.atm.atmExceptions;

public class InvalidPlanException extends AtmException {
    public InvalidPlanException(String message) {
        super(message);
    }
    public InvalidPlanException(String message, Throwable cause) {
        super(message, cause);
    }

}
