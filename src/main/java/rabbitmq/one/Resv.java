package rabbitmq.one;

import com.rabbitmq.client.*;
import net.sf.json.JSONObject;
import rabbitmq.entity.Student;
import utils.RabbitMq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Resv {

    private final static String QUEUE_NAMR="hello_root";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        RabbitMq rabbitMq = new RabbitMq();
        Channel channel = rabbitMq.CreateChanl();

        QueueingConsumer  consumer = new QueueingConsumer (channel);

        // basicConsume(String queue, boolean autoAck, Consumer callback)
        // 参数1 queue ：队列名
        // 参数2 autoAck ： 是否自动ACK
        // 参数3 callback ： 消费者对象的一个接口，用来配置回调
        channel.basicConsume(QUEUE_NAMR, true, consumer);

        // 获取消息
        /*while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [recv] Received '" + message + "'");
        }*/


        // 测试获取一个对象
        QueueingConsumer.Delivery delivery = consumer.nextDelivery();
        String stu_str = new String(delivery.getBody());
        System.out.println("获取的消息为："+stu_str);

        JSONObject obj = JSONObject.fromObject(stu_str);
        Student stu = (Student) JSONObject.toBean(obj,Student.class);

        System.out.println(stu.getName()+";"+stu.getAddress());


    }
}
