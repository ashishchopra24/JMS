package com.project.jms;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class JmsContext {

    public static void main(String[] args) throws NamingException {

        InitialContext initialContext=new InitialContext();
        Queue queue= (Queue) initialContext.lookup("queue/inbound");

                try( ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
                     JMSContext jmsContext = cf.createContext()
                ){
                   JMSProducer tenant= jmsContext.createProducer();
                   String messages[]=new String[3];
                   messages[0]="message 1";
                    messages[1]="message 2";
                    messages[2]="message 3";

                    tenant.setPriority(1);
                    tenant.send(queue,messages[0]);

                    tenant.setPriority(2);
                    tenant.send(queue,messages[1]);

                    tenant.setPriority(3);
                    tenant.send(queue,messages[2]);

                    JMSConsumer consumer=jmsContext.createConsumer(queue);

                    for(int i=0;i<3;i++)
                        System.out.println(consumer.receiveBody(String.class));



                }

    }
}
