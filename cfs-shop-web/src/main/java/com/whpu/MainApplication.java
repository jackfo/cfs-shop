package com.whpu;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 前台商城启动项
 * */
@Configurable
@SpringBootApplication
@ComponentScan(basePackages = {"com.whpu.controller.miaosha","com.whpu.service","com.whpu.config"})
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

}

