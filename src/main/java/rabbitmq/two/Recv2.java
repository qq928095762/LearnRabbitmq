package rabbitmq.two;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import utils.RabbitMq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv2 {
    private final static String QUEUE_NAMR="hello_root";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        RabbitMq rabbitMq = new RabbitMq();
        Channel channel = rabbitMq.CreateChanl();

        // 同一时刻服务器只会发一条消息给消费者
        channel.basicQos(1);

        QueueingConsumer consumer = new QueueingConsumer (channel);


        // basicConsume(String queue, boolean autoAck, Consumer callback)
        // 参数1 queue ：队列名
        // 参数2 autoAck ： 是否自动ACK
        // 参数3 callback ： 消费者对象的一个接口，用来配置回调
        channel.basicConsume(QUEUE_NAMR, false, consumer);

        // 获取消息
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [recv2] Received '" + message + "'");
            Thread.sleep(10000);
            // 返回确认状态，注释掉表示使用自动确认模式
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }

    }
}
