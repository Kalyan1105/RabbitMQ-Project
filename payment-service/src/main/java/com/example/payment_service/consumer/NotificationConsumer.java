package com.example.payment_service.consumer;


import com.example.payment_service.dto.OrderCreatedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer{

    @RabbitListener(queues = "notification.queue")
    public void handleNotification(OrderCreatedEvent event) {
        System.out.println("[FANOUT-NOTIFICATION] Order: " + event.getOrderId());
        System.out.println("[FANOUT-NOTIFICATION] Sending Email/SMS...");
    }
}
