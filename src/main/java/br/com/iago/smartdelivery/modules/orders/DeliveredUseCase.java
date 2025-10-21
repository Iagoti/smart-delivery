package br.com.iago.smartdelivery.modules.orders;

import br.com.iago.smartdelivery.modules.deliveryman.DeliveryManEntity;
import br.com.iago.smartdelivery.modules.deliveryman.DeliveryManRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeliveredUseCase {

    private OrderRepository orderRepository;
    private DeliveryManRepository deliveryManRepository;

    public DeliveredUseCase(OrderRepository orderRepository, DeliveryManRepository deliveryManRepository){
        this.orderRepository= orderRepository;
        this.deliveryManRepository = deliveryManRepository;
    }

    public void execute(UUID orderId) {
        OrderEntity orderEntity = this.orderRepository.findById(orderId).orElseThrow(() -> {
            throw new EntityNotFoundException("Pedido não encontrado " + orderId);
        });

        orderEntity.setStatus(StatusOrder.ENTREGUE);
        this.orderRepository.save(orderEntity);

        DeliveryManEntity deliveryManEntity = this.deliveryManRepository.findById(orderEntity.getDeliveryManId())
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("Entregador não cadastrado!");
                });
        deliveryManEntity.setAvailable(true);
        this.deliveryManRepository.save(deliveryManEntity);
    }
}
