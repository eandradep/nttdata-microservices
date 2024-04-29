package com.ntt.data.account.service.controller;

import com.ntt.data.account.service.models.dto.CreateTransactionDTO;
import com.ntt.data.account.service.models.services.account.IAccountService;
import com.ntt.data.account.service.models.services.transaction.ITransactionService;
import com.ntt.data.common.module.enums.TransactionType;
import com.ntt.data.common.module.models.service.account.Account;
import com.ntt.data.common.module.models.service.account.Transactions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movimientos")
public class TransactionController {

    private final ITransactionService transactionService;
    private final ModelMapper modelMapper;
    private final IAccountService accountService;

    @Autowired
    public TransactionController(
            ITransactionService transactionService,
            ModelMapper modelMapper,
            IAccountService accountService) {
        this.transactionService = transactionService;
        this.modelMapper = modelMapper;
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<?> registerTransaction(
            @Valid @RequestBody CreateTransactionDTO createTransactionDTO, BindingResult result) {
        Transactions newTransaction;
        Map<String, Object> response = new HashMap<>();
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + " " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            if (createTransactionDTO.getAmount().compareTo(BigDecimal.ZERO) == 0) {
                response.put("message", "El valor de la transaction debe ser diferente de cero");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            Transactions transactions = modelMapper.map(createTransactionDTO, Transactions.class);
            Account account = this.accountService.getAccountByNumber(createTransactionDTO.getNumber());
            BigDecimal newInitialBalance;
            newInitialBalance = getBigDecimal(createTransactionDTO, account, transactions);
            if (newInitialBalance.compareTo(BigDecimal.ZERO) < 0) {
                response.put("message", "Saldo no disponible");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            account.setInitialBalance(newInitialBalance);
            this.accountService.saveAccount(account);
            account.setClient(null);
            transactions.setCurrentBalance(newInitialBalance);
            transactions.setDate(new Date());
            transactions.setAccount(account);
            newTransaction = transactionService.create(transactions);

            response.put("message", "Datos registrados correctamente!");
            response.put("result", newTransaction);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static BigDecimal getBigDecimal(CreateTransactionDTO createTransactionDTO, Account account, Transactions transactions) {
        BigDecimal newInitialBalance;
        if (createTransactionDTO.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            newInitialBalance = account.getInitialBalance().subtract(createTransactionDTO.getAmount().abs());
            transactions.setType(TransactionType.RETIRO);
        } else {
            newInitialBalance = account.getInitialBalance().add(createTransactionDTO.getAmount());
            transactions.setType(TransactionType.DEPOSITO);
        }
        return newInitialBalance;
    }

}
