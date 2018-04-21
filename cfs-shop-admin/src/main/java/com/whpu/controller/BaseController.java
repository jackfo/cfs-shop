package com.whpu.controller;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @RestController注解相当于@ResponseBody ＋ @Controller合在一起的作用。
 * */
@RestController
@SpringBootApplication
public class BaseController {

    @RequestMapping("/")
    public String sayHello(){
        return "hello";
    }
}
