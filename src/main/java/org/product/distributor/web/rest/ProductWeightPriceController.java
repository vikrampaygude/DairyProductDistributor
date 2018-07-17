package org.product.distributor.web.rest;

import org.product.distributor.dto.ProductWeightPriceDTO;
import org.product.distributor.services.ProductWeightPriceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by vikram on 05/07/18.
 */
@CrossOrigin
@RestController
@RequestMapping("api/product-weight-price")
public class ProductWeightPriceController {

    private ProductWeightPriceService productWeightPriceService;

    public ProductWeightPriceController(ProductWeightPriceService productWeightPriceService) {
        this.productWeightPriceService = productWeightPriceService;
    }

    @PostMapping
    public void saveProductWeightPrice(@RequestBody ProductWeightPriceDTO productWeightPriceDTO){
        productWeightPriceService.saveProductWeightPrice(productWeightPriceDTO);
    }

    @PutMapping
    public void updateProductWeightPrice(@RequestBody ProductWeightPriceDTO productWeightPriceDTO){
        productWeightPriceService.updateProductWeightPrice(productWeightPriceDTO);
    }


    @GetMapping("{productId}/list")
    public List<ProductWeightPriceDTO> getProductWeightPrices(@PathVariable Long productId){
        return productWeightPriceService.getAllByProductId(productId);
    }

    @GetMapping("/{id}")
    public ProductWeightPriceDTO getProductWeightPrice(@PathVariable Long id){
        return productWeightPriceService.getById(id);
    }




}
