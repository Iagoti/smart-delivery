package br.com.iago.smartdelivery.modules.orders.consumers;

import br.com.iago.smartdelivery.configs.RabbitMQConfig;
import br.com.iago.smartdelivery.modules.orders.CreateDeliveryOrderUseCase;
import br.com.iago.smartdelivery.modules.orders.dto.OrderEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OrderCreatedConsumer {

    private CreateDeliveryOrderUseCase createDeliveryOrderUseCase;

    public OrderCreatedConsumer(CreateDeliveryOrderUseCase createDeliveryOrderUseCase){
        this.createDeliveryOrderUseCase = createDeliveryOrderUseCase;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_ORDER_CREATED)
    public void listener(OrderEvent event) {
        System.out.println("Chegou mensagem ");
        System.out.println("ID: " + event.id());

        this.createDeliveryOrderUseCase.execute(UUID.fromString(event.id()));
    }
}
