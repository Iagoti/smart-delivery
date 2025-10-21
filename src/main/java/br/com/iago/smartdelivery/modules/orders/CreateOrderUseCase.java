package br.com.iago.smartdelivery.modules.orders;

import br.com.iago.smartdelivery.configs.RabbitMQConfig;
import br.com.iago.smartdelivery.modules.customers.CustomerEntity;
import br.com.iago.smartdelivery.modules.customers.CustomerRepository;
import br.com.iago.smartdelivery.modules.orders.dto.CreateOrderRequest;
import br.com.iago.smartdelivery.modules.orders.dto.CreateOrderResponse;
import br.com.iago.smartdelivery.modules.orders.dto.OrderEvent;
import br.com.iago.smartdelivery.modules.orders.dto.OrderMapper;
import br.com.iago.smartdelivery.modules.users.UserEntity;
import br.com.iago.smartdelivery.modules.users.UserRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateOrderUseCase {

    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private CustomerRepository customerRepository;
    private RabbitTemplate rabbitTemplate;

    public CreateOrderUseCase(
            OrderRepository orderRepository,
            UserRepository userRepository,
            CustomerRepository customerRepository,
            RabbitTemplate rabbitTemplate
    ){
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public CreateOrderResponse execute(CreateOrderRequest createOrderRequest){
        String userName =  SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity userEntity = this.userRepository.findByUsername(userName).get();
        CustomerEntity customerEntity = this.customerRepository.findByUserId(userEntity.getId()).get();

        OrderEntity orderEntity = OrderMapper.toEntity(createOrderRequest, customerEntity.getId());
        this.orderRepository.save(orderEntity);

        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_ORDER_CREATED, new OrderEvent(orderEntity.getId().toString()));
        return new CreateOrderResponse(orderEntity.getId(), orderEntity.getStatus().toString());
    }
}
