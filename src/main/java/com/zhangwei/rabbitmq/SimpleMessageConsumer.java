package com.zhangwei.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Address;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class SimpleMessageConsumer {

	private static final String QUEUE_NAME = "queue_demo";
	private static final String IP_ADDRESS = "192.168.137.2";
	private static final int PORT = 5672;
	
	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		Address[] addresses = new Address[] {new Address(IP_ADDRESS,PORT)};
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUsername("admin");
		factory.setPassword("admin");
		Connection connection = factory.newConnection(addresses);
		Channel channel = connection.createChannel();
		channel.basicQos(64);
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag,Envelope envelope,
					AMQP.BasicProperties properties,byte[] body) throws IOException {
				System.out.println("receive message : "+new String(body));
				channel.basicAck(envelope.getDeliveryTag(), false);
			}
		};
		channel.basicConsume(QUEUE_NAME, consumer);
		Thread.sleep(10000);
		channel.close();
		connection.close();
	}

}
