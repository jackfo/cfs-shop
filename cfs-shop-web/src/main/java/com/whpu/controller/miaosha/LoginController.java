package com.whpu.controller.miaosha;


import com.whpu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private IUserService userServiceImlp;

    @RequestMapping("/to_login")
    @ResponseBody
    public String to_login(){
        return "login";
    }


    @RequestMapping("do_login")
    public String do_login(){

        return "";
    }

}
