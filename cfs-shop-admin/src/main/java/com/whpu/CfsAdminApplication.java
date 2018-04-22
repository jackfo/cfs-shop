package com.whpu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;


@Configurable
@EnableAutoConfiguration
/**
 * 该注解是一个组合注解 包含@EnableAutoConfiguration @ComponentScan
 * @EnableAutoConfiguration 让spring Boot根据类路径的jar包依赖为当前项目进行自动配置
 *                          如:
 * */
@ComponentScan(basePackages={"com.whpu.controller","com.whpu.mapper","com.whpu.shiro"})
@MapperScan(basePackages = "com.whpu.mapper")
public class CfsAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(CfsAdminApplication.class, args);
    }
}
