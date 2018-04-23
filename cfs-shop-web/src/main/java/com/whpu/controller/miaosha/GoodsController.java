package com.whpu.controller.miaosha;


import com.whpu.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/login")
public class GoodsController {

    @Autowired
    private IService serviceImpl;

    @Resource(name="service1")
    private IService service1;

    @Resource(name="service2")
    private IService service2;

    @RequestMapping("/to_login")
    @ResponseBody
    public String to_login(){
        System.out.println(service1+service1.getName());
        System.out.println(service2+service2.getName());
        System.out.println(serviceImpl+serviceImpl.getName());
        return "to_login";
    }

    @RequestMapping("do_login")
    public String do_login(){
        return "";
    }

}
