package com.whpu.service.impl;

import com.miaosha.mapper.GoodsMapper;
import com.miaosha.model.GoodsVo;
import com.whpu.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GoodServiceImpl implements IGoodsService {

    @Resource
    GoodsMapper goodsMapper;

    public List<GoodsVo> listGoodsVo(){
        return goodsMapper.listGoodsVo();
    }
}
