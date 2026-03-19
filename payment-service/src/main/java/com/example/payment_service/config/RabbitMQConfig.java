package com.example.payment_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String DIRECT_EXCHANGE = "order.direct.exchange";
    public static final String PAYMENT_QUEUE = "payment.queue";
    public static final String DIRECT_ROUTING_KEY = "order.created";

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE);
    }
    @Bean
    public Queue paymentQueue() {
        return QueueBuilder.durable(PAYMENT_QUEUE)
                .withArgument("x-dead-letter-exchange", DLX_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DLX_ROUTING_KEY)
                .build();
    }

    @Bean
    public Binding paymentBinding() {
        return BindingBuilder
                .bind(paymentQueue())
                .to(directExchange())
                .with(DIRECT_ROUTING_KEY);
    }

    public static final String TOPIC_EXCHANGE = "order.topic.exchange";
    public static final String INDIA_QUEUE = "order.india.queue";
    public static final String USA_QUEUE = "order.usa.queue";

    public static final String INDIA_ROUTING_KEY = "order.india";
    public static final String USA_ROUTING_KEY = "order.usa";

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public Queue indiaQueue() {
        return new Queue(INDIA_QUEUE, true);
    }

    @Bean
    public Queue usaQueue() {
        return new Queue(USA_QUEUE, true);
    }

    @Bean
    public Binding indiaBinding() {
        return BindingBuilder
                .bind(indiaQueue())
                .to(topicExchange())
                .with(INDIA_ROUTING_KEY);
    }

    @Bean
    public Binding usaBinding() {
        return BindingBuilder
                .bind(usaQueue())
                .to(topicExchange())
                .with(USA_ROUTING_KEY);
    }


    public static final String FANOUT_EXCHANGE = "order.fanout.exchange";
    public static final String NOTIFICATION_QUEUE = "notification.queue";
    public static final String ANALYTICS_QUEUE = "analytics.queue";

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue(NOTIFICATION_QUEUE, true);
    }

    @Bean
    public Queue analyticsQueue() {
        return new Queue(ANALYTICS_QUEUE, true);
    }

    @Bean
    public Binding notificationBinding() {
        return BindingBuilder
                .bind(notificationQueue())
                .to(fanoutExchange());
    }

    @Bean
    public Binding analyticsBinding() {
        return BindingBuilder
                .bind(analyticsQueue())
                .to(fanoutExchange());
    }

    public static final String DLX_EXCHANGE = "order.dlx.exchange";
    public static final String DLQ_QUEUE = "payment.dlq.queue";
    public static final String DLX_ROUTING_KEY = "order.dlq.routing";

    @Bean
    public DirectExchange dlxExchange() {
        return new DirectExchange(DLX_EXCHANGE);
    }

    @Bean
    public Queue deadLetterQueue() {
        return new Queue(DLQ_QUEUE, true);
    }

    @Bean
    public Binding dlqBinding() {
        return BindingBuilder
                .bind(deadLetterQueue())
                .to(dlxExchange())
                .with(DLX_ROUTING_KEY);
    }


    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public void hello() {
        System.out.println("hello");
    }
}