package com.ntt.data.account.service.models.dto;

import com.ntt.data.common.module.enums.AccountType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
public class UpdateAccountDTO implements Serializable {

    @NotNull(message = "Account type must not be null")
    private AccountType type;

    @NotNull(message = "InitialBalance must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "InitialBalance must be greater than 0")
    private BigDecimal initialBalance;
}
