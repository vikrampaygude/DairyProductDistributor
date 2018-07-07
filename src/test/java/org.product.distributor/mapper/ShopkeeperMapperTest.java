package org.product.distributor.mapper;

import org.junit.Assert;
import org.junit.Test;
import org.product.distributor.dto.ShopkeeperDTO;
import org.product.distributor.model.Distributor;
import org.product.distributor.model.DistributorArea;
import org.product.distributor.model.Shopkeeper;

/**
 * Created by vikram on 04/07/18.
 * Tests
 */
public class ShopkeeperMapperTest {

    private ShopkeeperMapper shopkeeperMapper = ShopkeeperMapper.INSTANCE;

    @Test
    public void getShopkeeperDTO(){
        //given
        Shopkeeper shopkeeper = new Shopkeeper();
        shopkeeper.setId(1001L);
        shopkeeper.setName("Ashapura super market");
        shopkeeper.setAddress("In from of Lake town, Katraj, Pune");

        DistributorArea distributorArea = new DistributorArea();
        distributorArea.setId(101L);
        distributorArea.setName("Katraj Line");

        Distributor distributor = new Distributor();
        distributor.setId(1L);
        distributor.setName("Sai Dairy");

        distributorArea.setDistributor(distributor);
        shopkeeper.setDistributorArea(distributorArea);


        //when
        ShopkeeperDTO shopkeeperDTO = shopkeeperMapper.getShopkeeperDTO(shopkeeper);
        //then
        Assert.assertEquals(shopkeeper.getId(), shopkeeperDTO.getId());
        Assert.assertEquals(shopkeeper.getName(), shopkeeperDTO.getName());
        Assert.assertEquals(shopkeeper.getAddress(), shopkeeperDTO.getAddress());
        Assert.assertEquals(shopkeeper.getDistributorArea().getName(), shopkeeperDTO.getDistributorAreaName());
        Assert.assertEquals(shopkeeper.getDistributorArea().getId(), shopkeeperDTO.getDistributorAreaId());
        Assert.assertEquals(shopkeeper.getDistributorArea().getDistributor().getName(), shopkeeperDTO.getDistributorName());
        Assert.assertEquals(shopkeeper.getDistributorArea().getDistributor().getId(), shopkeeperDTO.getDistributorId());

    }

    @Test
    public void getShopkeeper(){
        //given
        ShopkeeperDTO shopkeeperDTO = new ShopkeeperDTO();
        shopkeeperDTO.setId(111L);
        shopkeeperDTO.setName("Ashapura super market");
        shopkeeperDTO.setAddress("In from of Lake town, Katraj, Pune");
        shopkeeperDTO.setDistributorAreaId(1L);
        shopkeeperDTO.setDistributorAreaName("Katraj Line");
        shopkeeperDTO.setDistributorAreaId(101L);
        shopkeeperDTO.setDistributorId(11L);
        shopkeeperDTO.setDistributorName("Sai Dairy");
        //when
        Shopkeeper shopkeeper = shopkeeperMapper.getShopkeeper(shopkeeperDTO);
        //then
        Assert.assertEquals(shopkeeperDTO.getId(), shopkeeper.getId());
        Assert.assertEquals(shopkeeperDTO.getName(), shopkeeper.getName());
        Assert.assertEquals(shopkeeperDTO.getAddress(), shopkeeper.getAddress());

        Assert.assertEquals(shopkeeper.getDistributorArea().getName(), shopkeeperDTO.getDistributorAreaName());
        Assert.assertEquals(shopkeeper.getDistributorArea().getId(), shopkeeperDTO.getDistributorAreaId());
        Assert.assertEquals(shopkeeper.getDistributorArea().getDistributor().getName(), shopkeeperDTO.getDistributorName());
        Assert.assertEquals(shopkeeper.getDistributorArea().getDistributor().getId(), shopkeeperDTO.getDistributorId());
    }
}
