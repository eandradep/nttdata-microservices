package com.ntt.data.account.service.models.services.transaction;

import com.ntt.data.common.module.models.service.account.Transactions;

import java.util.Date;
import java.util.List;

public interface ITransactionService {

    Transactions create(Transactions transaction);


    List<Transactions> getTransactionsByAccountIdAndUserId(Date startDate, Date endDate, Long clientId);



}
