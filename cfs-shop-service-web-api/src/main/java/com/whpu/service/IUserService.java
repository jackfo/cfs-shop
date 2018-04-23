package com.whpu.service;

import com.miaosha.model.LoginVo;

import javax.servlet.http.HttpServletResponse;

public interface IUserService {

    /**
     * @param response 前端响应流
     * @param loginVo  登录用户信息
     * */
    public String login(HttpServletResponse response, LoginVo loginVo);
}
