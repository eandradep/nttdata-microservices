package com.ntt.data.account.service.models.entity;

import com.ntt.data.account.service.models.enums.TransactionType;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@Hidden
@Entity
@Table(name = "transactions", schema = "account_schema")
public class Transactions implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "transactionn_type", nullable = false)
    private TransactionType type;

    @Column(name = "transaction_date", nullable = false)
    private Date date;

    @Column(name = "transaction_amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "current_balance", nullable = false)
    private BigDecimal currentBalance;

}
