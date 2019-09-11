package com.project.jms;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class FirstTopic {

    public static void main(String[] args) throws NamingException, JMSException, InterruptedException {

        InitialContext initialContext=null;

        initialContext=new InitialContext();
        Topic topic=(Topic)initialContext.lookup("topic/Topic");

        ConnectionFactory cf=(ConnectionFactory) initialContext.lookup("ConnectionFactory");
        Connection connection=cf.createConnection();
        Session session=connection.createSession();

        MessageProducer producer=session.createProducer(topic);

        MessageConsumer consumer1=session.createConsumer(topic);
        MessageConsumer consumer2=session.createConsumer(topic);

        TextMessage message=session.createTextMessage("This is the message sent by producer");
        producer.send(message);
        connection.start();
        Thread.sleep(480000);
        TextMessage message1=(TextMessage)consumer1.receive();
        System.out.println("Consumer 1 message :- "+message1.getText());

        TextMessage message2=(TextMessage)consumer2.receive();
        System.out.println("Consumer 2 message :- "+message2.getText());

        connection.close();
        initialContext.close();



    }
}
