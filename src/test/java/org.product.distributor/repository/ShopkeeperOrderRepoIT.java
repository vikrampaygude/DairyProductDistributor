package org.product.distributor.repository;

import org.apache.tomcat.jni.Local;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.product.distributor.constant.ShopkeeperOrderStatus;
import org.product.distributor.model.DistributorArea;
import org.product.distributor.model.Shopkeeper;
import org.product.distributor.model.ShopkeeperOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by vikram on 08/07/18.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class ShopkeeperOrderRepoIT {

    @Autowired
    private ShopkeeperOrderRepo shopkeeperOrderRepo;

    @Test
    public void countByShopkeeperIdAndDate(){
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
        Integer orderCount = shopkeeperOrderRepo.countByShopkeeperIdAndDate(nowLocalDate, shopkeeper.getId());
        //then
        Assert.assertEquals(orderCount.intValue() , 1);
    }

    @Test
    public void countByShopkeeperIdAndDateMultiple(){
        //given
        LocalDate nowLocalDate = LocalDate.now();
        ShopkeeperOrder shopkeeperOrder = new ShopkeeperOrder();
        shopkeeperOrder.setDate(nowLocalDate);
        shopkeeperOrder.setStatus(ShopkeeperOrderStatus.NEW.name());

        Shopkeeper shopkeeper = new Shopkeeper();
        shopkeeper.setId(1l);

        shopkeeperOrder.setShopkeeper(shopkeeper);

        ShopkeeperOrder shopkeeperOrder2 = new ShopkeeperOrder();
        shopkeeperOrder2.setDate(nowLocalDate.plusDays(1));
        shopkeeperOrder2.setStatus(ShopkeeperOrderStatus.NEW.name());

        shopkeeperOrder2.setShopkeeper(shopkeeper);

        shopkeeperOrderRepo.save(shopkeeperOrder);
        shopkeeperOrderRepo.save(shopkeeperOrder2);
        //when
        Integer orderCount = shopkeeperOrderRepo.countByShopkeeperIdAndDate(nowLocalDate, shopkeeper.getId());
        //then
        Assert.assertEquals(orderCount.intValue() , 1);
    }


    @Test
    public void findByShopkeeperIdAndDate(){
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
        List<ShopkeeperOrder> shopkeeperOrders = shopkeeperOrderRepo.findByShopkeeperIdAndDate(nowLocalDate, shopkeeper.getId());
        //then
        Assert.assertEquals(shopkeeperOrders.size() , 1);
    }

    @Test
    public void findByShopkeeperIdAndDateMultiple(){
        //given
        LocalDate nowLocalDate = LocalDate.now();
        ShopkeeperOrder shopkeeperOrder = new ShopkeeperOrder();
        shopkeeperOrder.setDate(nowLocalDate);
        shopkeeperOrder.setStatus(ShopkeeperOrderStatus.NEW.name());

        Shopkeeper shopkeeper = new Shopkeeper();
        shopkeeper.setId(1l);

        shopkeeperOrder.setShopkeeper(shopkeeper);

        ShopkeeperOrder shopkeeperOrder2 = new ShopkeeperOrder();
        shopkeeperOrder2.setDate(nowLocalDate.plusDays(1));
        shopkeeperOrder2.setStatus(ShopkeeperOrderStatus.NEW.name());

        shopkeeperOrder2.setShopkeeper(shopkeeper);

        shopkeeperOrderRepo.save(shopkeeperOrder);
        shopkeeperOrderRepo.save(shopkeeperOrder2);
        //when
        List<ShopkeeperOrder> shopkeeperOrders = shopkeeperOrderRepo.findByShopkeeperIdAndDate(nowLocalDate, shopkeeper.getId());
        //then
        Assert.assertEquals(shopkeeperOrders.size() , 1);
    }


    @Test
    public void findActiveByDistributorAreaAndDate(){


        //given

        LocalDate nowLocalDate = LocalDate.now();

        DistributorArea distributorArea = new DistributorArea();
        distributorArea.setId(1L);

        ShopkeeperOrder shopkeeperOrder = new ShopkeeperOrder();
        shopkeeperOrder.setDate(nowLocalDate);
        shopkeeperOrder.setStatus(ShopkeeperOrderStatus.NEW.name());
        shopkeeperOrder.setDistributorArea(distributorArea);

        Shopkeeper shopkeeper = new Shopkeeper();
        shopkeeper.setId(1l);

        shopkeeperOrder.setShopkeeper(shopkeeper);

        ShopkeeperOrder shopkeeperOrder2 = new ShopkeeperOrder();
        shopkeeperOrder2.setDate(nowLocalDate);
        shopkeeperOrder2.setStatus(ShopkeeperOrderStatus.NEW.name());
        shopkeeperOrder2.setDistributorArea(distributorArea);

        shopkeeperOrder2.setShopkeeper(shopkeeper);

        shopkeeperOrderRepo.save(shopkeeperOrder);
        shopkeeperOrderRepo.save(shopkeeperOrder2);
        //when
        List<ShopkeeperOrder> shopkeeperOrders = shopkeeperOrderRepo.findActiveByDistributorAreaAndDate(nowLocalDate, distributorArea.getId());
        //then
        Assert.assertEquals(shopkeeperOrders.size() , 2);
    }


    @Test
    public void findActiveByDistributorAreaAndDateMultiple(){


        //given

        LocalDate nowLocalDate = LocalDate.now();

        DistributorArea distributorArea = new DistributorArea();
        distributorArea.setId(1L);

        ShopkeeperOrder shopkeeperOrder = new ShopkeeperOrder();
        shopkeeperOrder.setDate(nowLocalDate);
        shopkeeperOrder.setStatus(ShopkeeperOrderStatus.NEW.name());
        shopkeeperOrder.setDistributorArea(distributorArea);

        Shopkeeper shopkeeper = new Shopkeeper();
        shopkeeper.setId(1l);

        shopkeeperOrder.setShopkeeper(shopkeeper);

        ShopkeeperOrder shopkeeperOrder2 = new ShopkeeperOrder();
        shopkeeperOrder2.setDate(nowLocalDate.plusDays(1));
        shopkeeperOrder2.setStatus(ShopkeeperOrderStatus.NEW.name());
        shopkeeperOrder2.setDistributorArea(distributorArea);

        shopkeeperOrder2.setShopkeeper(shopkeeper);

        shopkeeperOrderRepo.save(shopkeeperOrder);
        shopkeeperOrderRepo.save(shopkeeperOrder2);
        //when
        List<ShopkeeperOrder> shopkeeperOrders = shopkeeperOrderRepo.findActiveByDistributorAreaAndDate(nowLocalDate, distributorArea.getId());
        //then
        Assert.assertEquals(shopkeeperOrders.size() , 1);
    }


}
