package bank.exception;

public class InvalidAmountException extends TransferException {
    public InvalidAmountException(String message) {
        super(message);
    }
}
