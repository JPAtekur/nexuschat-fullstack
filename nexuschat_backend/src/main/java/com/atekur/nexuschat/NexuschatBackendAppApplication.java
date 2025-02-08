package com.atekur.nexuschat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NexuschatBackendAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(NexuschatBackendAppApplication.class, args);
    }

}
