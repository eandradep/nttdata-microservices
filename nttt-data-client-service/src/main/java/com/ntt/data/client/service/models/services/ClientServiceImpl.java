package com.ntt.data.client.service.models.services;

import com.ntt.data.client.service.models.dao.ClientDAO;
import com.ntt.data.client.service.models.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements IClientService {

    private final ClientDAO clientRepository;

    @Autowired
    public ClientServiceImpl(ClientDAO clientDAO) {
        this.clientRepository = clientDAO;
    }

    @Override
    public List<Client> getAllClients() {
        return this.clientRepository.findAll();
    }

    @Override
    public Client saveClient(Client person) {
        return this.clientRepository.save(person);
    }

    @Override
    public Client getClientById(Long personId) {
        return this.clientRepository.findById(personId).orElse(null);
    }

}
