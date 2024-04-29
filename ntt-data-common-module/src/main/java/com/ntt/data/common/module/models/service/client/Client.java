package com.ntt.data.common.module.models.service.client;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
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
