package com.mc.erp.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.toolkit.Sequence;
import org.hibernate.validator.constraints.UUID;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication(scanBasePackages = {"com.mc.erp"})
public class McErpAdminApplication {

    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("super@Admin123");
        System.out.println(encode);
        SpringApplication.run(McErpAdminApplication.class, args);
    }

    /**
     * 初始化雪花id生成类
     * @return
     * @throws UnknownHostException
     */
    @Bean
    public Sequence sequence() throws UnknownHostException {
        InetAddress.getLocalHost();
        return new Sequence(InetAddress.getLocalHost());
    }
}
