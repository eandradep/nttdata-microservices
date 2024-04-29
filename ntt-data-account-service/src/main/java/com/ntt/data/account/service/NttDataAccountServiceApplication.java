package com.ntt.data.account.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableEurekaClient
@SpringBootApplication
@EntityScan(basePackages = {"com.ntt.data.common.module.models.service"})
@EnableFeignClients
public class NttDataAccountServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NttDataAccountServiceApplication.class, args);
	}

}
