package com.whpu.mq;


import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    public static final String MIAOSHA_QUEUE = "miaosha_queue";

    public static final String MIAOSHA_GROUP = "miaosha_group";


    @Value("${rocketMQ.namesrvAddr}")
    private String namesrvAddr;

    @Bean
    DefaultMQProducer defaultMQProducer(){
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer(MQConfig.MIAOSHA_GROUP);
        defaultMQProducer.setNamesrvAddr(namesrvAddr);
        try {
            defaultMQProducer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        return defaultMQProducer;
    }


}
