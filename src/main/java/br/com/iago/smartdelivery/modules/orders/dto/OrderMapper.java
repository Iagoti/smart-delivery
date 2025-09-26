package br.com.iago.smartdelivery.modules.orders.dto;

import br.com.iago.smartdelivery.modules.orders.OrderEntity;
import br.com.iago.smartdelivery.modules.products.ProductEntity;

import java.util.List;
import java.util.UUID;

public class OrderMapper {

    public static OrderEntity toEntity(CreateOrderRequest request){
        OrderEntity order = new OrderEntity();
        order.setCustomerId(request.customerId());

        List<ProductEntity> products = request.productsIds().stream()
                .map(id -> {
                    ProductEntity product = new ProductEntity();
                    product.setId(id);
                    return product;
                }).toList();

        order.setProducts(products);
        return order;
    }
}
