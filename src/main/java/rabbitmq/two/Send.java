package rabbitmq.two;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 *      工作队列
 *
 *
 *
 *
 *
 *
 * 能者多劳
 *
 * 5.4.Work模式的“能者多劳”
 *
 * // 同一时刻服务器只会发一条消息给消费者
 * channel.basicQos(1);
 *
 * //开启这行 表示使用手动确认模式
 * channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
 *
 *
 * 同时改为手动确认：
 *
 * // 监听队列，false表示手动返回完成状态，true表示自动
 * channel.basicConsume(QUEUE_NAME, false, consumer);
 *
 */

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    private final static String QUEUE_NAMR="hello_root";

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

        /**
         * 参数1 queue ：队列名
         * 参数2 durable ：是否持久化
         * 参数3 exclusive ：仅创建者可以使用的私有队列，断开后自动删除
         * 参数4 autoDelete : 当所有消费客户端连接断开后，是否自动删除队列
         * 参数5 arguments
         */
        channel.queueDeclare(QUEUE_NAMR,false,false,false,null);



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
            channel.basicPublish("",QUEUE_NAMR,null,message.getBytes());
            System.out.println("send a message :"+message);
        }

        channel.close();
        connection.close();
    }
}
