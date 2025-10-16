package br.com.iago.smartdelivery.modules.deliveryman;

import jakarta.persistence.*;

import java.util.UUID;

@Table
@Entity(name = "delivery_man")
public class DeliveryManEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String document;
    private String phone;

    public DeliveryManEntity(){}

    private DeliveryManEntity(String name, String phone, String document) {
        this.name = name;
        this.phone = phone;
        this.document = document;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public static class Builder {
        private String name;
        private String phone;
        private String document;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder document(String document) {
            this.document = document;
            return this;
        }

        public DeliveryManEntity build() {
            return new DeliveryManEntity(name, document, phone);
        }
    }
}
