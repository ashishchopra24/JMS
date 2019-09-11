package com.project.jms;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class JmsContext {

    public static void main(String[] args) throws NamingException {

        InitialContext initialContext=new InitialContext();
        Queue queue= (Queue) initialContext.lookup("queue/inbound");

                try( ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
                     JMSContext jmsContext = cf.createContext()
                ){
                    jmsContext.createProducer().send(queue, "A Message from JMS2!");
                    String msg=jmsContext.createConsumer(queue).receiveBody(String.class);
                    System.out.println(msg);


                }

    }
}
