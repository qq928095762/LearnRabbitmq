package utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMq {


    public Channel CreateChanl() throws IOException, TimeoutException {
        // 创建连接
        ConnectionFactory factory = new ConnectionFactory();
        // 设置ip和端口
        factory.setHost("10.9.95.96");
        factory.setPort(5672);

        factory.setUsername("root");
        factory.setPassword("123");
        factory.setVirtualHost("/");

        // 创建一个连接
        Connection connection = factory.newConnection();
        // 创建一个通道
        Channel channel = connection.createChannel();

        return  channel;
    }



}
