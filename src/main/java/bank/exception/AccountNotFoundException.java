package bank.exception;

public class AccountNotFoundException extends TransferException {
    public AccountNotFoundException(String message) {
        super(message);
    }
}
