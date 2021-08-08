package com.shanjupay.test.rocketmq.message;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 监听消息
 */
@Component
//  监听的消息的topic   监听的也需要创建一个组
@RocketMQMessageListener(topic = "my-topic",consumerGroup="demo-consumer-group")
public class ConsumerSimple implements RocketMQListener<String> {

    @Override
    public void onMessage(String msg) {
        //此方法被调用表示接收到消息，msg形参就是消息内容
        //处理消息...
        System.out.println(msg);
    }
}
