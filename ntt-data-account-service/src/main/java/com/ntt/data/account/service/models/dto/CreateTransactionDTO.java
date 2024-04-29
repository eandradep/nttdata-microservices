package com.ntt.data.account.service.models.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
public class CreateTransactionDTO implements Serializable {

    @NotNull(message = "Amount must not be null")
    private BigDecimal amount;

    @NotBlank(message = "Number must not be blank")
    @Size(max = 20, message = "Number must be at most 20 characters")
    private String number;
}
