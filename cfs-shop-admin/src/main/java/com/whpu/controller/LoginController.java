package com.whpu.controller;


import com.whpu.pojo.SysUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    @RequestMapping("/to_login")
    public String toLogin() {
        System.out.print("你好时间");
        return "login";
    }

    @RequestMapping("/do_login")
    public String doLogin(SysUser sysUser){

        System.out.print("doLogin方法。。。");
        return "";
    }

//    @RequestMapping("/do_login")
//    @ResponseBody
//    public Result<String> doLogin(HttpServletResponse response, @Valid LoginVo loginVo) {
//        //登录
//        String token = userService.login(response, loginVo);
//        return Result.success(token);
//    }
}
