package org.product.distributor.mapper;

import org.junit.Assert;
import org.junit.Test;
import org.product.distributor.dto.OrderProductDTO;
import org.product.distributor.model.OrderProduct;
import org.product.distributor.model.Product;
import org.product.distributor.model.ShopkeeperOrder;

/**
 * Created by vikram on 05/07/18.
 */
public class OrderProductMapperTest {

    private OrderProductMapper orderProductMapper = OrderProductMapper.INSTANCE;

    @Test
    public void getOrderProductDTO(){
        //given
        Product product = new Product();
        product.setId(1L);
        product.setName("Gaiche Dudh");


        ShopkeeperOrder shopkeeperOrder = new ShopkeeperOrder();
        shopkeeperOrder.setId(111L);

        OrderProduct orderProduct = new OrderProduct();

        orderProduct.setId(111111L);
        orderProduct.setProduct(product);
        orderProduct.setShopkeeperOrder(shopkeeperOrder);
        orderProduct.setQuantity(30.0);
        orderProduct.setSellingPrice(40.20);

        //when
        OrderProductDTO orderProductDTO = orderProductMapper.getOrderProductDTO(orderProduct);

        //then
        Assert.assertEquals(orderProduct.getId(), orderProductDTO.getId());
        Assert.assertEquals(orderProduct.getQuantity(), orderProductDTO.getQuantity());
        Assert.assertEquals(orderProduct.getSellingPrice(), orderProductDTO.getSellingPrice());
        Assert.assertEquals(orderProduct.getProduct().getId(), orderProductDTO.getProductId());
        Assert.assertEquals(orderProduct.getProduct().getName(), orderProductDTO.getProductName());
        Assert.assertEquals(orderProduct.getShopkeeperOrder().getId(), orderProductDTO.getOrderId());

    }

    @Test
    public void getOrderProduct(){

        //given
        OrderProductDTO orderProductDTO = new OrderProductDTO();

        orderProductDTO.setId(111L);
        orderProductDTO.setOrderId(1L);
        orderProductDTO.setProductId(10L);
        orderProductDTO.setProductName("Gai Dudh");
        orderProductDTO.setQuantity(50.0);
        orderProductDTO.setSellingPrice(40.50);

        //when
        OrderProduct orderProduct = orderProductMapper.getOrderProduct(orderProductDTO);

        //then
        Assert.assertEquals(orderProduct.getId(), orderProductDTO.getId());
        Assert.assertEquals(orderProduct.getQuantity(), orderProductDTO.getQuantity());
        Assert.assertEquals(orderProduct.getSellingPrice(), orderProductDTO.getSellingPrice());
        Assert.assertEquals(orderProduct.getProduct().getId(), orderProductDTO.getProductId());
        Assert.assertEquals(orderProduct.getProduct().getName(), orderProductDTO.getProductName());
        Assert.assertEquals(orderProduct.getShopkeeperOrder().getId(), orderProductDTO.getOrderId());

    }
}
