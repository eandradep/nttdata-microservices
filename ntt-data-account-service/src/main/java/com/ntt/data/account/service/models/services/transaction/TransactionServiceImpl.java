package com.ntt.data.account.service.models.services.transaction;

import com.ntt.data.account.service.models.dao.TransactionDAO;
import com.ntt.data.common.module.models.service.account.Transactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TransactionServiceImpl implements ITransactionService{

    private final TransactionDAO transactionsDAO;

    @Autowired
    public TransactionServiceImpl(TransactionDAO transactionsDAO) {
        this.transactionsDAO = transactionsDAO;
    }


    @Override
    public Transactions create(Transactions transaction) {
        return this.transactionsDAO.save(transaction);
    }

    @Override
    public List<Transactions> getTransactionsByAccountIdAndUserId(Date startDate, Date endDate, Long clientId) {
        System.out.println("startDate:"+startDate+"endDate:"+endDate+"clientId:"+clientId);
        return this.transactionsDAO.findTransactionsByAccountClientClientId(clientId);
    }


}
