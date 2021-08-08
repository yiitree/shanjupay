package com.shanjupay.test.rocketmq.message;

import com.shanjupay.test.rocketmq.model.OrderExt;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class ProducerSimple {

    /**
     * mq模板
     */
    @Autowired
    private RocketMQTemplate rocketMQTemplate;


    /**
     * 同步消息
     * @param topic 主题
     * @param msg 消息内容
     */
    public void sendSyncMsg(String topic,String msg){
        // 同步消息会有消息返回值
        SendResult sendResult = rocketMQTemplate.syncSend(topic, msg);
        System.out.println();
    }


    /**
     * 异步消息
     * @param topic
     * @param msg
     */
    public void sendASyncMsg(String topic,String msg){
        rocketMQTemplate.asyncSend(topic, msg, new SendCallback() {

            //消息发送成功的回调
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("发送成功");
                System.out.println(sendResult);
            }

            //消息发送失败的回调
            @Override
            public void onException(Throwable throwable) {
                System.out.println("发送失败");
                System.out.println(throwable.getMessage());
            }
        });
    }

    /**
     * 同步发送自定义对象
     * @param topic
     * @param orderExt
     */
    public void sendMsgByJson(String topic, OrderExt orderExt){
        // 将对象转成json串发送
        rocketMQTemplate.convertAndSend(topic,orderExt);
    }

    /**
     * 发送延迟消息
     */
    public void sendMsgByJsonDelay(String topic, OrderExt orderExt){
        // 先把消息对象转化为mq的消息体
        Message<OrderExt> message = MessageBuilder.withPayload(orderExt).build();
        // String destination,
        // Message<?> message,
        // long timeout(发送消息超时时间，毫秒),
        // int delayLevel 延迟等级 --- 一共18级别，其实就是等待3级（10s）后才可以进行发送消息，一般用于支付等待情况，30s后还不支付，就执行此方法，订单失败
        rocketMQTemplate.syncSend(topic,message,1000,3);


        System.out.printf("send msg : %s",orderExt);
    }

}
