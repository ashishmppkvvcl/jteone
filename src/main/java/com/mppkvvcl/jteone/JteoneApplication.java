package com.mppkvvcl.jteone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.resilience.annotation.EnableResilientMethods;

@SpringBootApplication
@EnableResilientMethods
public class JteoneApplication {

    public static void main(String[] args) {
        SpringApplication.run(JteoneApplication.class, args);
    }
}
