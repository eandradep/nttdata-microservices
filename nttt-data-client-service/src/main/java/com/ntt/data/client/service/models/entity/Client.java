package com.ntt.data.client.service.models.entity;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@Hidden
@Entity
@Table(name = "client", schema = "client_schema")
public class Client extends Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long clientId;

    @Column(name = "password", length = 30, nullable = false)
    private String password;

    @Column(name = "status", nullable = false)
    private Boolean status;

    public void changeStatus(){
        this.status = !this.status;
    }

}
