package com.whpu.web.interpector;

import com.whpu.constant.UserInformation;
import com.whpu.web.context.UserContext;
import com.whpu.web.pojo.SessionUser;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * HandlerInterceptorAdapter是SpringMVC的拦截器
 *
 * preHandle在业务处理器处理请求之前被调用。预处理，可以进行编码、安全控制等处理；
 *
 * postHandle在业务处理器处理请求执行完成后，生成视图之前执行。后处理（调用了Service并返回ModelAndView，但未进行页面渲染），有机会修改ModelAndView；
 *
 * afterCompletion在DispatcherServlet完全处理完请求后被调用，可用于清理资源等。返回处理（已经渲染了页面），可以根据ex是否为null判断是否发生了异常，进行日志记录；
 *
 *
 * 当前方法主要功能:
 *    获取cookie中的信息构造相应的用户
 * */
@Service
public class AccessInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    RedissonClient redissonClient;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            {
        if (handler instanceof HandlerMethod){
           SessionUser sessionUser = getSeesionUser(request,response);
           UserContext.setUser(sessionUser);
        }
        return true;
    }

    /**
     * 获取userToken对应的cookie值,根据其cookie值找到对应的SessionUser
     * 这个SessionUser存放在redis服务端
     * */
    public  SessionUser getSeesionUser(HttpServletRequest request, HttpServletResponse response){
        String cookieToken = getCookieValue(request, UserInformation.userToken);
        if (StringUtils.isEmpty(cookieToken)){
            return null;
        }
        RMap<String,SessionUser> userRMap = redissonClient.getMap(UserInformation.token_user);
        return userRMap.get(cookieToken);
    }

    /**
     * @author jack
     * 获取所有的cookie信息,找到指定cookieName的值
     * @param cookiName cookie名
     * @return 返回cookie的值
     * */
    private String getCookieValue(HttpServletRequest request, String cookiName) {
        Cookie[]  cookies = request.getCookies();
        if(cookies == null || cookies.length <= 0){
            return null;
        }
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals(cookiName)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
