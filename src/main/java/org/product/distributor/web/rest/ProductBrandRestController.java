package org.product.distributor.web.rest;

import org.product.distributor.dto.ProductBrandDTO;
import org.product.distributor.services.ProductBrandService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by vikram on 05/07/18.
 */
@CrossOrigin
@RestController
@RequestMapping("api/product-brand")
public class ProductBrandRestController {

    private ProductBrandService productBrandService;

    public ProductBrandRestController(ProductBrandService productBrandService) {
        this.productBrandService = productBrandService;
    }

    @PostMapping
    public void saveProductBrand(@RequestBody ProductBrandDTO productBrandDTO){
        productBrandService.saveProductBrand(productBrandDTO);
    }

    @PutMapping
    public void updateProductBrand(@RequestBody ProductBrandDTO productBrandDTO){
        productBrandService.updateProductBrand(productBrandDTO);
    }

    @GetMapping("/list")
    public List<ProductBrandDTO> getProductBrands(){

        return productBrandService.getAll();
    }

    @GetMapping("/{id}")
    public ProductBrandDTO getProductBrand(@PathVariable Long id){

        return productBrandService.getById(id);
    }
}
