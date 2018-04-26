package com.whpu.web.controller;

import com.whpu.web.context.UserContext;
import com.whpu.web.pojo.SessionUser;

public class BaseController {
    /**
     * 获取当前登录用户userId
     * */
    public Long userId(){
       SessionUser sessionUser = UserContext.getUser();
       if (sessionUser!=null){
           return sessionUser.getUserId();
       }
       return null;
    }

    public SessionUser sessionUser(){
        return UserContext.getUser();
    }
}
