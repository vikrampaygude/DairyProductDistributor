package org.product.distributor.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.product.distributor.constant.ShopkeeperOrderStatus;
import org.product.distributor.mapper.DailySellRowDataMapper;
import org.product.distributor.mapper.OrderProductMapper;
import org.product.distributor.mapper.ShopkeeperOrderMapper;
import org.product.distributor.model.OrderProduct;
import org.product.distributor.model.Product;
import org.product.distributor.model.Shopkeeper;
import org.product.distributor.model.ShopkeeperOrder;
import org.product.distributor.repository.OrderProductRepo;
import org.product.distributor.repository.ProductRepo;
import org.product.distributor.repository.ShopkeeperOrderRepo;
import org.product.distributor.repository.ShopkeeperRepo;
import org.product.distributor.services.OrderProductService;
import org.product.distributor.services.ShopkeeperOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vikram on 07/07/18.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class OrderProductServiceIT {


    @Autowired
    private OrderProductRepo orderProductRepo;

    private OrderProductMapper orderProductMapper = OrderProductMapper.INSTANCE;

    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private ShopkeeperRepo shopkeeperRepo;
    @Autowired
    private ShopkeeperOrderRepo shopkeeperOrderRepo;

    private OrderProductService orderProductService;

    private DailySellRowDataMapper dailySellRowDataMapper;

    private ShopkeeperOrderService shopkeeperOrderService ;
    private ShopkeeperOrderMapper shopkeeperOrderMapper = ShopkeeperOrderMapper.INSTANCE;



    @Before
    public void setUp(){
        shopkeeperOrderService = new ShopkeeperOrderService(shopkeeperOrderRepo, shopkeeperOrderMapper, orderProductRepo);
        orderProductService = new OrderProductService(orderProductRepo, orderProductMapper,productRepo,shopkeeperRepo, shopkeeperOrderRepo, dailySellRowDataMapper, shopkeeperOrderService);
    }


    @Test
    public void createDailyOrderForAllProducts() throws OperationNotSupportedException {
        //given
        List<Product> productList = productRepo.findByDistributorAreaList_Id(1L);
        List<Shopkeeper> shopkeeperList = shopkeeperRepo.findByDistributorArea_Id(1L);

        //when
        orderProductService.placeEmptyOrderByDistArea(1L, LocalDate.now());
        List<ShopkeeperOrder> shopkeeperOrderList = shopkeeperOrderRepo.findByShopkeeperIdAndDate(LocalDate.now(), 1L);

        List<Long> orderIds = new ArrayList<>();
        shopkeeperOrderList.stream().forEach(shopkeeperOrder -> orderIds.add(shopkeeperOrder.getId()));

        List<OrderProduct> orderProductList = orderProductRepo.findByShopkeeperOrderIds(orderIds);
        //then
        Assert.assertEquals(shopkeeperOrderList.size() , shopkeeperList.size());
        Assert.assertEquals(orderProductList.size() , productList.size() * shopkeeperList.size());

    }

    @Test
    public void isShopkeeperOrderPresentOnDateTrue(){
        //given
        LocalDate nowLocalDate = LocalDate.now();
        ShopkeeperOrder shopkeeperOrder = new ShopkeeperOrder();
        shopkeeperOrder.setDate(nowLocalDate);
        shopkeeperOrder.setStatus(ShopkeeperOrderStatus.NEW.name());

        Shopkeeper shopkeeper = new Shopkeeper();
        shopkeeper.setId(1l);

        shopkeeperOrder.setShopkeeper(shopkeeper);


        shopkeeperOrderRepo.save(shopkeeperOrder);
        //when
        //then
        Assert.assertTrue(orderProductService.isShopkeeperOrderPresentOnDate( nowLocalDate, shopkeeper.getId()));
    }

    @Test
    public void isShopkeeperOrderPresentOnDateFalse(){
        //given
        LocalDate nowLocalDate = LocalDate.now();
        //when
        //then
        Assert.assertFalse(orderProductService.isShopkeeperOrderPresentOnDate( nowLocalDate, 1L));
    }



}
