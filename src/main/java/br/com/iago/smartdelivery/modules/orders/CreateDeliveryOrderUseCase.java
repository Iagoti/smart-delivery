package br.com.iago.smartdelivery.modules.orders;

import br.com.iago.smartdelivery.modules.deliveryman.DeliveryManEntity;
import br.com.iago.smartdelivery.modules.deliveryman.DeliveryManRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class CreateDeliveryOrderUseCase {

    public OrderRepository orderRepository;
    public DeliveryManRepository deliveryManRepository;

    public CreateDeliveryOrderUseCase(OrderRepository orderRepository, DeliveryManRepository deliveryManRepository){
        this.orderRepository = orderRepository;
        this.deliveryManRepository = deliveryManRepository;
    }

    @Transactional
    public void execute(UUID orderId){
        var orderEntity = this.orderRepository.findById(orderId).orElseThrow( () -> {
            throw new IllegalArgumentException("Pedido não encontrado. " + orderId);
        });

        List<DeliveryManEntity> deliveryManEntities = this.deliveryManRepository.findByIsAvailable(true);
        if(deliveryManEntities.isEmpty()){
            System.out.println("Nenhum deliveryMan encontrado. " + orderId);
        }

        DeliveryManEntity firstDeliveryManEntity = deliveryManEntities.getFirst();
        orderEntity.setDeliveryManId(firstDeliveryManEntity.getId());
        orderEntity.setStatus(StatusOrder.EM_ROTA);
        this.orderRepository.save(orderEntity);
        firstDeliveryManEntity.setAvailable(false);
        this.deliveryManRepository.save(firstDeliveryManEntity);
    }
}
