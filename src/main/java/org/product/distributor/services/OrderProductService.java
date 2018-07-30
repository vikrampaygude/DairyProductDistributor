package org.product.distributor.services;

import org.product.distributor.constant.ShopkeeperOrderStatus;
import org.product.distributor.dto.ShopkeeperBillDTO;
import org.product.distributor.dto.order.DailySellGridDataDTO;
import org.product.distributor.dto.order.DailySellRowDataDTO;
import org.product.distributor.dto.OrderProductDTO;
import org.product.distributor.dto.search.OrderProductSearchDTO;
import org.product.distributor.error.exception.InvalidDailyOrderCreateReqException;
import org.product.distributor.error.exception.InvalidDailyOrderDeleteException;
import org.product.distributor.error.exception.InvalidOperationException;
import org.product.distributor.mapper.DailySellRowDataMapper;
import org.product.distributor.mapper.OrderProductMapper;
import org.product.distributor.mapper.ProductMapper;
import org.product.distributor.model.*;
import org.product.distributor.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by vikram on 05/07/18.
 *
 */
@Service
@Transactional
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
    private ShopkeeperCustomPriceRepo shopkeeperCustomPriceRepo;

    @Autowired
    private ShopkeeperBillService shopkeeperBillService;

    @Autowired
    private ProductMapper productMapper;

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

    public void saveOrderProductByWeight(OrderProductDTO orderProductDTO) throws InvalidOperationException {

        OrderProduct orderProduct = null;

        Optional<ProductWeightPrice> productWeightPrice = productWeightPriceRepo.findById(orderProductDTO.getProductWeightPriceId());

        if((productWeightPrice.get().getProduct().getId() != orderProductDTO.getProductId())){
            throw new InvalidOperationException("Product id of weight price does not match order product id");
        }

        //verify if by weight already present
        Optional<OrderProduct> orderProductOptional = orderProductRepo.findByWieght(orderProductDTO.getOrderId(), orderProductDTO.getProductId(), orderProductDTO.getProductWeightPriceId());

        if(orderProductOptional.isPresent()) // present update existing one.
            orderProduct = orderProductOptional.get();
        else // create new one.
            orderProduct = orderProductMapper.mapWithoutCustomPrice(orderProductDTO);
        orderProduct.setSellingPrice(productWeightPrice.get().getSellingPrice());
        orderProduct.setQuantity(orderProductDTO.getQuantity());
        orderProduct.setProductWeightPrice(productWeightPrice.get());

        orderProductRepo.save(orderProduct);

        shopkeeperOrderService.calculateAndSaveOrderBill(orderProductDTO.getOrderId());

    }

    public DailySellGridDataDTO getGridData(Long shopkeeperOrderId){
        ShopkeeperOrder shopkeeperOrder = shopkeeperOrderRepo.getOne(shopkeeperOrderId);

        return getDistributorAreaDayOrders(shopkeeperOrder.getDate(), shopkeeperOrder.getDistributorArea().getId());

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

                ShopkeeperCustomPrice  shopkeeperCustomPrice = getProductCustomPrice(product, shopkeeperOrder.getId());
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

    private ShopkeeperCustomPrice getProductCustomPrice(Product product, Long shopkeeperOrderId){
        Optional<ShopkeeperCustomPrice> shopkeeperCustomPrice = shopkeeperCustomPriceRepo.findByShopkeeperOrderAndProduct(product.getId(), shopkeeperOrderId);
        return shopkeeperCustomPrice.orElse(null);
    }


    public void applyLatestPrices(Long distributorAreaId, LocalDate localDate) {
        List<ShopkeeperOrder> shopkeepersOrderList = shopkeeperOrderRepo.findActiveByDistributorAreaAndDate(localDate, distributorAreaId);

        for (ShopkeeperOrder shopkeeperOrder : shopkeepersOrderList) {

            List<OrderProduct> orderProductList = orderProductRepo.findByShopkeeperOrderId(shopkeeperOrder.getId());

            Double totalPrices = 0.0;
            for (OrderProduct orderProduct : orderProductList) {
                ShopkeeperCustomPrice shopkeeperCustomPrice = getProductCustomPrice(orderProduct.getProduct(), shopkeeperOrder.getId());
                if (shopkeeperCustomPrice != null)
                    orderProduct.setSellingPrice(shopkeeperCustomPrice.getPrice());


                orderProduct.setSellingPrice( getProductSellingPrice(orderProduct) );

                totalPrices += (orderProduct.getQuantity() * orderProduct.getSellingPrice());
            }

            shopkeeperOrder.setTotalAmount(totalPrices);

            if(shopkeeperOrder.getPaidAmount() == null)
                shopkeeperOrder.setDueAmount(shopkeeperOrder.getTotalAmount());
            else
                shopkeeperOrder.setDueAmount(shopkeeperOrder.getTotalAmount() - shopkeeperOrder.getPaidAmount());

            orderProductRepo.saveAll(orderProductList);
            shopkeeperOrderRepo.save(shopkeeperOrder);

            shopkeeperOrderService.calculateAndSaveOrderBill(shopkeeperOrder.getId());
        }
    }

    public Double getProductSellingPrice(OrderProduct orderProduct){
        if(orderProduct.getProductWeightPrice() !=null)
            return orderProduct.getProductWeightPrice().getSellingPrice();
        else
            return orderProduct.getProduct().getSellingPrice();
    }

    public DailySellGridDataDTO getDistributorAreaDayOrders(LocalDate date, Long distributorAreaId){

        List<DailySellRowDataDTO> dailySellRowDataDTOS = new ArrayList<>();

        DailySellGridDataDTO dailySellGridDataDTO = null;

        List<Long> shopkeeperOrderIdList = new ArrayList<>();

        Double grandTotalAmount=0.0, grandTotalPaidAmount=0.0, grandTotalDueAmount=0.0;

        List<ShopkeeperOrder> shopkeeperOrderList= shopkeeperOrderRepo.findActiveByDistributorAreaAndDate(date, distributorAreaId);

        if( shopkeeperOrderList.size() ==0 )
            return null;

        shopkeeperOrderList.forEach(so -> shopkeeperOrderIdList.add(so.getId()));

        List<OrderProduct> orderProductList = orderProductRepo.findByShopkeeperOrderIds(shopkeeperOrderIdList);

        for (ShopkeeperOrder shopkeeperOrder : shopkeeperOrderList) {
            Predicate<OrderProduct> orderProductPredicate = order -> order.getShopkeeperOrder().getId().equals(shopkeeperOrder.getId());

            List<OrderProduct> orderProducts = orderProductList.stream()
                    .filter(orderProductPredicate)
                    .collect(Collectors.toList());
            loadWeightPrice(orderProducts);


            DailySellRowDataDTO dailySellRowDataDTO = dailySellRowDataMapper.getDailySellRowDataDTO(shopkeeperOrder
                    , orderProducts);

            Long shopkeeperId = shopkeeperOrder.getShopkeeper().getId();

            ShopkeeperBillDTO grandBill = shopkeeperBillService.getByShopkeeperAndDate(date, shopkeeperId);
            if(grandBill == null){
                shopkeeperBillService.saveShopkeeperBill(date, shopkeeperId, shopkeeperOrder.getTotalAmount(), shopkeeperOrder.getPaidAmount());
                grandBill = shopkeeperBillService.getByShopkeeperAndDate(date, shopkeeperId);//pull again
            }
            if(grandBill !=null) {
                dailySellRowDataDTO.setBillTotalPrice(grandBill.getGrandTotal());
                dailySellRowDataDTO.setBillPaidPrice(grandBill.getGrandPaid());
                dailySellRowDataDTO.setBillDuePrice(grandBill.getGrandDue());
            }

            dailySellRowDataDTO.processByWeight();// process and put same product in list.
            dailySellRowDataDTOS.add(dailySellRowDataDTO);

            grandTotalAmount += grandBill.getGrandTotal();//shopkeeperOrder.getTotalAmount();
            grandTotalPaidAmount += grandBill.getGrandPaid();//
            grandTotalDueAmount +=grandBill.getGrandDue();

        }


        if(dailySellRowDataDTOS.size() >0 ) {
            dailySellGridDataDTO = new DailySellGridDataDTO();
            dailySellGridDataDTO.setDate(date);
            dailySellGridDataDTO.setDailySellRowDataDTOList(dailySellRowDataDTOS);
            dailySellGridDataDTO.calculateTotalRow();

            dailySellGridDataDTO.setGrandTotalAmount(grandTotalAmount);
            dailySellGridDataDTO.setGrandTotalPaidAmount(grandTotalPaidAmount);
            dailySellGridDataDTO.setGrandTotalDueAmount(grandTotalDueAmount);

            dailySellGridDataDTO.setHasFutureOrder(hasFutureOrder(date, distributorAreaId));

            dailySellGridDataDTO.setProductDTOS(productMapper.map(productRepo.findByDistributorAreaList_Id(distributorAreaId)));
        }
        return dailySellGridDataDTO;
    }

    private void loadWeightPrice(List<OrderProduct> orderProducts) {
        orderProducts.stream().parallel().forEach(orderProduct -> {
            if(orderProduct.getProductWeightPrice()!=null) {
                Long id = orderProduct.getProductWeightPrice().getId();
                Optional<ProductWeightPrice> one = productWeightPriceRepo.findById(id);
                orderProduct.setProductWeightPrice(one.get());
            }

        });
    }


    public Boolean isShopkeeperOrderPresentOnDate(LocalDate date, Long shopkeeperId){
        return shopkeeperOrderRepo.countByShopkeeperIdAndDate(date, shopkeeperId) > 0 ;
    }

    public void placeEmptyDayOrders(OrderProductSearchDTO orderProductSearchDTO) throws OperationNotSupportedException, InvalidDailyOrderCreateReqException {
        if(isValidCreateRequest(orderProductSearchDTO.getLocalDate()) == false)
            throw new InvalidDailyOrderCreateReqException("Not allowed to create order on date "+orderProductSearchDTO.getLocalDate() +" wait until day before to create this order.");
        placeEmptyOrderByDistArea(orderProductSearchDTO.getDistributorAreaId(), orderProductSearchDTO.getLocalDate());
    }

    public void updateQuanity(OrderProductDTO orderProductDTO) {

        orderProductRepo.updateQuanitity(orderProductDTO.getId(), orderProductDTO.getQuantity());

        shopkeeperOrderService.calculateAndSaveOrderBill(orderProductDTO.getOrderId());

    }

    public void updateSellingPrice(Long orderId, ShopkeeperCustomPrice shopkeeperCustomPrice, Double price) {
        Optional<OrderProduct> orderProductOptional = orderProductRepo.findById(orderId);
        if(orderProductOptional.isPresent()) {
            OrderProduct orderProduct = orderProductOptional.get();
            orderProduct.setSellingPrice(price);
            orderProduct.setShopkeeperCustomPrice(shopkeeperCustomPrice);
            orderProductRepo.save(orderProduct);
            shopkeeperOrderService.calculateAndSaveOrderBill(orderProduct.getShopkeeperOrder().getId());

        }
    }

    /**
     * Copy will copy by values
     */
    public void copyOrderFromYesterday(Long orderId) throws InvalidDailyOrderCreateReqException {

        Optional<ShopkeeperOrder> shopkeeperOrderOptional = shopkeeperOrderRepo.findById(orderId);
        if (!shopkeeperOrderOptional.isPresent())
            return;

        if(isValidCreateRequest(shopkeeperOrderOptional.get().getDate()) == false)
            throw new InvalidDailyOrderCreateReqException("Not allowed to create order on date "+shopkeeperOrderOptional.get().getDate() +" wait until day before to create this order.");


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

        shopkeeperOrderService.calculateAndSaveOrderBill(shopkeeperOrder.getId());
    }



    /**
     * Thiw will create by yesterday
     */
    public void createDailyOrderAsYesterday(LocalDate date, Long distributorAreaId) throws OperationNotSupportedException, InvalidDailyOrderCreateReqException {

        if(isValidCreateRequest(date) == false)
            throw new InvalidDailyOrderCreateReqException("Not allowed to create order on date "+date.toString() +" wait until day before to create this order.");

        LocalDate yesterdaysDate = date.minusDays(1);// make it yesterday

        List<ShopkeeperOrder> shopkeeperOrderList= shopkeeperOrderRepo.findActiveByDistributorAreaAndDate(yesterdaysDate, distributorAreaId);

        if(shopkeeperOrderRepo.countBydistributorAreaIdAndDate(date, distributorAreaId) > 0)
            throw new OperationNotSupportedException("Order already present can't be created");

        shopkeeperOrderList.forEach(so -> {
            List<OrderProduct> orderProductList = orderProductRepo.findByShopkeeperOrderId(so.getId());
            ShopkeeperOrder newSo = copyNewOrderByShopkeeper(so, orderProductList, date);// for todays date
            shopkeeperOrderService.calculateAndSaveOrderBill(newSo.getId()); //calculate due and bill details
        });

    }

    public ShopkeeperOrder copyNewOrderByShopkeeper(ShopkeeperOrder so, List<OrderProduct> orderProductList, LocalDate date){
        //first create copy of so

        ShopkeeperOrder newSo = saveCopyOfShopkeeperOrder(so, date);

        List<OrderProduct> newOrderProductList = new ArrayList<>();

        orderProductList.forEach(orderProduct -> {
            newOrderProductList.add(saveCopyOfOrderProduct(orderProduct, newSo));
        });
        orderProductRepo.saveAll(newOrderProductList);

        return newSo;
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

    public void deleteByShopkeeperOrderId(Long shopkeeperOrderId){
        orderProductRepo.deleteByShopkeeperOrderId(shopkeeperOrderId);
    }

    public void deleteDayOrder(LocalDate date, Long distributorAreaId) throws InvalidDailyOrderDeleteException {
        //sequence of delete
        /**
         * 1. delete SHOPKEEPER_BILL
         * 2. delete SHOPKEEPER_CUSTOM_PRICE
         * 3. delete ORDER_PRODUCT
         * 4. delete SHOPKEEPER_ORDER
         */

        //TODO: Validation Can not delete this if date is in past or future date has a orders
        //TODO: Some questions if they do not use it for one day this applicaiton how should be due date calculation will be done

        if(hasFutureOrder(date, distributorAreaId)){
            throw new InvalidDailyOrderDeleteException("Day order can't be deleted as future order present and depend on this order for due calculation.");
        }

        //all soft deletesƒ
        shopkeeperOrderRepo.deleteByDistributorAreaAndDate(date, distributorAreaId);
        shopkeeperBillService.deleteByDate(date);

        List<ShopkeeperOrder> shopkeeperOrderList= shopkeeperOrderRepo.findActiveByDistributorAreaAndDate(date, distributorAreaId);

        /*List<ShopkeeperOrder> shopkeeperOrderList= shopkeeperOrderRepo.findActiveByDistributorAreaAndDate(date, distributorAreaId);

        shopkeeperOrderList.forEach(shopkeeperOrder -> {
            Long shopkeeperId = shopkeeperOrder.getShopkeeper().getId();
            shopkeeperBillService.deleteByDateAndShopkeeperId(date, shopkeeperId);
            shopkeeperCustomPriceRepo.deleteByShopkeeperOrderId(shopkeeperOrder.getId());
            deleteByShopkeeperOrderId(shopkeeperOrder.getId());
            shopkeeperOrderService.deleteById(shopkeeperOrder.getId());
        });*/
    }

    public Boolean hasFutureOrder(LocalDate localDate, Long distributorAreaId) {
        LocalDate nextDay = localDate.plusDays(1);
        return shopkeeperOrderRepo.countBydistributorAreaIdAndDate(nextDay, distributorAreaId) >0;
    }

    public Boolean isValidCreateRequest(LocalDate localDate){

        LocalDate today = LocalDate.now();
        if(today.until(localDate, ChronoUnit.DAYS) < 2) {
            return true;
        }
        return false;
    }
}
