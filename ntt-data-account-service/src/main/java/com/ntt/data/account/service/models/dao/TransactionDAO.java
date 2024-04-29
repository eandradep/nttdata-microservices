package com.ntt.data.account.service.models.dao;

import com.ntt.data.common.module.models.service.account.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionDAO extends JpaRepository<Transactions, Long> {

    List<Transactions> findTransactionsByAccountClientClientId(Long clientId);
}
