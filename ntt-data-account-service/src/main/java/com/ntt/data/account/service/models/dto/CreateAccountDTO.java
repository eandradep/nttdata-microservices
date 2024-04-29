package com.ntt.data.account.service.models.dto;

import com.ntt.data.common.module.enums.AccountType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
public class CreateAccountDTO implements Serializable {

    @NotBlank(message = "Number must not be blank")
    @Size(max = 20, message = "Number must be at most 20 characters")
    private String number;

    @NotBlank(message = "Client Identification must not be blank")
    @Size(max = 100, message = "Client Identification must be at most 100 characters")
    private String clientIdentification;

    @NotNull(message = "Account type must not be null")
    private AccountType type;

    @NotNull(message = "InitialBalance must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "InitialBalance must be greater than 0")
    private BigDecimal initialBalance;
}
