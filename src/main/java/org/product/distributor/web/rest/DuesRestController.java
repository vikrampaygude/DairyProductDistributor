package org.product.distributor.web.rest;

import org.product.distributor.dto.ShopkeeperOrderDTO;
import org.product.distributor.services.DuesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by vikram on 16/07/18.
 */
@CrossOrigin
@RestController
@RequestMapping("api/dues")
public class DuesRestController {


    private DuesService duesService;

    public DuesRestController(DuesService duesService) {
        this.duesService = duesService;
    }

    @GetMapping("/shopkeeper/{id}")
    public List<ShopkeeperOrderDTO> getDuesByShopkeeper(@PathVariable Long shopkeeperId){
        return duesService.getDuesByShopkeeperId(shopkeeperId);
    }

    @PutMapping("/update-due")
    public void updateDue(@RequestBody ShopkeeperOrderDTO shopkeeperOrderDTO){

    }

    @PutMapping("/shopkeeper-clear-dues/{id}")
    public void clearAllDues(@PathVariable Long shopkeeperId){

    }
}
