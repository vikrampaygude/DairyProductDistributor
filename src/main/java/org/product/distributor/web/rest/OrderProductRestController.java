package org.product.distributor.web.rest;

import org.product.distributor.dto.order.DailySellGridDataDTO;
import org.product.distributor.dto.OrderProductDTO;
import org.product.distributor.dto.ShopkeeperOrderDTO;
import org.product.distributor.dto.search.OrderProductSearchDTO;
import org.product.distributor.error.exception.InvalidDailyOrderCreateReqException;
import org.product.distributor.error.exception.InvalidDailyOrderDeleteException;
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
        orderProductService.saveOrderProductByWeight(orderProductDTO);
    }

    @PostMapping("/by-weight")
    public DailySellGridDataDTO saveOrderByWeight(@RequestBody OrderProductDTO orderProductDTO){
        return orderProductService.saveOrderProductByWeight(orderProductDTO);
    }

    @PostMapping("/place-day-orders")
    public void createEmptyDayOrders(@RequestBody OrderProductSearchDTO orderProductSearchDTO) throws OperationNotSupportedException, InvalidDailyOrderCreateReqException {
        orderProductService.placeEmptyDayOrders(orderProductSearchDTO);
    }

    @PostMapping("/update-quantity")
    public void updateQuantity(@RequestBody OrderProductDTO orderProductDTO){
       orderProductService.updateQuanity(orderProductDTO);
    }


    @PutMapping
    public void updateOrderProduct(@RequestBody OrderProductDTO orderProductDTO){
        orderProductService.updateOrderProduct(orderProductDTO);
    }

    @PostMapping("/update-paid-amount")
    public DailySellGridDataDTO updatePaidAmount(@RequestBody ShopkeeperOrderDTO shopkeeperOrderDTO){
        shopkeeperOrderService.updatePaidPrice(shopkeeperOrderDTO);
        return orderProductService.getGridData(shopkeeperOrderDTO.getId());
    }

    @PostMapping("/create-yesterday-copy")
    public void createOrderAsYesterday(@RequestBody OrderProductSearchDTO orderProductSearchDTO) throws OperationNotSupportedException {
        orderProductService.createDailyOrderAsYesterday(orderProductSearchDTO.getLocalDate(), orderProductSearchDTO.getDistributorAreaId());
    }

    @PutMapping("/apply-latest-price")
    public void applyLatestPrices(@RequestBody OrderProductSearchDTO orderProductSearchDTO){

        orderProductService.applyLatestPrices(orderProductSearchDTO.getDistributorAreaId(), orderProductSearchDTO.getLocalDate());
    }



    @GetMapping("/copy-yesterday-order/{orderId}")
    public DailySellGridDataDTO copyYesterdayOrder(@PathVariable Long orderId) throws InvalidDailyOrderCreateReqException {
        orderProductService.copyOrderFromYesterday(orderId);
        return orderProductService.getGridData(orderId);
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

    @DeleteMapping("/day-orders")
    public void deleteDayOrders(@RequestParam String date, @RequestParam Long distributorAreaId) throws InvalidDailyOrderDeleteException {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("d/MM/yyyy"));
        orderProductService.deleteDayOrder(localDate, distributorAreaId);
    }

}
