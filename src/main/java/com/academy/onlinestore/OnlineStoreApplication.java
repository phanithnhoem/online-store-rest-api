package com.academy.onlinestore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OnlineStoreApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(OnlineStoreApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        String name = NameGeneratorUtil.generateUniqueFileName();
//        System.out.println(name);
    }
}
