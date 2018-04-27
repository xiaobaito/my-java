package activeMQ.simple01;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Customer {

    public static void main(String[] args) throws JMSException, InterruptedException {
        ConnectionFactory factory = new ActiveMQConnectionFactory();
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination =  session.createQueue("first");
        MessageConsumer consumer = session.createConsumer(destination);
//        session.setMessageListener();

        while (true) {
//            Thread.sleep(5000L);
            TextMessage message = (TextMessage) consumer.receive();
            if (null != message) {
                System.out.println("receive result" + message.getText());
            }
        }
    }
}
