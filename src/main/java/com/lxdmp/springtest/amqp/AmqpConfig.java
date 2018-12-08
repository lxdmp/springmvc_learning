package com.lxdmp.springtest.amqp;

import java.util.Collections;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
@PropertySource("classpath:rabbitmq.properties")
public class AmqpConfig
{
	@Autowired
	Environment env;
	
	@Bean
	public ConnectionFactory connectionFactory()
	{
		CachingConnectionFactory factory = new CachingConnectionFactory(
			env.getProperty("host"), 
			env.getProperty("port", Integer.class)
		);
		factory.setUsername(env.getProperty("username"));
		factory.setPassword(env.getProperty("password"));
		return factory;
	}
	
	@Bean
	public RabbitAdmin rabbitAdmin(@Autowired ConnectionFactory connectionFactory)
	{
		return new RabbitAdmin(connectionFactory);
	}

	@Bean
	public AmqpTemplate rabbitTemplate(@Autowired ConnectionFactory connectionFactory)
	{
		ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
		backOffPolicy.setInitialInterval(500);
		backOffPolicy.setMultiplier(10.0);
		backOffPolicy.setMaxInterval(10000);

		RetryTemplate retryTemplate = new RetryTemplate();
		retryTemplate.setBackOffPolicy(backOffPolicy);

		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setRetryTemplate(retryTemplate);
		return rabbitTemplate;
	}
	
	@Bean
	public ChannelAwareMessageListener channelAwareMessageListener() 
		throws InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		return (ChannelAwareMessageListener)Class.forName(
			env.getProperty("listener.class")
		).newInstance();
	}
	
	@Bean
	public SimpleMessageListenerContainer messageListenerContainer(
		@Autowired ConnectionFactory connectionFactory, 
		@Autowired ChannelAwareMessageListener channelAwareMessageListener
	)
	{
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setChannelAwareMessageListener(channelAwareMessageListener);
		String[] queueNames = env.getProperty("queuenames").split(",");
		Queue[] queues = new Queue[queueNames.length];
		for(int i=0; i<queues.length; ++i)
			queues[i] = new Queue(queueNames[i]);
		container.setQueues(queues);
		return container;
	}
	//rabbitTemplate.setRoutingKey(env.getProperty("rabbitmq.routingkey"));
}

