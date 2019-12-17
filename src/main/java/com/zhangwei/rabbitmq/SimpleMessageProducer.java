package com.zhangwei.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class SimpleMessageProducer {

	private static final String EXCHANGE_NAME = "exchange_demo";
	private static final String QUEUE_NAME = "queue_demo";
	private static final String ROUTING_KEY = "routingkey_demo";
	private static final String IP_ADDRESS = "192.168.137.2";
	private static final int PORT = 5672;
	
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(IP_ADDRESS);
		factory.setPort(PORT);
		factory.setUsername("admin");
		factory.setPassword("admin");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(EXCHANGE_NAME, "direct",true,false,null);
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
		String message = "hello rabbitmq";
		channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, 
				MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
		channel.close();
		connection.close();		
	}

}
