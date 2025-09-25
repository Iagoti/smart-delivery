package br.com.iago.smartdelivery.modules.products;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductsRepository extends JpaRepository<ProductEntity, UUID> {

    Optional<ProductEntity> findByCode(int code);
}
