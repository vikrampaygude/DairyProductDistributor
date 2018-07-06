package org.product.distributor.mapper;

import org.junit.Assert;
import org.junit.Test;
import org.product.distributor.dto.ShopkeeperDTO;
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
        //when
        ShopkeeperDTO shopkeeperDTO = shopkeeperMapper.getShopkeeperDTO(shopkeeper);
        //then
        Assert.assertEquals(shopkeeper.getId(), shopkeeperDTO.getId());
        Assert.assertEquals(shopkeeper.getName(), shopkeeperDTO.getName());
        Assert.assertEquals(shopkeeper.getAddress(), shopkeeperDTO.getAddress());
    }

    @Test
    public void getShopkeeper(){
        //given
        ShopkeeperDTO shopkeeperDTO = new ShopkeeperDTO();
        shopkeeperDTO.setId(111L);
        shopkeeperDTO.setName("Ashapura super market");
        shopkeeperDTO.setAddress("In from of Lake town, Katraj, Pune");
        //when
        Shopkeeper shopkeeper = shopkeeperMapper.getShopkeeper(shopkeeperDTO);
        //then
        Assert.assertEquals(shopkeeperDTO.getId(), shopkeeper.getId());
        Assert.assertEquals(shopkeeperDTO.getName(), shopkeeper.getName());
        Assert.assertEquals(shopkeeperDTO.getAddress(), shopkeeper.getAddress());
    }
}
