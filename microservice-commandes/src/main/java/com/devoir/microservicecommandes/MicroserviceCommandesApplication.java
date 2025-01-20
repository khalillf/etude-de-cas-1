package com.devoir.microservicecommandes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication
@RefreshScope
public class MicroserviceCommandesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceCommandesApplication.class, args);
    }

}
