package bank.service;

import java.math.BigDecimal;

public interface CommissionService {
    // 1. Class Structure
    // 2. Main Classes
    BigDecimal calculateCommission(BigDecimal amount, boolean isVip);
}
