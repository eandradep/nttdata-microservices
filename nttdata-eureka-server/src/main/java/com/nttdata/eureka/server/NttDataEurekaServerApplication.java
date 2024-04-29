package com.nttdata.eureka.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class NttDataEurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NttDataEurekaServerApplication.class, args);
    }

}
