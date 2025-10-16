package br.com.iago.smartdelivery.modules.deliveryman;

import br.com.iago.smartdelivery.modules.deliveryman.dto.CreateDeliveryManRequest;
import br.com.iago.smartdelivery.modules.orders.CreateOrderUseCase;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateDeliveryManUseCase {

    private DeliveryManRepository deliveryManRepository;

    public CreateDeliveryManUseCase(DeliveryManRepository deliveryManRepository){
        this.deliveryManRepository = deliveryManRepository;
    }
    public UUID execute(CreateDeliveryManRequest request){
        this.deliveryManRepository.findByDocument(request.document())
                .ifPresent(deliveryMan -> {
                    throw new IllegalArgumentException("Entregador já cadastrado.");
                });

        DeliveryManEntity deliveryManEntity = new DeliveryManEntity.Builder()
                .name(request.name())
                .phone(request.phone())
                .document(request.document())
                .build();

        deliveryManEntity = this.deliveryManRepository.save(deliveryManEntity);
        return deliveryManEntity.getId();
    }
}
