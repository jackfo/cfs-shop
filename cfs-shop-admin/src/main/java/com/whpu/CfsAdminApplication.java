package com.whpu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan("com.whpu.controller")
/**
 * 该注解是一个组合注解 包含@EnableAutoConfiguration @ComponentScan
 * @EnableAutoConfiguration 让spring Boot根据类路径的jar包依赖为当前项目进行自动配置
 *                          如:
 * */
//@SpringBootApplication
public class CfsAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(CfsAdminApplication.class, args);
    }
}
