package com.ntt.data.account.service.controller;

import com.ntt.data.account.service.models.services.account.IAccountService;
import com.ntt.data.account.service.models.services.transaction.ITransactionService;
import com.ntt.data.common.module.models.service.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.QueryParam;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/report")
public class ReportController {
    private final ITransactionService transactionService;
    private final IAccountService accountService;

    @Autowired
    public ReportController(ITransactionService transactionService, IAccountService accountService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    @GetMapping("/reportes")
    public ResponseEntity<?> getReportes(@RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaInicio,
                                         @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaFin,
                                         @QueryParam("clientId") Long clientId) {

        // Aquí puede llamar al método de su servicio que ejecuta la lógica del negocio y le proporciona los datos del informe.
        Map<String, Object> response = new HashMap<>();

        List<Account> x =  accountService.getAccountByClientId(clientId);
        response.put("result", x);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
