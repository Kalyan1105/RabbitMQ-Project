package com.example.payment_service.consumer;

import com.example.payment_service.dto.OrderCreatedEvent;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Component
public class PaymentConsumer {
    @RabbitListener(queues = "notification.queue")
    public void notification(OrderCreatedEvent event) {
        System.out.println(" Notification: " + event);
    }

    @RabbitListener(queues = "analytics.queue")
    public void analytics(OrderCreatedEvent event) {
        System.out.println(" Analytics: " + event);
    }

    @RabbitListener(queues = "payment.queue")
    @Retryable(
            retryFor = Exception.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )
    public void processPayment(OrderCreatedEvent event) {

        System.out.println("[DIRECT] Payment for Order: " + event.getOrderId());
        System.out.println("[DIRECT] Amount: " + event.getAmount());

        if (event.getAmount() == -1) {
            System.out.println("[DIRECT] Payment failed for Order: "
                    + event.getOrderId() + " - retrying..");

            throw new RuntimeException(
                    "Payment failed - invalid amount for order: " + event.getOrderId()
            );
        }
    }
    @Recover
    public void recover(Exception e, OrderCreatedEvent event) {
        System.out.println("==================================");
        System.out.println("[RECOVERY] all 2 attempts failed for order"+ event.getOrderId());
        System.out.println("[RECOVERY] Reason: " + e.getMessage());
        System.out.println("[RECOVERY] forwarding to DLQ...: " + e.getMessage());
        System.out.println("==================================");
        throw new AmqpRejectAndDontRequeueException(e);
    }
}