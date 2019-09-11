package com.project.jms;

import org.apache.activemq.artemis.api.jms.ActiveMQJMSClient;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Enumeration;

public class FirstQueue {

    public static void main(String ar[]) throws NamingException, JMSException, InterruptedException {

        InitialContext initialContext=null;
        initialContext=new InitialContext();

       ConnectionFactory cf=(ConnectionFactory) initialContext.lookup("ConnectionFactory");
       Connection  connection=cf.createConnection();

       Session session=connection.createSession();

       Queue queue=(Queue) initialContext.lookup("queue/inboundQueue1");
       MessageProducer tenant=session.createProducer(queue);

       TextMessage message1=session.createTextMessage("Message 1");
        TextMessage message2=session.createTextMessage("Message 2");

        tenant.send(message1);
        tenant.send(message2);

        QueueBrowser queueBrowser=session.createBrowser(queue);

        Enumeration messages=queueBrowser.getEnumeration();

        while(messages.hasMoreElements()) {
            TextMessage message=(TextMessage)messages.nextElement();
            System.out.println("Message Sent :- " + message.getText());
        }

       MessageConsumer partner=session.createConsumer(queue);
        connection.start();
        //Thread.sleep(60000);
        //Thread.sleep(60000);
        TextMessage msg=(TextMessage) partner.receive();
        Thread.sleep(60000);
       System.out.println("First Message Received :- "+msg.getText());
        msg=(TextMessage) partner.receive();
        System.out.println("Second Message Received :- "+msg.getText());

       connection.close();
       initialContext.close();

    }

}
