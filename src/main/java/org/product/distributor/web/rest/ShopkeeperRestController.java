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

    @GetMapping("/list/{distributorAreaId}")
    public List<ShopkeeperDTO> getShopkeepers(@PathVariable Long distributorAreaId){

        return shopkeeperService.getAll(distributorAreaId);
    }

    @GetMapping("/list-distributor-area/{distributorAreaId}")
    public List<ShopkeeperDTO> getShopkeepersByArea(@PathVariable Long distributorAreaId){
        return shopkeeperService.getAll(distributorAreaId);
    }


    @GetMapping("/{id}")
    public ShopkeeperDTO getShopkeeper(@PathVariable Long id){

        return shopkeeperService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void markDeleted(@PathVariable Long id){
        shopkeeperService.markDelete(id);
    }

}