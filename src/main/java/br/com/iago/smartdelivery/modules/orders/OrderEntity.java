package br.com.iago.smartdelivery.modules.orders;

import br.com.iago.smartdelivery.modules.customers.CustomerEntity;
import br.com.iago.smartdelivery.modules.deliveryman.DeliveryManEntity;
import br.com.iago.smartdelivery.modules.products.ProductEntity;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "customer_id")
    private UUID customerId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false, insertable = false, updatable = false)
    private CustomerEntity customer;

    @ManyToMany
    @JoinTable(
            name = "orders_products",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<ProductEntity> products;

    @Column(name="deliveryman_id")
    private UUID deliveryManId;

    @ManyToOne
    @JoinColumn(name = "deliveryman_id", insertable = false, updatable = false)
    private DeliveryManEntity deliveryManEntity;

    @Enumerated(EnumType.STRING)
    private StatusOrder status = StatusOrder.CRIADO;

    public OrderEntity(){}

    public OrderEntity(UUID customerId, CustomerEntity customer, List<ProductEntity> products, StatusOrder status) {
        this.customerId = customerId;
        this.customer = customer;
        this.products = products;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public List<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products;
    }

    public StatusOrder getStatus() {
        return status;
    }

    public void setStatus(StatusOrder status) {
        this.status = status;
    }

    public UUID getDeliveryManId() {
        return deliveryManId;
    }

    public void setDeliveryManId(UUID deliveryManId) {
        this.deliveryManId = deliveryManId;
    }

    public DeliveryManEntity getDeliveryManEntity() {
        return deliveryManEntity;
    }

    public void setDeliveryManEntity(DeliveryManEntity deliveryManEntity) {
        this.deliveryManEntity = deliveryManEntity;
    }
}
