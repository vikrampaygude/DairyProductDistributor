package org.product.distributor.services;

import org.product.distributor.constant.ShopkeeperOrderStatus;
import org.product.distributor.dto.DailySellGridDataDTO;
import org.product.distributor.dto.DailySellRowDataDTO;
import org.product.distributor.dto.OrderProductDTO;
import org.product.distributor.dto.ShopkeeperOrderDTO;
import org.product.distributor.dto.search.OrderProductSearchDTO;
import org.product.distributor.mapper.DailySellRowDataMapper;
import org.product.distributor.mapper.OrderProductMapper;
import org.product.distributor.model.*;
import org.product.distributor.repository.*;
import org.springframework.stereotype.Service;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
    private DailySellRowDataMapper dailySellRowDataMapper;
    private ShopkeeperOrderService shopkeeperOrderService;

    public OrderProductService(OrderProductRepo orderProductRepo, OrderProductMapper orderProductMapper, ProductRepo productRepo, ShopkeeperRepo shopkeeperRepo, ShopkeeperOrderRepo shopkeeperOrderRepo, DailySellRowDataMapper dailySellRowDataMapper, ShopkeeperOrderService shopkeeperOrderService) {
        this.orderProductRepo = orderProductRepo;
        this.orderProductMapper = orderProductMapper;
        this.productRepo = productRepo;
        this.shopkeeperRepo = shopkeeperRepo;
        this.shopkeeperOrderRepo = shopkeeperOrderRepo;
        this.dailySellRowDataMapper = dailySellRowDataMapper;
        this.shopkeeperOrderService = shopkeeperOrderService;
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

    /*
        Some scenarios which are going to fail.
        1. If product price is changed any time it should reflect the table result
        2. If new product is added
        3. If custom price is changed
     */
    public void placeEmptyOrderByDistArea(Long distributorAreaId, LocalDate localDate) throws OperationNotSupportedException {

        List<Product> productList = productRepo.findByDistributorAreaList_Id(distributorAreaId);
        List<Shopkeeper> shopkeeperList = shopkeeperRepo.findByDistributorArea_Id(distributorAreaId);

        List<ShopkeeperOrder> shopkeepersOrderList = new ArrayList<>(shopkeeperList.size());

        DistributorArea distributorArea = new DistributorArea();
        distributorArea.setId(distributorAreaId);

        for (Shopkeeper shopkeeper : shopkeeperList) {

            // we have to make sure there is only one order for the give date
            // TODO: one issue if product is added after creating daily order this logic will be blocker
            if(isShopkeeperOrderPresentOnDate(localDate, shopkeeper.getId()))
                throw new OperationNotSupportedException("Can't create multiple orders on same date. " +
                        "Order for date "+localDate +" already present in database");

            ShopkeeperOrder shopkeeperOrder = new ShopkeeperOrder();

            shopkeeperOrder.setShopkeeper(shopkeeper);
            shopkeeperOrder.setStatus(ShopkeeperOrderStatus.NEW.name());
            shopkeeperOrder.setDate(localDate);
            shopkeeperOrder.setDistributorArea(distributorArea);

            shopkeeperOrderRepo.save(shopkeeperOrder);

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

    public DailySellGridDataDTO getDistributorAreaDayOrders(LocalDate date, Long distributorId){

        List<DailySellRowDataDTO> dailySellRowDataDTOS = new ArrayList<>();

        DailySellGridDataDTO dailySellGridDataDTO = null;

        List<Long> shopkeeperOrderIdList = new ArrayList<>();

        List<ShopkeeperOrder> shopkeeperOrderList= shopkeeperOrderRepo.findActiveByDistributorAreaAndDate(date, distributorId);
        shopkeeperOrderList.forEach(so -> shopkeeperOrderIdList.add(so.getId()));

        List<OrderProduct> orderProductList = orderProductRepo.findByShopkeeperOrderIds(shopkeeperOrderIdList);



        shopkeeperOrderList.forEach(shopkeeperOrder -> {
            Predicate<OrderProduct> orderProductPredicate = order -> order.getShopkeeperOrder().getId().equals(shopkeeperOrder.getId());

            DailySellRowDataDTO dailySellRowDataDTO = dailySellRowDataMapper.getDailySellRowDataDTO(shopkeeperOrder
                    ,orderProductList.stream().parallel()
                            .filter(orderProductPredicate)
                            .collect(Collectors.toList())
            );

            dailySellRowDataDTOS.add(dailySellRowDataDTO);
        });

        if(dailySellRowDataDTOS.size() >0 ) {
            dailySellGridDataDTO = new DailySellGridDataDTO();
            dailySellGridDataDTO.setDate(date);
            dailySellGridDataDTO.setDailySellRowDataDTOList(dailySellRowDataDTOS);
        }
        return dailySellGridDataDTO;
    }

    public Boolean isShopkeeperOrderPresentOnDate(LocalDate date, Long shopkeeperId){
        return shopkeeperOrderRepo.countByShopkeeperIdAndDate(date, shopkeeperId) > 0 ;
    }

    public void placeEmptyDayOrders(OrderProductSearchDTO orderProductSearchDTO) throws OperationNotSupportedException {
        placeEmptyOrderByDistArea(orderProductSearchDTO.getDistributorAreaId(), orderProductSearchDTO.getLocalDate());
    }

    public ShopkeeperOrderDTO updateQuanity(OrderProductDTO orderProductDTO) {

        orderProductRepo.updateQuanitity(orderProductDTO.getId(), orderProductDTO.getQuantity());

        return shopkeeperOrderService.calculateAndSaveOrderBill(orderProductDTO.getOrderId());
    }

}
