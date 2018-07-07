package org.product.distributor.mapper;

import org.junit.Assert;
import org.junit.Test;
import org.product.distributor.dto.ProductQuantityPriceDTO;
import org.product.distributor.model.Product;
import org.product.distributor.model.ProductQuantityPrice;

/**
 * Created by vikram on 05/07/18.
 *
 */

public class ProductQuantityPriceMapperTest {

    private ProductQuantityPriceMapper productQuantityPriceMapper = ProductQuantityPriceMapper.INSTANCE;

    @Test
    public void getProductQuantityPriceDTO(){

        //given
        Product product = new Product();
        product.setName("Gaiche dudh");
        product.setId(1L);

        ProductQuantityPrice productQuantityPrice = new ProductQuantityPrice();

        productQuantityPrice.setProduct(product);
        productQuantityPrice.setId(1000L);
        productQuantityPrice.setSellingPrice(42.50);
        productQuantityPrice.setPurchasePrice(40.50);
        productQuantityPrice.setQuantity(1.0);

        //when
        ProductQuantityPriceDTO productQuantityPriceDTO = productQuantityPriceMapper.getProductQuantityPriceDTO(productQuantityPrice);
        //then
        Assert.assertEquals(productQuantityPrice.getId(), productQuantityPriceDTO.getId());
        Assert.assertEquals(productQuantityPrice.getQuantity(), productQuantityPriceDTO.getQuantity());
        Assert.assertEquals(productQuantityPrice.getPurchasePrice(), productQuantityPriceDTO.getPurchasePrice());
        Assert.assertEquals(productQuantityPrice.getSellingPrice(), productQuantityPriceDTO.getSellingPrice());
        Assert.assertEquals(productQuantityPrice.getProduct().getId(), productQuantityPriceDTO.getProductId());
        Assert.assertEquals(productQuantityPrice.getProduct().getName(), productQuantityPriceDTO.getProductName());

    }

    @Test
    public void getProductQuantityPrice(){

        //given
        ProductQuantityPriceDTO productQuantityPriceDTO = new ProductQuantityPriceDTO();
        productQuantityPriceDTO.setId(111L);
        productQuantityPriceDTO.setPurchasePrice(40.50);
        productQuantityPriceDTO.setSellingPrice(42.50);
        productQuantityPriceDTO.setQuantity(1.0);
        productQuantityPriceDTO.setProductName("Gaiche Dudh");
        productQuantityPriceDTO.setProductId(1000L);

        //when
        ProductQuantityPrice productQuantityPrice = productQuantityPriceMapper.getProductQuantityPrice(productQuantityPriceDTO);

        //then
        Assert.assertEquals(productQuantityPriceDTO.getId(), productQuantityPrice.getId());
        Assert.assertEquals(productQuantityPriceDTO.getPurchasePrice(), productQuantityPrice.getPurchasePrice());
        Assert.assertEquals(productQuantityPriceDTO.getSellingPrice(), productQuantityPrice.getSellingPrice());
        Assert.assertEquals(productQuantityPriceDTO.getQuantity(), productQuantityPrice.getQuantity());
        Assert.assertEquals(productQuantityPriceDTO.getProductId(), productQuantityPrice.getProduct().getId());
        Assert.assertEquals(productQuantityPriceDTO.getProductName(), productQuantityPrice.getProduct().getName());

    }


}
