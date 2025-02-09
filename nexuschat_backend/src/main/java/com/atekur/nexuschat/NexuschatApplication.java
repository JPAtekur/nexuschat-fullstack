package com.atekur.nexuschat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NexuschatApplication {

    public static void main(String[] args) {
        SpringApplication.run(NexuschatApplication.class, args);
    }

}
