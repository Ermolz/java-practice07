package bank.domain;

import bank.exception.InsufficientFundsException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class Account {
    // 1. Class Structure
    // 2. Main Classes
    private String id;
    private BigDecimal balance;
    private boolean isVip;

    public void withdraw(BigDecimal amount) {
        if (balance.compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        balance = balance.subtract(amount);
    }

    public void deposit(BigDecimal amount) {
        balance = balance.add(amount);
    }
}
