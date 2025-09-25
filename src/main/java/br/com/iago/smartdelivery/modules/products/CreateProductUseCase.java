package br.com.iago.smartdelivery.modules.products;

import br.com.iago.smartdelivery.modules.products.dto.CreateProductRequest;
import br.com.iago.smartdelivery.modules.products.dto.CreateProductResponse;
import br.com.iago.smartdelivery.modules.products.dto.ProductMapper;
import org.springframework.stereotype.Service;

@Service
public class CreateProductUseCase {

    private ProductsRepository productsRepository;

    public CreateProductUseCase(ProductsRepository productsRepository){
        this.productsRepository = productsRepository;
    }

    public CreateProductResponse execute(CreateProductRequest createProductRequest){
        this.productsRepository.findByCode(createProductRequest.getCode())
                .ifPresent(product -> {
                    throw new IllegalArgumentException("Produto já cadastrado.");
                });

        ProductEntity productEntity = ProductMapper.requestToEntity(createProductRequest);
        this.productsRepository.save(productEntity);
        return ProductMapper.entityToResponse(productEntity);
    }
}
