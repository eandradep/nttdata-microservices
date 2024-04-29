package com.ntt.data.account.service.controller;

import com.ntt.data.account.service.models.clients.IClientFeign;
import com.ntt.data.account.service.models.dto.CreateAccountDTO;
import com.ntt.data.account.service.models.dto.UpdateAccountDTO;
import com.ntt.data.account.service.models.services.account.IAccountService;
import com.ntt.data.common.module.models.service.account.Account;
import com.ntt.data.common.module.models.service.client.Client;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
            newAccount = accountService.saveAccount(account);
            response.put("message", "Datos registrados correctamente!");
            response.put("result", newAccount);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @Valid @RequestBody UpdateAccountDTO accountUpdate, BindingResult result, @PathVariable Long id) {
        Account clienteActual = this.accountService.getAccountById(id);
        Account clienteUpdated = null;
        Map<String, Object> response = new HashMap<>();
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        if (clienteActual == null) {
            response.put("mensaje", "Error: no se pudo editar, la cuenta con ID: "
                    .concat(id.toString().concat(" no existe en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
            clienteActual.setInitialBalance(accountUpdate.getInitialBalance());
            clienteActual.setType(accountUpdate.getType());
            clienteUpdated = this.accountService.saveAccount(clienteActual);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar la cuenta en la base de datos");
            response.put("error", Objects.requireNonNull(e.getMessage()).concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "La cuenta ha sido actualizado con éxito!");
        response.put("cliente", clienteUpdated);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        Account currentAccount = this.accountService.getAccountById(id);
        if (currentAccount == null) {
            response.put("mensaje", "Error: no se pudo eliminar, la cuenta con ID: "
                    .concat(id.toString().concat(" no existe en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        if (!currentAccount.getStatus()) {
            response.put("mensaje", "Error: no se pudo eliminar, la cuenta con ID: "
                    .concat(id.toString().concat(", la cuenta ya se ha eliminada!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
            currentAccount.changeStatus();
            this.accountService.saveAccount(currentAccount);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar la cuenta de la base de datos");
            response.put("error", Objects.requireNonNull(e.getMessage()).concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "La cuenta se ha eliminado con éxito!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
}
