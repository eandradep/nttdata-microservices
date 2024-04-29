package com.ntt.data.account.service.models.services.transaction;

import com.ntt.data.common.module.models.service.account.Transactions;

import java.util.List;

public interface ITransactionService {

    Transactions create(Transactions transaction);

    List<Transactions> getTransactionsByAccountId(Long accountId);

    List<Transactions> getTransactionsByUserId(Long userId);

    List<Transactions> getTransactionsByAccountIdAndUserId(Long accountId, Long userId);

}
