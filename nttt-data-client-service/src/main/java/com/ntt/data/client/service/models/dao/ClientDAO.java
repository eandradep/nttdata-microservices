package com.ntt.data.client.service.models.dao;

import com.ntt.data.client.service.models.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClientDAO extends JpaRepository<Client, Long> {

}
