package bank.service;

import bank.domain.Account;
import bank.exception.AccountNotFoundException;
import bank.exception.InsufficientFundsException;
import bank.exception.InvalidAmountException;
import bank.repository.AccountRepository;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

// 4. Mockito: mandatory usage
@ExtendWith(MockitoExtension.class)
class TransferServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CommissionService commissionService;

    @Mock
    private AuditService auditService;

    @InjectMocks
    private TransferService transferService;

    // 3. Minimum 3 business scenarios (Scenario 1 - successful transfer)
    // 5. Void methods: verify, times, never
    // 6. AssertJ SoftAssertions
    @Test
    void shouldTransferSuccessfully() {
        Account from = new Account("A1", new BigDecimal("1000"), false);
        Account to = new Account("A2", new BigDecimal("200"), false);
        BigDecimal amount = new BigDecimal("300");

        when(accountRepository.findById("A1")).thenReturn(Optional.of(from));
        when(accountRepository.findById("A2")).thenReturn(Optional.of(to));
        when(commissionService.calculateCommission(amount, false)).thenReturn(new BigDecimal("10"));

        transferService.transfer("A1", "A2", amount);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(from.getBalance()).isEqualByComparingTo("690");
        softly.assertThat(to.getBalance()).isEqualByComparingTo("500");
        softly.assertThat(from.getId()).isEqualTo("A1");
        softly.assertThat(to.getId()).isEqualTo("A2");
        softly.assertAll();

        verify(accountRepository, times(2)).save(any(Account.class));
        verify(auditService).logTransfer("A1", "A2", amount);
        verify(auditService, never()).logFailedTransfer(anyString(), anyString(), anyString());
    }

    // 3. Minimum 3 business scenarios (Scenario 2 - insufficient funds)
    // 5. Void methods: verify, times, never
    @Test
    void shouldFailDueToInsufficientFunds() {
        Account from = new Account("A1", new BigDecimal("100"), false);
        Account to = new Account("A2", new BigDecimal("200"), false);
        BigDecimal amount = new BigDecimal("300");

        when(accountRepository.findById("A1")).thenReturn(Optional.of(from));
        when(accountRepository.findById("A2")).thenReturn(Optional.of(to));
        when(commissionService.calculateCommission(amount, false)).thenReturn(new BigDecimal("10"));

        assertThatThrownBy(() -> transferService.transfer("A1", "A2", amount))
                .isInstanceOf(InsufficientFundsException.class)
                .hasMessage("Insufficient funds");

        verify(accountRepository, never()).save(any());
        verify(auditService).logFailedTransfer("A1", "A2", "Insufficient funds");
        verify(auditService, never()).logTransfer(anyString(), anyString(), any());
    }

    // 3. Minimum 3 business scenarios (Scenario 3 - invalid amount)
    // 5. Void methods: verify, times, never
    @Test
    void shouldFailDueToNegativeAmount() {
        BigDecimal amount = new BigDecimal("-100");

        assertThatThrownBy(() -> transferService.transfer("A1", "A2", amount))
                .isInstanceOf(InvalidAmountException.class)
                .hasMessage("Amount must be positive");

        verify(accountRepository, never()).findById(anyString());
        verify(accountRepository, never()).save(any());
        verify(auditService).logFailedTransfer("A1", "A2", "Amount must be positive");
        verify(auditService, never()).logTransfer(anyString(), anyString(), any());
    }
}
