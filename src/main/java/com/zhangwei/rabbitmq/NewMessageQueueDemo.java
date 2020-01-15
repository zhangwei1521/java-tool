package com.zhangwei.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.GetResponse;
import com.rabbitmq.client.ShutdownListener;
import com.rabbitmq.client.ShutdownSignalException;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class NewMessageQueueDemo {
    public static void main(String[] args) throws IOException, TimeoutException{
        //test01();
        //test02();
        test03();
    }

    private static void test01() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = ConnectionFactoryBuilder.build();
        Connection connection = null;
        Channel channel = null;
        try {
            connection = connectionFactory.newConnection();
            channel = connection.createChannel();
            channel.exchangeDeclare("myInnerExchange","fanout",true,false,true,null);
            channel.exchangeDeclare("pendingExchange1","direct",true,false,false,null);
            channel.queueDeclare("pendingQueue1",true,false,false,null);
            channel.queueBind("pendingQueue1","myInnerExchange","");
            channel.exchangeBind("myInnerExchange","pendingExchange1","pendingExchange1-myInnerExchange");

            channel.basicPublish("pendingExchange1","pendingExchange1-myInnerExchange",
                    new AMQP.BasicProperties().builder()
                            .contentType("text/plain")
                            .deliveryMode(2)
                            .priority(1)
                            .build(),
                    "hello from NewMessageQueueDemo".getBytes());
        } finally {
            if(channel != null){
                channel.close();
            }
            if(connection != null){
                connection.close();
            }
        }
        System.out.println("success");
    }

    private static void test02() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = ConnectionFactoryBuilder.build();
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.basicQos(20);
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("receive message : "+new String(body));
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        channel.basicConsume("pendingQueue1",false, consumer);
    }

    private static void test03() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = ConnectionFactoryBuilder.build();
        Connection connection = connectionFactory.newConnection();
        connection.addShutdownListener(new ShutdownListener() {
            @Override
            public void shutdownCompleted(ShutdownSignalException cause) {
                System.out.println(cause.getReason());
                System.out.println(cause.getReference());
            }
        });
        Channel channel = connection.createChannel();
        GetResponse resp = channel.basicGet("pendingQueue1",false);
        System.out.println("receive message : "+new String(resp.getBody()));
        channel.basicAck(resp.getEnvelope().getDeliveryTag(),false);
        channel.close();
        connection.close();
    }
}
