package org.product.distributor.web.rest;

import org.product.distributor.dto.ShopkeeperCustomPriceDTO;
import org.product.distributor.dto.order.DailySellGridDataDTO;
import org.product.distributor.services.OrderProductService;
import org.product.distributor.services.ShopkeeperCustomPriceService;
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
    private OrderProductService orderProductService;

    public ShopkeeperCustomPriceRestController(ShopkeeperCustomPriceService shopkeeperCustomPriceService, OrderProductService orderProductService) {
        this.shopkeeperCustomPriceService = shopkeeperCustomPriceService;
        this.orderProductService = orderProductService;
    }

    @PostMapping
    public DailySellGridDataDTO saveShopkeeperCustomPrice(@RequestBody ShopkeeperCustomPriceDTO shopkeeperCustomPriceDTO) throws OperationNotSupportedException {
        shopkeeperCustomPriceService.save(shopkeeperCustomPriceDTO);
        return orderProductService.getGridData(shopkeeperCustomPriceDTO.getShopkeeperOrderId());

    }

    @PutMapping
    public DailySellGridDataDTO updateShopkeeperCustomPrice(@RequestBody ShopkeeperCustomPriceDTO shopkeeperCustomPriceDTO){
        shopkeeperCustomPriceService.update(shopkeeperCustomPriceDTO);
        return orderProductService.getGridData(shopkeeperCustomPriceDTO.getShopkeeperOrderId());
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