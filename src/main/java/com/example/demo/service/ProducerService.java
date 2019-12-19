package com.example.demo.service;

import io.netty.util.internal.ThreadExecutorMap;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 生产者
 */
@Service
public class ProducerService {

    /**
     * 初始化
     */
    public static void main(String[] args) throws Exception {
        // 创建一个带组名的生产者实例
        DefaultMQProducer producer = new
                DefaultMQProducer("please_rename_unique_group_name");
        // 指定MQ服务ID地址/域名
        producer.setNamesrvAddr("localhost:9876");
        // 启动生产者实例
        producer.start();
        for (int i = 0; i < 10; i++) {
            // 创建消息实例, 指定topic, tag和消息体.
            Message msg = new Message("TopicTest",
                    "TagA",
                    ("Hello RocketMQ " +
                            i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            // 调用发送消息方法来传递消息到其中一个broker
            SendResult sendResult = producer.send(msg);
//                System.out.printf("%s%n", sendResult);
            System.out.printf("发送消息的ID：%s, ---发送消息的状态：%s%n", sendResult.getMsgId(), sendResult.getSendStatus());
        }
        // 不常使用的话，关闭生产者实例
        producer.shutdown();
    }

}
