/**
 * Project Name:ActiveMq
 * File Name:ConsumerApp.java
 * Package Name:com.zjs.consumer
 * Date:2016��12��23������11:42:11
 * Copyright (c) 2016, 893568029@qq.com All Rights Reserved.
 *
*/
package com.zjs.consumer;
/**
 * ClassName: ConsumerApp <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(��ѡ). <br/>
 * date: 2016��12��23�� ����11:42:11 <br/>
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
 * activemq������ʵ��
 */
public class ConsumerApp implements MessageListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerApp.class);
    private static final String BROKER_URL = "tcp://112.124.39.129:61616";
    private static final String SUBJECT = "zjs";

    public static void main(String[] args) throws JMSException {
        //��ʼ��ConnectionFactory
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);

        //����mq����
        Connection conn = connectionFactory.createConnection();
        //��������
        conn.start();

        //�����Ự
        Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //ͨ���Ự����Ŀ��
        Destination dest = session.createQueue(SUBJECT);
        //����mq��Ϣ��������
        MessageConsumer consumer = session.createConsumer(dest);

        //��ʼ��MessageListener
        ConsumerApp me = new ConsumerApp();

        //���������趨��������
        consumer.setMessageListener(me);
    }

    public void onMessage(Message message) {
        TextMessage txtMessage = (TextMessage)message;
        try {
            LOGGER.info ("get message " + txtMessage.getText());
        } catch (JMSException e) {
            LOGGER.error("error {}", e);
        }
    }
}
