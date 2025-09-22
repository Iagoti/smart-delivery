package br.com.iago.smartdelivery.modules.products;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "procucts")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String description;
    private double price;
}
