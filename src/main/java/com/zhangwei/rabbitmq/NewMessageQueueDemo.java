package com.zhangwei.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.GetResponse;
import com.rabbitmq.client.ReturnListener;
import com.rabbitmq.client.ShutdownListener;
import com.rabbitmq.client.ShutdownSignalException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class NewMessageQueueDemo {
    public static void main(String[] args) throws IOException, TimeoutException{
        //test01();
        //test02();
        //test03();
        test04();
    }

    private static void test01() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = ConnectionFactoryBuilder.build();
        Connection connection = null;
        Channel channel = null;
        try {
            connection = connectionFactory.newConnection();
            channel = connection.createChannel();
            //channel.exchangeDeclare("myInnerExchange","fanout",true,false,true,null);
            channel.exchangeDeclare("pendingExchange1","direct",true,false,false,null);
            channel.queueDeclare("pendingQueue1",true,false,false,null);
            channel.queueBind("pendingQueue1","pendingExchange1","pendingExchange1+pendingQueue1");
            //channel.queueBind("pendingQueue1","myInnerExchange","");
            //channel.exchangeBind("myInnerExchange","pendingExchange1","pendingExchange1-myInnerExchange");

            channel.addReturnListener(new ReturnListener() {
                @Override
                public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println("message come back from "+exchange+" with "+routingKey+
                            ", \nreplyCode: "+replyCode+
                            "\nreplyText: "+replyText+
                            "\nproperties: "+properties+
                            "\nmessage content: "+new String(body));
                }
            });
            channel.basicPublish("pendingExchange1","pendingExchange1-pendingQueue1",true,
                    new AMQP.BasicProperties().builder()
                            .contentType("text/plain")
                            .deliveryMode(2)
                            .priority(1)
                            .build(),
                    "hello from NewMessageQueueDemo".getBytes());
        } finally {
            if(channel != null){
                //channel.close();
            }
            if(connection != null){
                //connection.close();
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

    private static void test04() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = ConnectionFactoryBuilder.build();
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("MyAE","fanout",true,false,null);
        channel.queueDeclare("alternateQueue",true,false,false,null);
        channel.queueBind("alternateQueue","MyAE","");
        Map<String,Object> exchangeProps = new HashMap<>();
        exchangeProps.put("alternate-exchange","MyAE");
        channel.exchangeDeclare("pendingExchange2","direct",true,false,exchangeProps);

        //死信交换机
        channel.exchangeDeclare("dlxExchange","direct",true,false,null);
        //死信队列
        channel.queueDeclare("dlxQueue",true,false,false,null);
        channel.queueBind("dlxQueue","dlxExchange","dlxRoutingKey");

        Map<String,Object> queueProps = new HashMap<>();
        queueProps.put("x-message-ttl",6000);
        queueProps.put("x-expires",20000);
        queueProps.put("x-dead-letter-exchange","dlxExchange");
        queueProps.put("x-dead-letter-routing-key","dlxRoutingKey");
        channel.queueDeclare("pendingQueue2",true,false,false,queueProps);
        channel.queueBind("pendingQueue2","pendingExchange2","pendingExchange2-pendingQueue2");
        channel.basicPublish("pendingExchange2","pendingExchange2-pendingQueue2",
                new AMQP.BasicProperties().builder()
                        .contentType("text/plain")
                        .deliveryMode(2)
                        .priority(1)
                        .expiration("4000")
                        .build(),
                "hello from NewMessageQueueDemo.test04".getBytes());
        connection.close();
    }
}
