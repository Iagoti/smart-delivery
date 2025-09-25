package br.com.iago.smartdelivery.modules.products;

import br.com.iago.smartdelivery.modules.products.dto.ListProductResponse;
import br.com.iago.smartdelivery.modules.products.dto.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private ProductsRepository productsRepository;

    public ProductService(ProductsRepository productsRepository){
        this.productsRepository = productsRepository;
    }

    public List<ListProductResponse> findAll(){
        var products = this.productsRepository.findAll();
        return ProductMapper.toResponse(products);
    }
}
