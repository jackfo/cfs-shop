package com.whpu.service;



import com.whpu.controller.miaosha.GoodsController;
import com.whpu.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Test {

    @Bean(name = "service1")
    IService service1(){
        IService iService = new ServiceImpl();
        iService.setName("1");
        return iService;
    }
    @Bean(name = "service2")
    IService service2(){
        IService iService = new ServiceImpl();
        iService.setName("2");
        return iService;
    }

}
