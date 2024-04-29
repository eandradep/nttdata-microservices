package com.ntt.data.account.service.models.clients;

import com.ntt.data.common.module.models.service.client.Client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "client-service")
public interface IClientFeign {

    @GetMapping("/internal-service/{identification}")
    Client findClientByIdentification(@PathVariable("identification") String identification);

}
