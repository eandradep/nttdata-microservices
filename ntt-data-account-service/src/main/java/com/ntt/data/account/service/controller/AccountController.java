package com.ntt.data.account.service.controller;

import com.ntt.data.account.service.models.clients.IClientFeign;
import com.ntt.data.account.service.models.dto.CreateAccountDTO;
import com.ntt.data.account.service.models.services.account.IAccountService;
import com.ntt.data.common.module.models.service.account.Account;
import com.ntt.data.common.module.models.service.client.Client;
import com.ntt.data.common.module.models.service.client.Person;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cuentas")
public class AccountController {

    private final IAccountService accountService;
    private final IClientFeign iClientFeign;
    private final ModelMapper modelMapper;

    @Autowired
    public AccountController(IAccountService accountService, ModelMapper modelMapper, IClientFeign iClientFeign) {
        this.accountService = accountService;
        this.modelMapper = modelMapper;
        this.iClientFeign= iClientFeign;
    }

    @PostMapping
    public ResponseEntity<?> saveAccount(
            @Valid @RequestBody CreateAccountDTO clientData, BindingResult result) {
        Account newAccount;
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
            Client client = this.iClientFeign.findClientByIdentification(clientData.getClientIdentification());
            if (client == null){
                response.put("mensaje", "No existe un cliente!");
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }
            Account account = modelMapper.map(clientData, Account.class);
            account.setClient(client);
            account.setStatus(true);
            newAccount = accountService.createAccount(account);
            response.put("message", "Datos registrados correctamente!");
            response.put("result", newAccount);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
