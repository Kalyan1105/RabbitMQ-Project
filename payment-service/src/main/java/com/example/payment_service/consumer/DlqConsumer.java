package com.example.payment_service.consumer;
import com.example.payment_service.dto.OrderCreatedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DlqConsumer {
    @RabbitListener(queues = "payment.dlq.queue")
    public void handleDeadLetter(OrderCreatedEvent event){
        System.out.println("**********************************************");
        System.out.println("[DLQ] Failed message received");
        System.out.println("[DLQ] Order ID:" + event.getOrderId());
        System.out.println("[DLQ] Amount "+ event.getAmount());
        System.out.println("[DLQ] All retries exhausted -need manual intervention");
        System.out.println("**********************************************");

    }

}
