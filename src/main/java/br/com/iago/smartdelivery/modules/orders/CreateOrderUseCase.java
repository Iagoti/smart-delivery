package br.com.iago.smartdelivery.modules.orders;

import br.com.iago.smartdelivery.modules.orders.dto.CreateOrderRequest;
import br.com.iago.smartdelivery.modules.orders.dto.CreateOrderResponse;
import br.com.iago.smartdelivery.modules.orders.dto.OrderMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateOrderUseCase {

    private OrderRepository orderRepository;

    public CreateOrderUseCase(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    public CreateOrderResponse execute(CreateOrderRequest createOrderRequest){
        OrderEntity orderEntity = OrderMapper.toEntity(createOrderRequest);
        this.orderRepository.save(orderEntity);
        return new CreateOrderResponse(orderEntity.getId(), orderEntity.getStatus().toString());
    }
}
