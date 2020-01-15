package com.zhangwei.rabbitmq;

import com.rabbitmq.client.ConnectionFactory;

public class ConnectionFactoryBuilder {

    private static final String IP_ADDRESS = "192.168.56.2";
    private static final int PORT = 5672;
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";

    public static ConnectionFactory build(){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(IP_ADDRESS);
        factory.setPort(PORT);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);

        //factory.setUri("amqp://admin:admin@192.168.56.2:5672/");
        return factory;
    }
}
