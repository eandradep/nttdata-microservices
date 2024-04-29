package com.ntt.data.account.service.models.services.account;

import com.ntt.data.common.module.models.service.account.Account;

import java.util.List;

public interface IAccountService {

    Account getAccount(Long accountId);

    Account createAccount(Account account);

    List<Account> getAllAccounts();
}
