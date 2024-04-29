package com.ntt.data.account.service.models.services.account;

import com.ntt.data.common.module.models.service.account.Account;

import java.util.List;

public interface IAccountService {

    Account getAccountById(Long accountId);

    Account saveAccount(Account account);

    List<Account> getAllAccounts();

    Account getAccountByNumber(String number);

    List<Account> getAccountByClientId(Long clientId);
}
