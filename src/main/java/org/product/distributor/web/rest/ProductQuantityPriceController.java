package org.product.distributor.web.rest;

import org.product.distributor.dto.ProductQuantityPriceDTO;
import org.product.distributor.services.ProductQuantityPriceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by vikram on 05/07/18.
 */
@CrossOrigin
@RestController
@RequestMapping("api/product-quantity-price")
public class ProductQuantityPriceController {

    private ProductQuantityPriceService productQuantityPriceService;

    public ProductQuantityPriceController(ProductQuantityPriceService productQuantityPriceService) {
        this.productQuantityPriceService = productQuantityPriceService;
    }

    @PostMapping
    public void saveProductQuantityPrice(@RequestBody ProductQuantityPriceDTO productQuantityPriceDTO){
        productQuantityPriceService.saveProductQuantityPrice(productQuantityPriceDTO);
    }

    @PutMapping
    public void updateProductQuantityPrice(@RequestBody ProductQuantityPriceDTO productQuantityPriceDTO){
        productQuantityPriceService.updateProductQuantityPrice(productQuantityPriceDTO);
    }


    @GetMapping("{productId}/list")
    public List<ProductQuantityPriceDTO> getProductQuantityPrices(@PathVariable Long productId){
        return productQuantityPriceService.getAllByProductId(productId);
    }

    @GetMapping("/{id}")
    public ProductQuantityPriceDTO getProductQuantityPrice(@PathVariable Long id){
        return productQuantityPriceService.getById(id);
    }




}
