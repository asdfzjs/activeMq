/**
 * Project Name:ActiveMq
 * File Name:ProducerApp.java
 * Package Name:com.zjs.produce
 * Date:2016��12��23������11:41:00
 * Copyright (c) 2016, 893568029@qq.com All Rights Reserved.
 *
*/
package com.zjs.produce;
/**
 * ClassName: ProducerApp <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(��ѡ). <br/>
 * date: 2016��12��23�� ����11:41:00 <br/>
 *
 * @author zhujisong
 * @version 
 * @since JDK 1.8
 */
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

/**
 * Created by outofmemory.cn on 14-8-26.
 * activemq ������ʵ��
 */
public class ProducerApp {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerApp.class);
    private static final String BROKER_URL = "tcp://112.124.39.129:61616";
    private static final String SUBJECT = "zjs";

    public static void main(String[] args) throws JMSException {
        //��ʼ�����ӹ���
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        //�������
        Connection conn = connectionFactory.createConnection();
        //��������
        conn.start();

        //����Session���˷�����һ��������ʾ�Ự�Ƿ���������ִ�У��ڶ��������趨�Ự��Ӧ��ģʽ
        Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //��������
        Destination dest = session.createQueue(SUBJECT);
        //createTopic������������Topic
        //session.createTopic("TOPIC");

        //ͨ��session���Դ�����Ϣ��������
        MessageProducer producer = session.createProducer(dest);
        for (int i=0;i<100;i++) {
            //��ʼ��һ��mq��Ϣ
            TextMessage message = session.createTextMessage("hello active mq ����" + i);
            //������Ϣ
            producer.send(message);
            LOGGER.debug("send message {}", i);
        }

        //�ر�mq����
        conn.close();
    }
}

