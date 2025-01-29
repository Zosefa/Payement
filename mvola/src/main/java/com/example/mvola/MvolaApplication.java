package com.example.mvola;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MvolaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MvolaApplication.class, args);
    }

}
