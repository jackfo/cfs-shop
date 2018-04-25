package com.whpu.controller.miaosha;


import com.miaosha.model.LoginVo;
import com.whpu.service.IUserService;
import com.whpu.util.json.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private IUserService userServiceImlp;

    @RequestMapping("/to_login")
    public String to_login(){
        return "login";
    }


    @RequestMapping("do_login")
    @ResponseBody
    public HttpResponse<String> do_login(HttpServletResponse response, LoginVo loginVo){
        String token = userServiceImlp.login(response,loginVo);
        return HttpResponse.success(token);
    }

}
