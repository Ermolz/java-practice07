package bank.service;

import bank.domain.Account;
import bank.exception.AccountNotFoundException;
import bank.exception.InsufficientFundsException;
import bank.exception.InvalidAmountException;
import bank.repository.AccountRepository;

import java.math.BigDecimal;

public class TransferService {

    private final AccountRepository accountRepository;
    private final CommissionService commissionService;
    private final AuditService auditService;

    public TransferService(
            AccountRepository accountRepository,
            CommissionService commissionService,
            AuditService auditService
    ) {
        // 1. Class Structure
        // 2. Main Classes
        this.accountRepository = accountRepository;
        this.commissionService = commissionService;
        this.auditService = auditService;
    }

    public void transfer(String fromId, String toId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            auditService.logFailedTransfer(fromId, toId, "Amount must be positive");
            throw new InvalidAmountException("Amount must be positive");
        }

        Account from = accountRepository.findById(fromId)
                .orElseThrow(() -> new AccountNotFoundException("Sender account not found"));

        Account to = accountRepository.findById(toId)
                .orElseThrow(() -> new AccountNotFoundException("Receiver account not found"));

        BigDecimal commission = commissionService.calculateCommission(amount, from.isVip());
        BigDecimal totalAmount = amount.add(commission);

        try {
            from.withdraw(totalAmount);
        } catch (InsufficientFundsException ex) {
            auditService.logFailedTransfer(fromId, toId, "Insufficient funds");
            throw ex;
        }

        to.deposit(amount);

        accountRepository.save(from);
        accountRepository.save(to);

        auditService.logTransfer(fromId, toId, amount);
    }
}
