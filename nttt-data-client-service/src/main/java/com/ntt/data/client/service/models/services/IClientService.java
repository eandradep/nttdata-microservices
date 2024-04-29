package com.ntt.data.client.service.models.services;

import com.ntt.data.common.module.models.service.client.Client;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IClientService {

    /**
     * Retrieves a list of all clients.
     *
     * @return List<Client> - A list of all clients
     *
     * @transactional The method is transactional and operates in read-only mode
     */
    @Transactional(readOnly = true)
    List<Client> getAllClients();

    /**
     * Saves a client in the database.
     *
     * @param person The client to be saved.
     * @return The saved client.
     */
    @Transactional()
    Client saveClient(Client person);

    /**
     * Retrieves a client by their ID.
     *
     * @param personId the ID of the client to retrieve
     * @return the client with the specified ID
     */
    @Transactional(readOnly = true)
    Client getClientById(Long personId);

    @Transactional(readOnly = true)
    Client getClientByIdentity(String identity);

}
