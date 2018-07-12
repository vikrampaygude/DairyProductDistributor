package org.product.distributor.web.rest;

import org.product.distributor.dto.DailySellGridDataDTO;
import org.product.distributor.dto.OrderProductDTO;
import org.product.distributor.dto.ShopkeeperOrderDTO;
import org.product.distributor.dto.search.OrderProductSearchDTO;
import org.product.distributor.services.OrderProductService;
import org.product.distributor.services.ShopkeeperOrderService;
import org.springframework.web.bind.annotation.*;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by vikram on 05/07/18.
 */
@CrossOrigin
@RestController
@RequestMapping("api/order")
public class OrderProductRestController {

    private OrderProductService orderProductService;
    private ShopkeeperOrderService shopkeeperOrderService;

    public OrderProductRestController(OrderProductService orderProductService, ShopkeeperOrderService shopkeeperOrderService) {
        this.orderProductService = orderProductService;
        this.shopkeeperOrderService = shopkeeperOrderService;
    }

    @PostMapping
    public void saveOrderProduct(@RequestBody OrderProductDTO orderProductDTO){
        orderProductService.saveOrderProduct(orderProductDTO);
    }

    @PostMapping("/place-day-orders")
    public void createEmptyDayOrders(@RequestBody OrderProductSearchDTO orderProductSearchDTO) throws OperationNotSupportedException {
        orderProductService.placeEmptyDayOrders(orderProductSearchDTO);
    }

    @PostMapping("/update-quantity")
    public ShopkeeperOrderDTO updateQuantity(@RequestBody OrderProductDTO orderProductDTO){
       return orderProductService.updateQuanity(orderProductDTO);
    }


    @PutMapping
    public void updateOrderProduct(@RequestBody OrderProductDTO orderProductDTO){
        orderProductService.updateOrderProduct(orderProductDTO);
    }

    @PostMapping("/update-paid-amount")
    public ShopkeeperOrderDTO updatePaidAmount(@RequestBody ShopkeeperOrderDTO shopkeeperOrderDTO){
        return shopkeeperOrderService.updatePaidPrice(shopkeeperOrderDTO);
    }


    @GetMapping("/list")
    public List<OrderProductDTO> getOrderProducts(){

        return orderProductService.getAll();
    }

    @GetMapping("/day-orders")
    public DailySellGridDataDTO getDayOrders(@RequestParam String date, @RequestParam Long distributorAreaId){

        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("d/MM/yyyy"));
        return orderProductService.getDistributorAreaDayOrders(localDate, distributorAreaId);
    }


    @GetMapping("/{id}")
    public List<OrderProductDTO> getOrderProduct(){

        return orderProductService.getAll();
    }
}
