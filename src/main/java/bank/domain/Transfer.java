package bank.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class Transfer {
    // 1. Class Structure
    // 7. AssertJ list assertions
    private final String fromId;
    private final String toId;
    private final BigDecimal amount;
    private final String status;
}
