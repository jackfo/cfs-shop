package com.whpu.controller;


import com.whpu.model.SysUser;
import com.whpu.util.exception.MsgException;
import com.whpu.util.json.CodeMsg;
import com.whpu.util.json.HttpResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    /**
     * 返回对应的登录界面
     * TODO:如果返回字符串是login那么会一直重定向,不知道为什么
     * */
    @RequestMapping("/to_login")
    public String toLogin() {
        System.out.println("你好时间");
        return "to_login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public HttpResponse do_login(HttpServletRequest request){
        String error = null;
        String userLoginId = request.getParameter("userLoginId");
        String password = request.getParameter("password");
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userLoginId, password);
        token.setRememberMe(true);
        if (userLoginId==null){
            throw new MsgException(CodeMsg.USER_ID_ISEMPTY);
        }
        try {
            subject.login(token);
            //TODO:待完善
        } catch (UnknownAccountException e) {
            error = "用户名/密码错误";
            throw new MsgException(CodeMsg.SERVER_ERROR);
        } catch (IncorrectCredentialsException e) {
            error = "用户名/密码错误";
            throw new MsgException(CodeMsg.PASSWORD_ERROR);
        } catch (AuthenticationException e) {
            //其他错误，比如锁定，如果想单独处理请单独catch处理
            error = "其他错误：" + e.getMessage();
            throw new MsgException(CodeMsg.PASSWORD_ERROR);
        }
        return HttpResponse.success("登陆成功");
    }


//    @RequestMapping("/do_login")
//    @ResponseBody
//    public Result<String> doLogin(HttpServletResponse response, @Valid LoginVo loginVo) {
//        //登录
//        String token = userService.login(response, loginVo);
//        return Result.success(token);
//    }
}
