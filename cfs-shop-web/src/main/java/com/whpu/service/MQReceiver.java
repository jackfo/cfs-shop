package com.whpu.service;

import com.miaosha.mapper.GoodsMapper;
import com.miaosha.mapper.MiaoshaUserMapper;
import com.miaosha.mapper.OrderMapper;
import com.miaosha.model.GoodsVo;
import com.miaosha.model.MiaoshaMessage;
import com.miaosha.model.MiaoshaOrder;
import com.miaosha.model.MiaoshaUser;
import com.whpu.mq.MQConfig;
import com.whpu.util.convert.UtilObject;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListener;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import service.IOrderService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Configuration
public class MQReceiver {

    /**
     * NameServer 地址
     */
    @Value("${rocketMQ.namesrvAddr}")
    private String namesrvAddr;

    @Resource
    GoodsMapper goodsMapper;

    @Resource
    OrderMapper orderMapper;

    @Resource
    MiaoshaUserMapper miaoshaUserMapper;

    @Autowired
    IOrderService orderServiceImpl;

    /**
     * 订去MQConfig.MIAOSHA_QUEUE
     */
    @PostConstruct
    public void miaoShaConsumer(){
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(MQConfig.MIAOSHA_QUEUE);
        //指定NameServer地址，多个地址以 ; 隔开
        consumer.setNamesrvAddr(namesrvAddr);

        try {
            consumer.subscribe(MQConfig.MIAOSHA_QUEUE,"push");
            //设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
            //如果非第一次启动，那么按照上次消费的位置继续消费
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                    for(MessageExt messageExt:msgs){
                       byte[] bytes = messageExt.getBody();
                       String string = new String(bytes);
                       //至此我们已经获取到了秒杀用户的信息
                        MiaoshaMessage miaoshaMessage = UtilObject.stringToBean(string,MiaoshaMessage.class);

                        MiaoshaUser miaoshaUser = miaoshaMessage.getUser();
                        long goodsId = miaoshaMessage.getGoodsId();
                        GoodsVo goods = goodsMapper.getGoodsVoByGoodsId(goodsId);
                        int stock = goods.getStockCount();
                        if(stock <= 0) {
                           break;
                        }

                        //根据用户和id查看当前用户是否秒杀到
                        MiaoshaOrder order = orderMapper.getMiaoshaOrderByUserIdGoodsId(miaoshaUser.getId(), goodsId);
                        if(order != null) {
                            continue;
                        }
                        //减库存 下订单 写入秒杀订单
                        orderServiceImpl.miaosha(miaoshaUser, goods);
                    }
                    return null;
                }
            });
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }
}
