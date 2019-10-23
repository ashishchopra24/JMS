package com.project.jms;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Enumeration;

public class firstTopic {
    public static void main(String ar[]) throws NamingException, JMSException, InterruptedException {

        InitialContext initialContext=null;
        initialContext=new InitialContext();

        ConnectionFactory cf=(ConnectionFactory) initialContext.lookup("ConnectionFactory");
        Connection connection=cf.createConnection();

        Session session=connection.createSession();

        Topic topic=(Topic) initialContext.lookup("topic/Topic");
        MessageProducer tenant=session.createProducer(topic);

        MessageConsumer partner1=session.createConsumer(topic);
        MessageConsumer partner2=session.createConsumer(topic);

        TextMessage message=session.createTextMessage("I am the best");

        tenant.send(message);

        connection.start();

        TextMessage msg1=(TextMessage)partner1.receive();
        System.out.println("Consumer 1 message :- "+msg1.getText());

        TextMessage msg2=(TextMessage)partner2.receive();
        System.out.println("Consumer 2 message :- "+msg2.getText());

        connection.close();
        initialContext.close();

    }
}
