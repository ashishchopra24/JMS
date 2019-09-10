package com.project.jms;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class FirstQueue {

    public static void main(String ar[]) throws NamingException, JMSException, InterruptedException {

        InitialContext initialContext=null;
        initialContext=new InitialContext();

       ConnectionFactory cf=(ConnectionFactory) initialContext.lookup("ConnectionFactory");
       Connection  connection=cf.createConnection();
       Session session=connection.createSession();

       Queue queue=(Queue) initialContext.lookup("queue/inboundQueue");
       MessageProducer tenant=session.createProducer(queue);

       TextMessage message=session.createTextMessage("this is the message sent by the tenant");
       tenant.send(message);
        System.out.println("Message Sent :- "+message.getText());

       MessageConsumer partner=session.createConsumer(queue);
       connection.start();
       //Thread.sleep(480000);
       TextMessage receivedMessage=(TextMessage) partner.receive(2000);
       System.out.println("Message Received :- "+receivedMessage.getText());


    }

}
