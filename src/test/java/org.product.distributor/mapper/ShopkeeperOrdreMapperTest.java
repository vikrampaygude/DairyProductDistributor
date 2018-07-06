package org.product.distributor.mapper;

import static org.junit.Assert.*;

import org.junit.Test;
import org.product.distributor.dto.ShopkeeperOrderDTO;
import org.product.distributor.model.Shopkeeper;
import org.product.distributor.model.ShopkeeperOrder;

import java.time.LocalDate;

/**
 * Created by vikram on 05/07/18.
 *
 */
public class ShopkeeperOrdreMapperTest {

    private ShopkeeperOrderMapper shopkeeperOrderMapper = ShopkeeperOrderMapper.INSTANCE;

    @Test
    public void getShopkeeperOrderDTO() {

        //given
        ShopkeeperOrder shopkeeperOrder = new ShopkeeperOrder();

        shopkeeperOrder.setId(1111L);
        shopkeeperOrder.setDate(LocalDate.now());
        shopkeeperOrder.setDueAmount(105450.00);
        shopkeeperOrder.setTotalAmount(1000000.00);
        shopkeeperOrder.setPaidAmount(894550.00);//1000000.00 - 105450.00 = 894550.00
        shopkeeperOrder.setStatus("IN PROGRESS");

        Shopkeeper shopkeeper = new Shopkeeper();
        shopkeeper.setId(111L);
        shopkeeper.setName("Ashapura super market!");

        shopkeeperOrder.setShopkeeper(shopkeeper);

        //when
        ShopkeeperOrderDTO shopkeeperOrderDTO = shopkeeperOrderMapper.getShopkeeperOrderDTO(shopkeeperOrder);

        //then
        //make sure data is correct
        assertEquals(shopkeeperOrderDTO.getTotalAmount(), (Double) (shopkeeperOrderDTO.getDueAmount() + shopkeeperOrderDTO.getPaidAmount()) );
        assertEquals(shopkeeperOrderDTO.getDueAmount(), (Double) (shopkeeperOrderDTO.getTotalAmount() - shopkeeperOrderDTO.getPaidAmount()) );
        assertEquals(shopkeeperOrderDTO.getPaidAmount(), (Double) (shopkeeperOrderDTO.getTotalAmount() - shopkeeperOrderDTO.getDueAmount()) );

        //Verify mapping data
        assertEquals(shopkeeperOrder.getId(), shopkeeperOrderDTO.getId());
        assertEquals(shopkeeperOrder.getDate(), shopkeeperOrderDTO.getDate());
        assertEquals(shopkeeperOrder.getDueAmount(), shopkeeperOrderDTO.getDueAmount());
        assertEquals(shopkeeperOrder.getTotalAmount(), shopkeeperOrderDTO.getTotalAmount());
        assertEquals(shopkeeperOrder.getPaidAmount(), shopkeeperOrderDTO.getPaidAmount());
        assertEquals(shopkeeperOrder.getStatus(), shopkeeperOrderDTO.getStatus());
        assertEquals(shopkeeperOrder.getShopkeeper().getId(), shopkeeperOrderDTO.getShopkeeperId());
        assertEquals(shopkeeperOrder.getShopkeeper().getName(), shopkeeperOrderDTO.getShopkeeperName());


    }


    @Test
    public void getShopkeeperOrder() {
        //given
        ShopkeeperOrderDTO shopkeeperOrderDTO = new ShopkeeperOrderDTO();
        shopkeeperOrderDTO.setDate(LocalDate.now());
        shopkeeperOrderDTO.setId(1111111L);
        shopkeeperOrderDTO.setDueAmount(105450.00);
        shopkeeperOrderDTO.setTotalAmount(1000000.00);
        shopkeeperOrderDTO.setPaidAmount(894550.00);//1000000.00 - 105450.00 = 894550.00
        shopkeeperOrderDTO.setStatus("IN PROGRESS");

        shopkeeperOrderDTO.setShopkeeperId(11L);
        shopkeeperOrderDTO.setShopkeeperName("Ashapura super market!");

        //when
        ShopkeeperOrder shopkeeperOrder = shopkeeperOrderMapper.getShopkeeperOrder(shopkeeperOrderDTO);
        //then
        //make sure data is correct
        assertEquals(shopkeeperOrder.getTotalAmount(), (Double) (shopkeeperOrder.getDueAmount() + shopkeeperOrder.getPaidAmount()) );
        assertEquals(shopkeeperOrder.getDueAmount(), (Double) (shopkeeperOrder.getTotalAmount() - shopkeeperOrder.getPaidAmount()) );
        assertEquals(shopkeeperOrder.getPaidAmount(), (Double) (shopkeeperOrder.getTotalAmount() - shopkeeperOrder.getDueAmount()) );

        //Verify mapping data
        assertEquals(shopkeeperOrder.getId(), shopkeeperOrderDTO.getId());
        assertEquals(shopkeeperOrder.getDate(), shopkeeperOrderDTO.getDate());
        assertEquals(shopkeeperOrder.getDueAmount(), shopkeeperOrderDTO.getDueAmount());
        assertEquals(shopkeeperOrder.getTotalAmount(), shopkeeperOrderDTO.getTotalAmount());
        assertEquals(shopkeeperOrder.getPaidAmount(), shopkeeperOrderDTO.getPaidAmount());
        assertEquals(shopkeeperOrder.getStatus(), shopkeeperOrderDTO.getStatus());
        assertEquals(shopkeeperOrder.getShopkeeper().getId(), shopkeeperOrderDTO.getShopkeeperId());
        assertEquals(shopkeeperOrder.getShopkeeper().getName(), shopkeeperOrderDTO.getShopkeeperName());
    }
}