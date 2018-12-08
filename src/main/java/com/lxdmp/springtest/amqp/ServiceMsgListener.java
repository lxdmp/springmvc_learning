package com.lxdmp.springtest.amqp;

import java.io.IOException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;
import com.rabbitmq.client.Channel;
import org.apache.log4j.Logger;

@Component
public class ServiceMsgListener implements ChannelAwareMessageListener
{
	private static final Logger logger = Logger.getLogger(ServiceMsgListener.class);

	@Override
	public void onMessage(Message message, Channel channel) throws Exception
	{
		String body = new String(message.getBody(), "UTF-8");
		String queueName = message.getMessageProperties().getConsumerQueue();
		logger.info("消息内容:"+body);
		logger.info(queueName);

		boolean msgConsumed = false;
		// 业务处理
		// 可根据消息来自的队列进行分发处理
		msgConsumed = true;

		if(msgConsumed){
			basicACK(message, channel);
		}else{
			basicNACK(message, channel);
		}
	}
	
	private void basicACK(Message message,Channel channel)
	{
		// 正常消费掉后通知mq服务器移除此条mq
		try{
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		}catch(IOException e){
			logger.error("通知服务器移除mq时异常，异常信息："+e);
		}
	}
	
	private void basicNACK(Message message,Channel channel)
	{
		// 处理异常，mq重回队列
		try{
			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
		}catch(IOException e){
			logger.error("mq重新进入服务器时出现异常，异常信息："+e);
		}
	}
}

