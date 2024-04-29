package com.ntt.data.client.service.models.entity;


import com.ntt.data.client.service.models.dto.UpdateClientDto;
import lombok.Getter;
import lombok.Setter;
import io.swagger.v3.oas.annotations.Hidden;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@Hidden
@MappedSuperclass
public abstract class Person implements Serializable {

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "gender", length = 10)
    private String gender;

    @Column(name = "age")
    private String age;

    @Column(name = "identification", unique = true, length = 20)
    private String identification;

    @Column(name = "address", length = 100)
    private String address;

    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    public void updateData(UpdateClientDto updateClientDto) {
        this.name =  updateClientDto.getName();
        this.gender= updateClientDto.getGender();
        this.age = updateClientDto.getAge();
        this.address = updateClientDto.getAddress();
        this.phoneNumber= updateClientDto.getPhoneNumber();
    }

}
