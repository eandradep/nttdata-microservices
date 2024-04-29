package com.ntt.data.client.service.controller;

import com.ntt.data.client.service.models.services.IClientService;
import com.ntt.data.common.module.models.service.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/internal-service")
public class InternalClientController {
    private final IClientService iClientService;

    @Autowired
    public InternalClientController(IClientService iClientService) {
        this.iClientService = iClientService;
    }

    @GetMapping("/{identification}")
    public Client index(@PathVariable("identification") String identification) {
        return iClientService.getClientByIdentity(identification);
    }
}
