package org.product.distributor.web.rest;

import org.product.distributor.dto.ProductAreaPriceDTO;
import org.product.distributor.services.ProductAreaPriceService;
import org.springframework.web.bind.annotation.*;

/**
 * Created by vikram on 08/08/18.
 */
@CrossOrigin
@RestController
@RequestMapping("api/product-area-price")
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
