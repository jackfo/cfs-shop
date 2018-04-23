package com.whpu.service.impl;

import com.whpu.service.IService;
import com.whpu.controller.miaosha.GoodsController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 测试bean配置
 * */
@Service
public class ServiceImpl implements IService {
    public String name;

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
