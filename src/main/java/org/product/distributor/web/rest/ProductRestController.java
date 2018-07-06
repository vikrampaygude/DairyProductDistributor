package org.product.distributor.web.rest;

import org.product.distributor.dto.ProductDTO;
import org.product.distributor.services.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by vikram on 05/07/18.
 */
@RestController
@RequestMapping("api/product")
public class ProductRestController {

    private ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public void saveProduct(@RequestBody ProductDTO productDTO){
        productService.saveProduct(productDTO);
    }

    @PutMapping
    public void updateProduct(@RequestBody ProductDTO productDTO){
        productService.updateProduct(productDTO);
    }

    @GetMapping("/list")
    public List<ProductDTO> getProducts(){

        return productService.getAll();
    }

    @GetMapping("/{id}")
    public List<ProductDTO> getProduct(){

        return productService.getAll();
    }
}
