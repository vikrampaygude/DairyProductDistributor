package org.product.distributor.web.rest;

import org.product.distributor.dto.OrderProductDTO;
import org.product.distributor.services.OrderProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by vikram on 05/07/18.
 */
@RestController
@RequestMapping("api/order-product")
public class OrderProductRestController {

    private OrderProductService orderProductService;

    public OrderProductRestController(OrderProductService orderProductService) {
        this.orderProductService = orderProductService;
    }

    @PostMapping
    public void saveOrderProduct(@RequestBody OrderProductDTO orderProductDTO){
        orderProductService.saveOrderProduct(orderProductDTO);
    }

    @PutMapping
    public void updateOrderProduct(@RequestBody OrderProductDTO orderProductDTO){
        orderProductService.updateOrderProduct(orderProductDTO);
    }

    @GetMapping("/list")
    public List<OrderProductDTO> getOrderProducts(){

        return orderProductService.getAll();
    }

    @GetMapping("/{id}")
    public List<OrderProductDTO> getOrderProduct(){

        return orderProductService.getAll();
    }
}
