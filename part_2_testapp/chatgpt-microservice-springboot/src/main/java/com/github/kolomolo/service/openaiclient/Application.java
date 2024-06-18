package com.github.kolomolo.service.openaiclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// TODO JWT token must be enabled with simple authentication
/**
 * The SpringBoot application.
 */
@SpringBootApplication
public class Application {
    /**
     * the constructor
     */
    public Application() {
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}