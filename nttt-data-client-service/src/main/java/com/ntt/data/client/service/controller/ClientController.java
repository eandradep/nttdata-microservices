package com.ntt.data.client.service.controller;

import com.ntt.data.client.service.models.dto.CreateClientDto;
import com.ntt.data.client.service.models.dto.UpdateClientDto;
import com.ntt.data.client.service.models.entity.Client;
import com.ntt.data.client.service.models.entity.Person;
import com.ntt.data.client.service.models.services.IClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("/clientes")
public class ClientController {

    private final IClientService iClientService;
    private final ModelMapper modelMapper;

    @Autowired
    public ClientController(IClientService iClientService, ModelMapper modelMapper) {
        this.iClientService = iClientService;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Obtener una lista de todos los clientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa y se encontró la lista de clientes")
    })
    @GetMapping
    public List<Client> index() {
        return iClientService.getAllClients();
    }

    @Operation(summary = "Obtener detalles de un cliente específico por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa y cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "No se encontró al cliente con el ID especificado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor al realizar la consulta en la base de datos")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Client cliente;
        Map<String, Object> response = new HashMap<>();
        try {
            cliente = this.iClientService.getClientById(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", Objects.requireNonNull(e.getMessage()).concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (cliente == null) {
            response.put("mensaje", "El cliente ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Client>(cliente, HttpStatus.OK);
    }

    @Operation(summary = "Crear un nuevo cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Datos registrados correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta debido a errores de validación"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor al realizar el insert en la base de datos")
    })
    @PostMapping
    public ResponseEntity<?> saveQuestion(
            @Valid @RequestBody CreateClientDto clientData, BindingResult result) {
        Person newPerson;
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
            Client person = modelMapper.map(clientData, Client.class);
            newPerson = iClientService.saveClient(person);
            response.put("message", "Datos registrados correctamente!");
            response.put("result", newPerson);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Actualizar los datos de un cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "El cliente se ha actualizado con éxito"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta debido a errores de validación"),
            @ApiResponse(responseCode = "404", description = "El cliente no se pudo encontrar en la base de datos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor al actualizar el cliente en la base de datos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @Valid @RequestBody UpdateClientDto cliente, BindingResult result, @PathVariable Long id) {

        Client clienteActual = this.iClientService.getClientById(id);
        Client clienteUpdated = null;
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
            response.put("mensaje", "Error: no se pudo editar, el cliente ID: "
                    .concat(id.toString().concat(" no existe en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
            clienteActual.updateData(cliente);
            clienteUpdated = this.iClientService.saveClient(clienteActual);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar el cliente en la base de datos");
            response.put("error", Objects.requireNonNull(e.getMessage()).concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El cliente ha sido actualizado con éxito!");
        response.put("cliente", clienteUpdated);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Eliminar un cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El cliente se ha eliminado con éxito"),
            @ApiResponse(responseCode = "404", description = "El cliente no se pudo encontrar en la base de datos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor al eliminar el cliente de la base de datos")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        Client clienteActual = this.iClientService.getClientById(id);
        if (clienteActual == null) {
            response.put("mensaje", "Error: no se pudo eliminar, el cliente ID: "
                    .concat(id.toString().concat(" no existe en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        if (!clienteActual.getStatus()) {
            response.put("mensaje", "Error: no se pudo eliminar, el cliente ID: "
                    .concat(id.toString().concat(", el clente ya se ha eliminado!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
            clienteActual.changeStatus();
            this.iClientService.saveClient(clienteActual);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar el cliente de la base de datos");
            response.put("error", Objects.requireNonNull(e.getMessage()).concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El cliente eliminado con éxito!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

}
