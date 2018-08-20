package org.product.distributor.web.rest;

import org.product.distributor.dto.ProductAreaPriceDTO;
import org.product.distributor.services.ProductAreaPriceService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by vikram on 08/08/18.
 */
@CrossOrigin
@RestController("api/product-area-price")
public class ProductAreaPriceController {

    private ProductAreaPriceService productAreaPriceService;

    public ProductAreaPriceController(ProductAreaPriceService productAreaPriceService) {
        this.productAreaPriceService = productAreaPriceService;
    }

    @PostMapping
    public void saveProductAreaPrice(@RequestBody ProductAreaPriceDTO productAreaPriceDTO){
        productAreaPriceService.save(productAreaPriceDTO);
    }
}
