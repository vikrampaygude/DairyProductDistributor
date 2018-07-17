package org.product.distributor.web.rest;

import org.product.distributor.dto.ShopkeeperCustomPriceDTO;
import org.product.distributor.dto.ShopkeeperDTO;
import org.product.distributor.services.ShopkeeperCustomPriceService;
import org.product.distributor.services.ShopkeeperService;
import org.springframework.web.bind.annotation.*;

import javax.naming.OperationNotSupportedException;
import java.util.List;

/**
 * Created by vikram on 05/07/18.
 *
 */
@CrossOrigin
@RestController
@RequestMapping("api/shopkeeper-custom-price")
public class ShopkeeperCustomPriceRestController {

    private ShopkeeperCustomPriceService shopkeeperCustomPriceService;

    public ShopkeeperCustomPriceRestController(ShopkeeperCustomPriceService shopkeeperCustomPriceService) {
        this.shopkeeperCustomPriceService = shopkeeperCustomPriceService;
    }

    @PostMapping
    public void saveShopkeeperCustomPrice(@RequestBody ShopkeeperCustomPriceDTO shopkeeperCustomPriceDTO) throws OperationNotSupportedException {
        shopkeeperCustomPriceService.save(shopkeeperCustomPriceDTO);
    }

    @PutMapping
    public void updateShopkeeperCustomPrice(@RequestBody ShopkeeperCustomPriceDTO shopkeeperCustomPriceDTO){
        shopkeeperCustomPriceService.update(shopkeeperCustomPriceDTO);
    }

    @GetMapping("/list")
    public List<ShopkeeperCustomPriceDTO> getShopkeeperCustomPrices(){

        return shopkeeperCustomPriceService.getAll();
    }

    @GetMapping("/{id}")
    public ShopkeeperCustomPriceDTO getShopkeeperCustomPrice(@PathVariable Long id){

        return shopkeeperCustomPriceService.getById(id);
    }
}