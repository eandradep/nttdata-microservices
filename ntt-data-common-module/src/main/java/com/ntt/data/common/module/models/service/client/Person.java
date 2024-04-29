package com.ntt.data.common.module.models.service.client;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@Setter
@Getter
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

}
