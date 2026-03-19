package com.example.payment_service.consumer;

import com.example.payment_service.dto.OrderCreatedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RegionConsumer {
    @RabbitListener(queues="order.india.queue")
    public void handleIndiaOrder(OrderCreatedEvent event){
        System.out.println("[TOPIC_INDIA] Recieved Order: "+event.getOrderId());
        System.out.println("[TOPIC_INDIA] Amount :"+event.getAmount());
        System.out.println("[TOPIC_INDIA] applying India regional processing...");
    }

    @RabbitListener(queues="order.usa.queue")
    public void handleUsOrder(OrderCreatedEvent event){
        System.out.println("[TOPIC_USA] Recieved Order: "+event.getOrderId());
        System.out.println("[TOPIC_USA] Amount :"+event.getAmount());
        System.out.println("[TOPIC_USA] pplying Usa regional processing...");
    }}
