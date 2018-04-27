package activeMQ.simple01;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Producer {

    public static void main(String[] args) throws JMSException, InterruptedException {
        //第一步：建立ConnectionFactory工厂对象，需要填入用户名，密码，以及要连接的地址，也可以使用默认值
        ConnectionFactory factory = new ActiveMQConnectionFactory();
        //第二步：通过ConnectionFactory创建一个Connection连接，并使用start()开启这个连接，连接默认是关闭的
        Connection connection = factory.createConnection();
        connection.start();
        //第三步：通过Connection创建一个Session会话（上下文环境对象），用于接受消息，
        //参数讲解：transacted :是否启用事务 ；acknowledgeMode 接收模式

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //第四步：通过Session创建destinition,指的是一个客户端用来制定生产消息的目标和消费消息的来源
        //在PTP模式中，Destinition被称作Queue,在Pub/Sub中,Destinition被称作Topic(主题)，在程序中可以有多个Queue和Topic
        Destination destination = session.createQueue("first");

        // 第五步：我们需要通过Session对象创建消息的发送和接受对象(生产者/消费者)，MessageProducer/MessageConsumer
        MessageProducer producer = session.createProducer(destination);
        //第六步：我们可以使用MessageProducer.setDeliverMode()设置持久化/非持久化(DeliverMode)
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        //第七步：使用JMS规范的TextMessage形式创建数据（通过Session对象）并用MessageProducer的send方法封装数据receive接收数据
        Thread.sleep(1000L);
        for (int i = 0; i < 10000; i++) {
            TextMessage textMessage = session.createTextMessage("send test" + i);
            producer.send(destination,textMessage,DeliveryMode.NON_PERSISTENT,0,1000L);
            System.out.println("send message : " + textMessage.getText());


        }
        connection.close();
        //最后需要关闭连接





    }
}
