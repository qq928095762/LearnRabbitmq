package rabbitmq.four;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *   路由
 */

public class Send {
    private final static String EXCHANGE_NAME = "test_exchange_direct";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
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

        // 声明exchange
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        // 发送消息
        String message;

        /**
         * basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body)
         * 参数1 exchange ：交换器
         * 参数2 routingKey ： 路由键
         * 参数3 props ： 消息的其他参数
         * 参数4 body ： 消息体
         */
        for(int i=0;i<100;i++){
            message = "hello" + i;
            channel.basicPublish(EXCHANGE_NAME,"update",null,message.getBytes());
            System.out.println("send a message :"+message);
        }

        channel.close();
        connection.close();
    }
}
