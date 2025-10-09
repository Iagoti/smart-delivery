package br.com.iago.smartdelivery.modules.products;

import br.com.iago.smartdelivery.modules.products.dto.CreateProductRequest;
import br.com.iago.smartdelivery.modules.products.dto.ListProductResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/products")
@RestController
public class ProductController {

    CreateProductUseCase createProductUseCase;
    ProductService productService;

    public ProductController(CreateProductUseCase createProductUseCase, ProductService productService){
        this.createProductUseCase = createProductUseCase;
        this.productService = productService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@RequestBody CreateProductRequest createProductRequest){
        try{
            var productCreated = createProductUseCase.execute(createProductRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(productCreated);
        }catch (IllegalArgumentException ex){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getMessage());
        }
    }

    @GetMapping
    public List<ListProductResponse> findAll(){
        return this.productService.findAll();
    }
}
