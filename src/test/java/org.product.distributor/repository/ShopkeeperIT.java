package org.product.distributor.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.product.distributor.model.Shopkeeper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by vikram on 08/07/18.
 * Test cases depends on Data.sql
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class ShopkeeperIT {

    @Autowired
    private ShopkeeperRepo shopkeeperRepo;

    @Test
    public void findByDistributorArea_Id(){
        //given
        //1 shopkeeper added to distributor area id 1
        //when
        List<Shopkeeper> shopkeeperList = shopkeeperRepo.findByDistributorArea_Id(1L);
        //then
        Assert.assertEquals(shopkeeperList.size(), 1);
    }

}
