package org.product.distributor.services;

import org.product.distributor.constant.ShopkeeperOrderStatus;
import org.product.distributor.dto.OrderProductDTO;
import org.product.distributor.mapper.OrderProductMapper;
import org.product.distributor.model.*;
import org.product.distributor.repository.*;
import org.springframework.stereotype.Service;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by vikram on 05/07/18.
 *
 */
@Service
public class OrderProductService {

    private OrderProductRepo orderProductRepo;
    private OrderProductMapper orderProductMapper;
    private ProductRepo productRepo;
    private ShopkeeperRepo shopkeeperRepo;
    private ShopkeeperOrderRepo shopkeeperOrderRepo;

    public OrderProductService(OrderProductRepo orderProductRepo, OrderProductMapper orderProductMapper, ProductRepo productRepo, ShopkeeperRepo shopkeeperRepo, ShopkeeperOrderRepo shopkeeperOrderRepo) {
        this.orderProductRepo = orderProductRepo;
        this.orderProductMapper = orderProductMapper;
        this.productRepo = productRepo;
        this.shopkeeperRepo = shopkeeperRepo;
        this.shopkeeperOrderRepo = shopkeeperOrderRepo;
    }

    public List<OrderProductDTO> getAll(){
        List<OrderProductDTO> orderProductDTOList = new ArrayList<>();
        orderProductRepo.findAll().forEach(d -> orderProductDTOList.add(orderProductMapper.getOrderProductDTO(d)));
        return orderProductDTOList;
    }

    public OrderProductDTO getById(Long id){
        Optional<OrderProduct> orderProductOptional = orderProductRepo.findById(id);
        if(orderProductOptional.isPresent())
            return orderProductMapper.getOrderProductDTO(orderProductOptional.get());
        return null;
    }

    public OrderProduct saveOrderProduct(OrderProductDTO orderProductDTO){
        return orderProductRepo.save(orderProductMapper.getOrderProduct(orderProductDTO));
    }

    public OrderProduct updateOrderProduct(OrderProductDTO orderProductDTO){
        if(orderProductDTO.getId() == null || orderProductDTO.getId() ==0)
            throw new IllegalArgumentException();

        return orderProductRepo.save(orderProductMapper.getOrderProduct(orderProductDTO));
    }

    public void placeEmptyOrderByDistArea(Long distributorAreaId, LocalDate localDate) throws OperationNotSupportedException {

        List<Product> productList = productRepo.findByDistributorAreaList_Id(distributorAreaId);
        List<Shopkeeper> shopkeeperList = shopkeeperRepo.findByDistributorArea_Id(distributorAreaId);

        List<ShopkeeperOrder> shopkeepersOrderList = new ArrayList<>(shopkeeperList.size());

        DistributorArea distributorArea = new DistributorArea();
        distributorArea.setId(distributorAreaId);

        for (Shopkeeper shopkeeper : shopkeeperList) {

            // we have to make sure there is only one order for the give date
            if(isShopkeeperOrderPresentOnDate(localDate, shopkeeper.getId()))
                throw new OperationNotSupportedException("Can't create multiple orders on same date. " +
                        "Order for date "+localDate +" already present in database");

            ShopkeeperOrder shopkeeperOrder = new ShopkeeperOrder();

            shopkeeperOrder.setShopkeeper(shopkeeper);
            shopkeeperOrder.setStatus(ShopkeeperOrderStatus.NEW.name());
            shopkeeperOrder.setDate(localDate);
            shopkeeperOrder.setDistributorArea(distributorArea);

            List<OrderProduct> orderProductList = new ArrayList<>(productList.size());

            productList.forEach(product -> {
                orderProductList.add(OrderProduct.getObject(product, shopkeeperOrder));
            });

            shopkeeperOrder.setOrderProductList(orderProductList);
            orderProductRepo.saveAll(orderProductList);

            shopkeepersOrderList.add(shopkeeperOrder);
        }
        //all ready for save
        shopkeeperOrderRepo.saveAll(shopkeepersOrderList);
    }

    public void getAllOrders(LocalDate date, Long distributorId){
        shopkeeperOrderRepo.findActiveByDistributorAreaAndDate(date, distributorId);
    }

    public Boolean isShopkeeperOrderPresentOnDate(LocalDate date, Long shopkeeperId){
        return shopkeeperOrderRepo.countByShopkeeperIdAndDate(date, shopkeeperId) > 0 ;
    }

}
