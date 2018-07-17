package org.product.distributor.services;

import org.product.distributor.constant.ShopkeeperOrderStatus;
import org.product.distributor.dto.order.DailySellGridDataDTO;
import org.product.distributor.dto.order.DailySellRowDataDTO;
import org.product.distributor.dto.OrderProductDTO;
import org.product.distributor.dto.ShopkeeperOrderDTO;
import org.product.distributor.dto.search.OrderProductSearchDTO;
import org.product.distributor.mapper.DailySellRowDataMapper;
import org.product.distributor.mapper.OrderProductMapper;
import org.product.distributor.model.*;
import org.product.distributor.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ProductWeightPriceRepo productWeightPriceRepo;

    @Autowired
    private ShopkeeperCustomPriceService shopkeeperCustomPriceService;

    @Autowired
    private ShopkeeperCustomPriceRepo shopkeeperCustomPriceRepo;

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

        OrderProduct orderProduct = orderProductMapper.mapWithoutCustomPrice(orderProductDTO);
        orderProduct.setSellingPrice(productWeightPriceRepo.findById(orderProductDTO.getProductWeightPriceId()).get().getSellingPrice());
        return orderProductRepo.save(orderProduct);
    }

    public OrderProduct updateOrderProduct(OrderProductDTO orderProductDTO){
        if(orderProductDTO.getId() == null || orderProductDTO.getId() ==0)
            throw new IllegalArgumentException();

        return orderProductRepo.save(orderProductMapper.mapWithoutCustomPrice(orderProductDTO));
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
                OrderProduct orderProduct = OrderProduct.getObject(product, shopkeeperOrder);

                ShopkeeperCustomPrice  shopkeeperCustomPrice = shopkeeperCustomPriceService.getProductCustomPrice(product, shopkeeperOrder.getId());
                if(shopkeeperCustomPrice!=null)
                    orderProduct.setSellingPrice(shopkeeperCustomPrice.getPrice());

                orderProduct.setShopkeeperCustomPrice(shopkeeperCustomPrice);
                orderProductList.add(orderProduct);

            });

            shopkeeperOrder.setOrderProductList(orderProductList);
            orderProductRepo.saveAll(orderProductList);

            shopkeepersOrderList.add(shopkeeperOrder);
        }
        //all ready for save
        shopkeeperOrderRepo.saveAll(shopkeepersOrderList);
    }

    public void applyLatestPrices(Long distributorAreaId, LocalDate localDate) {
        List<ShopkeeperOrder> shopkeepersOrderList = shopkeeperOrderRepo.findActiveByDistributorAreaAndDate(localDate, distributorAreaId);

        for (ShopkeeperOrder shopkeeperOrder : shopkeepersOrderList) {

            List<OrderProduct> orderProductList = orderProductRepo.findByShopkeeperOrderId(shopkeeperOrder.getId());

            Double totalPrices = 0.0;
            for (OrderProduct orderProduct : orderProductList) {
                ShopkeeperCustomPrice shopkeeperCustomPrice = shopkeeperCustomPriceService.getProductCustomPrice(orderProduct.getProduct(), shopkeeperOrder.getId());
                if (shopkeeperCustomPrice != null)
                    orderProduct.setSellingPrice(shopkeeperCustomPrice.getPrice());

                orderProduct.setSellingPrice(orderProduct.getProduct().getSellingPrice());

                totalPrices += (orderProduct.getQuantity() * orderProduct.getSellingPrice());
            }

            shopkeeperOrder.setTotalAmount(totalPrices);

            if(shopkeeperOrder.getPaidAmount() == null)
                shopkeeperOrder.setDueAmount(shopkeeperOrder.getTotalAmount());
            else
                shopkeeperOrder.setDueAmount(shopkeeperOrder.getTotalAmount() - shopkeeperOrder.getPaidAmount());

            orderProductRepo.saveAll(orderProductList);
            shopkeeperOrderRepo.save(shopkeeperOrder);

        }
    }

    public DailySellGridDataDTO getDistributorAreaDayOrders(LocalDate date, Long distributorId){

        List<DailySellRowDataDTO> dailySellRowDataDTOS = new ArrayList<>();

        DailySellGridDataDTO dailySellGridDataDTO = null;

        List<Long> shopkeeperOrderIdList = new ArrayList<>();

        Double grandTotalAmount=0.0, grandTotalPaidAmount=0.0, grandTotalDueAmount=0.0;

        List<ShopkeeperOrder> shopkeeperOrderList= shopkeeperOrderRepo.findActiveByDistributorAreaAndDate(date, distributorId);
        shopkeeperOrderList.forEach(so -> shopkeeperOrderIdList.add(so.getId()));

        List<OrderProduct> orderProductList = orderProductRepo.findByShopkeeperOrderIds(shopkeeperOrderIdList);

        for (ShopkeeperOrder shopkeeperOrder : shopkeeperOrderList) {
            Predicate<OrderProduct> orderProductPredicate = order -> order.getShopkeeperOrder().getId().equals(shopkeeperOrder.getId());

            DailySellRowDataDTO dailySellRowDataDTO = dailySellRowDataMapper.getDailySellRowDataDTO(shopkeeperOrder
                    , orderProductList.stream().parallel()
                            .filter(orderProductPredicate)
                            .collect(Collectors.toList())
            );

            dailySellRowDataDTO.processByWeight();// process and put same product in list.
            dailySellRowDataDTOS.add(dailySellRowDataDTO);

            grandTotalAmount += shopkeeperOrder.getTotalAmount();
            grandTotalPaidAmount += shopkeeperOrder.getPaidAmount();
            grandTotalDueAmount +=shopkeeperOrder.getDueAmount();
        }


        if(dailySellRowDataDTOS.size() >0 ) {
            dailySellGridDataDTO = new DailySellGridDataDTO();
            dailySellGridDataDTO.setDate(date);
            dailySellGridDataDTO.setDailySellRowDataDTOList(dailySellRowDataDTOS);
            dailySellGridDataDTO.calculateTotalRow();

            dailySellGridDataDTO.setGrandTotalAmount(grandTotalAmount);
            dailySellGridDataDTO.setGrandTotalPaidAmount(grandTotalPaidAmount);
            dailySellGridDataDTO.setGrandTotalDueAmount(grandTotalDueAmount);
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

    public void updateSellingPrice(Long orderId, ShopkeeperCustomPrice shopkeeperCustomPrice, Double price) {
        Optional<OrderProduct> orderProductOptional = orderProductRepo.findById(orderId);
        if(orderProductOptional.isPresent()) {
            OrderProduct orderProduct = orderProductOptional.get();
            orderProduct.setSellingPrice(price);
            orderProduct.setShopkeeperCustomPrice(shopkeeperCustomPrice);
            orderProductRepo.save(orderProduct);
            shopkeeperOrderService.calculateAndSaveOrderBill(orderId);

        }
    }

    /**
     * Copy will copy by values
     */
    public void copyOrderFromYesterday(Long orderId) {
        Optional<ShopkeeperOrder> shopkeeperOrderOptional = shopkeeperOrderRepo.findById(orderId);
        if (!shopkeeperOrderOptional.isPresent())
            return;
        ShopkeeperOrder shopkeeperOrder = shopkeeperOrderOptional.get();
        Optional<ShopkeeperOrder> oneByShopkeeperIdAndDate = shopkeeperOrderRepo.findOneByShopkeeperIdAndDate(shopkeeperOrder.getDate().minusDays(1)
                , shopkeeperOrder.getShopkeeper().getId());

        if (!oneByShopkeeperIdAndDate.isPresent())
            return;
        ShopkeeperOrder prevShopkeeperOrder = oneByShopkeeperIdAndDate.get();


        List<OrderProduct> orderProductList = orderProductRepo.findByShopkeeperOrderId(shopkeeperOrder.getId());
        List<OrderProduct> orderProductPrevList = orderProductRepo.findByShopkeeperOrderId(prevShopkeeperOrder.getId());

        for (OrderProduct prevOrderProduct : orderProductPrevList){
            Boolean found = false;
            ProductWeightPrice prevWp = prevOrderProduct.getProductWeightPrice();

            for (OrderProduct orderProduct : orderProductList) {
                ProductWeightPrice wp = orderProduct.getProductWeightPrice();

                if (orderProduct.getProduct().equals(prevOrderProduct.getProduct())
                        && ( (wp ==null && prevWp ==null) || (prevWp!=null && wp != null && prevWp.getId().equals(wp.getId()))
                        )) {
                    orderProduct.setQuantity(prevOrderProduct.getQuantity());
                    orderProduct.setSellingPrice(prevOrderProduct.getSellingPrice());
                    orderProduct.setProductWeightPrice(prevOrderProduct.getProductWeightPrice());
                    orderProduct.setShopkeeperCustomPrice(saveCopyOfCustomPrice(prevOrderProduct.getShopkeeperCustomPrice(), shopkeeperOrder));

                    orderProductRepo.save(orderProduct);

                    found = true;
                }

            }
            if(!found){
                saveCopyOfOrderProduct(prevOrderProduct, shopkeeperOrder);
            }

        }
    }



    /**
     * Thiw will create by yesterday
     */
    public void createDailyOrderAsYesterday(LocalDate date, Long distributorAreaId) throws OperationNotSupportedException {

        LocalDate yesterdaysDate = date.minusDays(1);// make it yesterday

        List<ShopkeeperOrder> shopkeeperOrderList= shopkeeperOrderRepo.findActiveByDistributorAreaAndDate(yesterdaysDate, distributorAreaId);

        if(shopkeeperOrderRepo.countBydistributorAreaIdAndDate(date, distributorAreaId) > 0)
            throw new OperationNotSupportedException("Order already present can't be created");

        shopkeeperOrderList.forEach(so -> {
            List<OrderProduct> orderProductList = orderProductRepo.findByShopkeeperOrderId(so.getId());
            copyNewOrderByShopkeeper(so, orderProductList, date);// for todays date
        });
    }

    public void copyNewOrderByShopkeeper(ShopkeeperOrder so, List<OrderProduct> orderProductList, LocalDate date){
        //first create copy of so

        ShopkeeperOrder newSo = saveCopyOfShopkeeperOrder(so, date);

        List<OrderProduct> newOrderProductList = new ArrayList<>();

        orderProductList.forEach(orderProduct -> {
            newOrderProductList.add(saveCopyOfOrderProduct(orderProduct, newSo));
        });
        orderProductRepo.saveAll(newOrderProductList);

    }

    private OrderProduct saveCopyOfOrderProduct(OrderProduct op, ShopkeeperOrder so) {
        OrderProduct newOp = new OrderProduct();
        newOp.setSellingPrice(op.getSellingPrice());
        newOp.setShopkeeperCustomPrice(saveCopyOfCustomPrice(op.getShopkeeperCustomPrice(), so));
        newOp.setProductWeightPrice(op.getProductWeightPrice());
        newOp.setProduct(op.getProduct());
        newOp.setShopkeeperOrder(op.getShopkeeperOrder());
        newOp.setQuantity(op.getQuantity());
        newOp.setShopkeeperOrder(so);

        return orderProductRepo.save(newOp);

    }


    private ShopkeeperOrder saveCopyOfShopkeeperOrder(ShopkeeperOrder so, LocalDate date) {
        ShopkeeperOrder newSo = new ShopkeeperOrder();
        newSo.setDistributorArea(so.getDistributorArea());
        newSo.setStatus(so.getStatus());
        newSo.setShopkeeper(so.getShopkeeper());
        newSo.setDate(date);
        return shopkeeperOrderRepo.save(newSo);
    }

    private ShopkeeperCustomPrice saveCopyOfCustomPrice(ShopkeeperCustomPrice copyFormCustomPrice, ShopkeeperOrder so){

        if(copyFormCustomPrice == null)
            return null;

        ShopkeeperCustomPrice shopkeeperCustomPrice = new ShopkeeperCustomPrice();
        shopkeeperCustomPrice.setPrice(copyFormCustomPrice.getPrice());
        shopkeeperCustomPrice.setProduct(copyFormCustomPrice.getProduct());
        shopkeeperCustomPrice.setShopkeeperOrder(so);

        return shopkeeperCustomPriceRepo.save(shopkeeperCustomPrice);

    }

}
