package org.product.distributor.web.rest;

import org.product.distributor.dto.ShopkeeperDTO;
import org.product.distributor.services.ShopkeeperService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by vikram on 05/07/18.
 */
@CrossOrigin
@RestController
@RequestMapping("api/shopkeeper")
public class ShopkeeperRestController {

    private ShopkeeperService shopkeeperService;

    public ShopkeeperRestController(ShopkeeperService shopkeeperService) {
        this.shopkeeperService = shopkeeperService;
    }

    @PostMapping
    public void saveShopkeeper(@RequestBody ShopkeeperDTO shopkeeperDTO){
        shopkeeperService.saveShopkeeper(shopkeeperDTO);
    }

    @PutMapping
    public void updateShopkeeper(@RequestBody ShopkeeperDTO shopkeeperDTO){
        shopkeeperService.updateShopkeeper(shopkeeperDTO);
    }

    @GetMapping("/list")
    public List<ShopkeeperDTO> getShopkeepers(){

        return shopkeeperService.getAll();
    }

    @GetMapping("/{id}")
    public List<ShopkeeperDTO> getShopkeeper(){

        return shopkeeperService.getAll();
    }
}
