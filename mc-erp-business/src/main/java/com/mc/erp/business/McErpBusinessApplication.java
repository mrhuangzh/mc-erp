package com.mc.erp.business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.mc.erp"})
public class McErpBusinessApplication {

    public static void main(String[] args) {
        SpringApplication.run(McErpBusinessApplication.class, args);
    }

}
