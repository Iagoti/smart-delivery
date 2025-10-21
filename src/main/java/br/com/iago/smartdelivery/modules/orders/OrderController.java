package br.com.iago.smartdelivery.modules.orders;

import br.com.iago.smartdelivery.modules.orders.dto.CreateOrderRequest;
import br.com.iago.smartdelivery.modules.orders.dto.CreateOrderResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/orders")
@RestController
public class OrderController {

    private CreateOrderUseCase createOrderUseCase;
    private DeliveredUseCase deliveredUseCase;

    public OrderController(CreateOrderUseCase createOrderUseCase, DeliveredUseCase deliveredUseCase){
        this.createOrderUseCase = createOrderUseCase;
        this.deliveredUseCase = deliveredUseCase;
    }

    @PostMapping
    public CreateOrderResponse create(@RequestBody CreateOrderRequest createOrderRequest) {
        return this.createOrderUseCase.execute(createOrderRequest);
    }

    @PutMapping("/delivered/{orderId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String delivered(@PathVariable UUID orderId){
        this.deliveredUseCase.execute(orderId);
        return "Pedido entregue com sucesso!";
    }
}
