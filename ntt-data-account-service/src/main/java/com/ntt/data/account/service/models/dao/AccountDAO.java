package com.ntt.data.account.service.models.dao;

import com.ntt.data.common.module.models.service.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountDAO extends JpaRepository<Account, Long> {
    Optional<Account> findByNumber(String accountNumber);
    List<Account> findAccountsByClientClientId(Long clientId);
}
