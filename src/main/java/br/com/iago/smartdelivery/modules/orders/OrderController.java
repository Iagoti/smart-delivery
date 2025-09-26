package br.com.iago.smartdelivery.modules.orders;

import br.com.iago.smartdelivery.modules.orders.dto.CreateOrderRequest;
import br.com.iago.smartdelivery.modules.orders.dto.CreateOrderResponse;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/orders")
@RestController
public class OrderController {

    private CreateOrderUseCase createOrderUseCase;

    public OrderController(CreateOrderUseCase createOrderUseCase){
        this.createOrderUseCase = createOrderUseCase;
    }

    @PostMapping
    public CreateOrderResponse create(@RequestBody CreateOrderRequest createOrderRequest) {
        return this.createOrderUseCase.execute(createOrderRequest);
    }
}
