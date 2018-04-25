package com.whpu.mq;


import com.whpu.util.convert.UtilObject;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQSender {

    @Autowired
    DefaultMQProducer defaultMQProducer;

    public void  sendMiaoshaMessage(Object value) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        byte[] bytes = UtilObject.beanToString(value).getBytes();
        Message message = new Message(MQConfig.MIAOSHA_QUEUE,bytes);
        defaultMQProducer.send(message);
    }
}
