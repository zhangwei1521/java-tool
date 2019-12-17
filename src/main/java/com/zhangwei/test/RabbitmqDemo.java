package com.zhangwei.test;

import com.zhangwei.rabbitmq.SimpleMessageConsumer;
import com.zhangwei.rabbitmq.SimpleMessageProducer;

public class RabbitmqDemo {
    public static void main(String[] args) {
        try {
            SimpleMessageProducer.produceMessage(null);
            SimpleMessageConsumer.consumeMessage();
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
