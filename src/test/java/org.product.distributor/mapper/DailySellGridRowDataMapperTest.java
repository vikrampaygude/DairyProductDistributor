package org.product.distributor.mapper;

import org.junit.Assert;
import org.junit.Test;
import org.product.distributor.dto.DailySellGridRowDataDTO;
import org.product.distributor.model.OrderProduct;
import org.product.distributor.model.Product;
import org.product.distributor.model.Shopkeeper;
import org.product.distributor.model.ShopkeeperOrder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vikram on 09/07/18.
 */

public class DailySellGridRowDataMapperTest {

    private DailySellGridRowDataMapper dailySellGridRowDataMapper = DailySellGridRowDataMapper.INSTANCE;


    @Test
    public void getGridDataDTo() throws Exception {

        //given
        LocalDate date = LocalDate.now();

        Map<ShopkeeperOrder, List<OrderProduct>> shopkeeperOrderMap = new HashMap<>();

        Shopkeeper shopkeeper = new Shopkeeper();
        shopkeeper.setId(1l);
        shopkeeper.setName("Hanuman super market");

        ShopkeeperOrder shopkeeperOrder = new ShopkeeperOrder();
        shopkeeperOrder.setShopkeeper(shopkeeper);
        shopkeeperOrder.setId(2L);
        shopkeeperOrder.setStatus("NEW");
        shopkeeperOrder.setPaidAmount(0.0);
        shopkeeperOrder.setTotalAmount(0.0);

        Product product = new Product();
        product.setId(101L);
        product.setName("C(g)");

        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setShopkeeperOrder(shopkeeperOrder);
        orderProduct.setSellingPrice(100.0);
        orderProduct.setProduct(product);
        orderProduct.setQuantity(10.0);

        List<OrderProduct> orderProductList = new ArrayList<>();
        orderProductList.add(orderProduct);

        shopkeeperOrderMap.put(shopkeeperOrder, orderProductList);

        //when
        DailySellGridRowDataDTO dailySellGridRowDataDTO = dailySellGridRowDataMapper.getGridDataDTo(date, shopkeeperOrderMap);

        //then

        Assert.assertEquals(dailySellGridRowDataDTO.getDate(), date);
        Assert.assertEquals(dailySellGridRowDataDTO.getShopkeeperOrderDTOListMap().size() , shopkeeperOrderMap.size());


    }

}