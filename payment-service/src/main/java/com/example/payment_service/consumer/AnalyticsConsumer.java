package com.example.payment_service.consumer;




import com.example.payment_service.dto.OrderCreatedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AnalyticsConsumer {

    @RabbitListener(queues = "analytics.queue")
    public void handleAnalytics(OrderCreatedEvent event) {
        System.out.println("[FANOUT-ANALYTICS] Order: " + event.getOrderId());
        System.out.println("[FANOUT-ANALYTICS] Storing analytics data...");
    }
}