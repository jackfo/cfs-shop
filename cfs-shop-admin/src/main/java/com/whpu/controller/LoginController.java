package com.whpu.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/to_login")
    public String toLogin() {
        System.out.print("你好时间");
        return "login";
    }

    @RequestMapping("/login")
    public String doLogin(){
        System.out.print("doLogin方法。。。");
        return "s";
    }

//    @RequestMapping("/do_login")
//    @ResponseBody
//    public Result<String> doLogin(HttpServletResponse response, @Valid LoginVo loginVo) {
//        //登录
//        String token = userService.login(response, loginVo);
//        return Result.success(token);
//    }
}
