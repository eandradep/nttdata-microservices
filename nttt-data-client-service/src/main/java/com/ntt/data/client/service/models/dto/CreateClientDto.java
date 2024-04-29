package com.ntt.data.client.service.models.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Setter
@Getter
public class CreateClientDto implements Serializable {

    @NotEmpty(message = "La contraseña no puede estar vacía")
    @Size(min = 8, max = 30, message = "La contraseña debe tener entre 8 y 30 caracteres")
    private String password;

    @NotNull(message = "El estado no puede ser nulo")
    private Boolean status;

    @NotNull(message = "El nombre no puede ser nulo")
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
    private String name;

    @Size(max = 10, message = "El género no puede tener más de 10 caracteres")
    private String gender;

    @Pattern(regexp = "\\d{1,3}", message = "La edad debe ser un número válido de hasta 3 dígitos")
    private String age;

    @NotNull(message = "La identificación no puede ser nula")
    @Size(max = 20, message = "La identificación no puede tener más de 20 caracteres")
    private String identification;

    @Size(max = 100, message = "La dirección no puede tener más de 100 caracteres")
    private String address;

    @Pattern(regexp = "\\d{3}-\\d{3}-\\d{4}", message = "El número de teléfono debe seguir el formato: 123-456-7890")
    private String phoneNumber;
}
