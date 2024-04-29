package com.ntt.data.common.module.models.service.account;

import com.ntt.data.common.module.enums.AccountType;
import com.ntt.data.common.module.models.service.client.Client;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
@Entity
@Table(name = "account", schema = "account_schema")
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(name = "number", unique = true, length = 20, nullable = false)
    private String number;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private AccountType type;

    @Column(name = "initial_balance", nullable = false)
    private BigDecimal initialBalance;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    public void changeStatus() {
        this.status = !this.status;
    }
}
