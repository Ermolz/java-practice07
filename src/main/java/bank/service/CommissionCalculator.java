package bank.service;

import bank.exception.InvalidAmountException;
import java.math.BigDecimal;

public class CommissionCalculator implements CommissionService {

    @Override
    public BigDecimal calculateCommission(BigDecimal amount, boolean vipClient) {
        // 1. Class Structure
        // 8. PIT mutation testing
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("Amount must be positive");
        }

        if (vipClient) {
            return BigDecimal.ZERO;
        } else if (amount.compareTo(new BigDecimal("1000")) >= 0) {
            return amount.multiply(new BigDecimal("0.02"));
        } else {
            return new BigDecimal("5");
        }
    }
}
