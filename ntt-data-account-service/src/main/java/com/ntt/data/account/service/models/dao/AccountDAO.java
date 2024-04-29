package com.ntt.data.account.service.models.dao;

import com.ntt.data.common.module.models.service.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountDAO extends JpaRepository<Account, Long> {
}
