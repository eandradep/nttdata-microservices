package com.ntt.data.client.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableEurekaClient
@SpringBootApplication
@EntityScan(basePackages = {"com.ntt.data.common.module.models.service.client"})
@EnableFeignClients
public class NtttDataClientServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NtttDataClientServiceApplication.class, args);
    }

}
