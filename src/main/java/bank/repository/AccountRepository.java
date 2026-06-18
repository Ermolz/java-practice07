package bank.repository;

import bank.domain.Account;
import java.util.Optional;

public interface AccountRepository {
    // 1. Class Structure
    // 2. Main Classes 
    Optional<Account> findById(String id);
    void save(Account account);
}
