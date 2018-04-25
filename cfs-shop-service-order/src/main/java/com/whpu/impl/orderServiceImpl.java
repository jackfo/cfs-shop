package com.whpu.impl;

import com.miaosha.mapper.GoodsMapper;
import com.miaosha.mapper.MiaoshaUserMapper;
import com.miaosha.mapper.OrderMapper;
import com.miaosha.model.*;
import com.whpu.constant.Info;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.IOrderService;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class orderServiceImpl implements IOrderService {

    @Resource
    MiaoshaUserMapper miaoshaUserMapper;

    @Resource
    GoodsMapper goodsMapper;

    @Resource
    OrderMapper orderMapper;

    @Autowired
    RedissonClient redissonClient;

    @Override
    public OrderInfo miaosha(MiaoshaUser miaoshaUser, GoodsVo goodsVo) {
        MiaoshaGoods g = new MiaoshaGoods();
        g.setGoodsId(goodsVo.getId());
        int result = goodsMapper.reduceStock(g);
        if (result>0){
            return createOrder(miaoshaUser, goodsVo);
        }else{
            setGoodsOver(goodsVo.getId());
            return null;
        }
    }

    @Transactional
    public OrderInfo createOrder(MiaoshaUser user, GoodsVo goods) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getId());
        orderMapper.insert(orderInfo);
        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setGoodsId(goods.getId());
        miaoshaOrder.setOrderId(orderInfo.getId());
        miaoshaOrder.setUserId(user.getId());
        orderMapper.insertMiaoshaOrder(miaoshaOrder);

        //将减少的信息添加到redis缓存中去
        RMap<String,MiaoshaOrder> rMap = redissonClient.getMap(MiaoshaOrder.class.getName());
        rMap.put(user.getId()+"_"+goods.getId(),miaoshaOrder);
        return orderInfo;
    }


    /**
     * 设置秒杀商品是否结束
     * */
    private void setGoodsOver(Long goodsId) {
        RMap<Long,Boolean> rMap = redissonClient.getMap(Info.isGoodsOver);
        rMap.put(goodsId,true);
    }

}
