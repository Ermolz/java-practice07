package bank.service;

import bank.exception.InvalidAmountException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

// 8. PIT mutation testing
class TransferMutationTest {

    // 8. PIT mutation testing (Weak test)
    @Test
    void weakTestShouldNotThrowForRegularClient() {
        CommissionCalculator calculator = new CommissionCalculator();

        assertThatCode(() -> calculator.calculateCommission(new BigDecimal("500"), false))
                .doesNotThrowAnyException();
    }

    // 8. PIT mutation testing (Fixed strong test)
    @Test
    void shouldReturnFixedCommissionForRegularSmallTransfer() {
        CommissionCalculator calculator = new CommissionCalculator();

        BigDecimal commission = calculator.calculateCommission(new BigDecimal("500"), false);

        assertThat(commission).isEqualByComparingTo("5");
    }

    @Test
    void shouldReturnZeroCommissionForVipClient() {
        CommissionCalculator calculator = new CommissionCalculator();

        BigDecimal commission = calculator.calculateCommission(new BigDecimal("500"), true);

        assertThat(commission).isEqualByComparingTo("0");
    }

    @Test
    void shouldReturnPercentCommissionForLargeTransfer() {
        CommissionCalculator calculator = new CommissionCalculator();

        BigDecimal commission = calculator.calculateCommission(new BigDecimal("1000"), false);

        assertThat(commission).isEqualByComparingTo("20.00");
    }

    @Test
    void shouldThrowExceptionForZeroOrNegativeAmount() {
        CommissionCalculator calculator = new CommissionCalculator();

        assertThatThrownBy(() -> calculator.calculateCommission(BigDecimal.ZERO, false))
                .isInstanceOf(InvalidAmountException.class)
                .hasMessage("Amount must be positive");
    }
}
