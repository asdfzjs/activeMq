/**
 * Project Name:ActiveMq
 * File Name:ConsumerApp.java
 * Package Name:com.zjs.consumer
 * Date:2016年12月23日上午11:42:11
 * Copyright (c) 2016, 893568029@qq.com All Rights Reserved.
 *
*/
package com.zjs.consumer;
/**
 * ClassName: ConsumerApp <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年12月23日 上午11:42:11 <br/>
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
 * activemq消费者实例
 */
public class ConsumerApp implements MessageListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerApp.class);
    private static final String BROKER_URL = "tcp://112.124.39.129:61616";
    private static final String SUBJECT = "zjs";

    public static void main(String[] args) throws JMSException {
        //初始化ConnectionFactory
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);

        //创建mq连接
        Connection conn = connectionFactory.createConnection();
        //启动连接
        conn.start();

        //创建会话
        Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //通过会话创建目标
        Destination dest = session.createQueue(SUBJECT);
        //创建mq消息的消费者
        MessageConsumer consumer = session.createConsumer(dest);

        //初始化MessageListener
        ConsumerApp me = new ConsumerApp();

        //给消费者设定监听对象
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
