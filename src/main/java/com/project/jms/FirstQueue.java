package com.project.jms;

import org.apache.activemq.artemis.api.jms.ActiveMQJMSClient;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class FirstQueue {

    public static void main(String ar[]) throws NamingException, JMSException, InterruptedException {

        Connection connection = null;
        try {
            // Step 1. Directly instantiate the JMS Queue object.
            Queue queue = ActiveMQJMSClient.createQueue("exampleQueue2");

            // Starting with Artemis 1.0.1 you can just use the URI to instantiate the object directly
            ConnectionFactory cf = new ActiveMQConnectionFactory("tcp://localhost:61616");

            // Step 4.Create a JMS Connection

            connection = cf.createConnection();

            Thread.sleep(60000);
            // Step 5. Create a JMS Session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //Thread.sleep(60000);
            // Step 6. Create a JMS Message Producer
            MessageProducer producer = session.createProducer(queue);


            // Step 7. Create a Text Message
            TextMessage message = session.createTextMessage("This is a text message");

            System.out.println("Sent message: " + message.getText());

            // Step 8. Send the Message
            producer.send(message);

            // Step 9. Create a JMS Message Consumer
            MessageConsumer messageConsumer = session.createConsumer(queue);

            // Step 10. Start the Connection
            connection.start();

            Thread.sleep(120000);

            // Step 11. Receive the message
            TextMessage messageReceived = (TextMessage) messageConsumer.receive(5000);

            System.out.println("Received message: " + messageReceived.getText());
        } finally {
            if (connection != null) {
                connection.close();
            }
        }

  /*      InitialContext initialContext=null;
        initialContext=new InitialContext();

       ConnectionFactory cf=(ConnectionFactory) initialContext.lookup("ConnectionFactory");
       Connection  connection=cf.createConnection();
       connection.start();

       Session session=connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

       Queue queue=(Queue) initialContext.lookup("queue/inboundQueue");
       MessageProducer tenant=session.createProducer(queue);

       TextMessage message=session.createTextMessage("this is the message sent by the tenant");
       tenant.send(message);
        System.out.println("Message Sent :- "+message.getText());

       MessageConsumer partner=session.createConsumer(queue);

       Thread.sleep(480000);
       TextMessage receivedMessage=(TextMessage) partner.receive(2000);
       System.out.println("Message Received :- "+receivedMessage.getText());

*/
    }

}
