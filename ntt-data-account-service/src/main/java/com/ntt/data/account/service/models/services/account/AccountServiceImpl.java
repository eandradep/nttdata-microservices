package com.ntt.data.account.service.models.services.account;

import com.ntt.data.account.service.models.dao.AccountDAO;
import com.ntt.data.common.module.models.service.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements IAccountService {

    private final AccountDAO accountDAO;

    @Autowired
    public AccountServiceImpl(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Override
    public Account getAccountById(Long accountId) {
        return this.accountDAO.findById(accountId).orElse(null);
    }

    @Override
    public Account saveAccount(Account account) {
        return this.accountDAO.save(account);
    }

    @Override
    public List<Account> getAllAccounts() {
        return this.accountDAO.findAll();
    }
    @Override
    public Account getAccountByNumber(String number) {
        return this.accountDAO.findByNumber(number).orElse(null);
    }

    @Override
    public List<Account> getAccountByClientId(Long clientId) {
        return this.accountDAO.findAccountsByClientClientId(clientId);
    }
}
