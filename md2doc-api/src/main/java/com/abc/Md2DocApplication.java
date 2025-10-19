package com.abc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.abc.*")
public class Md2DocApplication {
    public static void main(String[] args) {
        SpringApplication.run(Md2DocApplication.class, args);
    }
}
