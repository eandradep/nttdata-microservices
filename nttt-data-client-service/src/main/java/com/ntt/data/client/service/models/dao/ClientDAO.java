package com.ntt.data.client.service.models.dao;

import com.ntt.data.common.module.models.service.client.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ClientDAO extends JpaRepository<Client, Long> {

    Optional<Client> findByIdentification(String lastName);

}
