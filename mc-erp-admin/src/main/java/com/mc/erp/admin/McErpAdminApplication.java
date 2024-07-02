package com.mc.erp.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.mc.erp"})
public class McErpAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(McErpAdminApplication.class, args);
    }

}
