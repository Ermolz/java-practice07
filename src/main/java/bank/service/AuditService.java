package bank.service;

import java.math.BigDecimal;

public interface AuditService {
    // 1. Class Structure
    // 2. Main Classes
    void logTransfer(String fromId, String toId, BigDecimal amount);
    void logFailedTransfer(String fromId, String toId, String reason);
}
