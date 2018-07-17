package org.product.distributor.mapper;

import org.junit.Assert;
import org.junit.Test;
import org.product.distributor.dto.ProductWeightPriceDTO;
import org.product.distributor.model.Product;
import org.product.distributor.model.ProductWeightPrice;

/**
 * Created by vikram on 05/07/18.
 *
 */

public class ProductWeightPriceMapperTest {

    private ProductWeightPriceMapper productWeightPriceMapper = ProductWeightPriceMapper.INSTANCE;

    @Test
    public void getProductWeightPriceDTO(){

        //given
        Product product = new Product();
        product.setName("Gaiche dudh");
        product.setId(1L);
        product.setUnitOfMeasure("Liter");

        ProductWeightPrice productWeightPrice = new ProductWeightPrice();

        productWeightPrice.setProduct(product);
        productWeightPrice.setId(1000L);
        productWeightPrice.setSellingPrice(42.50);
        productWeightPrice.setPurchasePrice(40.50);
        productWeightPrice.setWeight(1.0);

        //when
        ProductWeightPriceDTO productWeightPriceDTO = productWeightPriceMapper.getProductWeightPriceDTO(productWeightPrice);
        //then
        Assert.assertEquals(productWeightPrice.getId(), productWeightPriceDTO.getId());
        Assert.assertEquals(productWeightPrice.getWeight(), productWeightPriceDTO.getWeight());
        Assert.assertEquals(productWeightPrice.getPurchasePrice(), productWeightPriceDTO.getPurchasePrice());
        Assert.assertEquals(productWeightPrice.getSellingPrice(), productWeightPriceDTO.getSellingPrice());
        Assert.assertEquals(productWeightPrice.getProduct().getId(), productWeightPriceDTO.getProductId());
        Assert.assertEquals(productWeightPrice.getProduct().getName(), productWeightPriceDTO.getProductName());
        Assert.assertEquals(productWeightPrice.getProduct().getUnitOfMeasure(), productWeightPriceDTO.getUnitOfMeasure());

    }

    @Test
    public void getProductWeightPrice(){

        //given
        ProductWeightPriceDTO productWeightPriceDTO = new ProductWeightPriceDTO();
        productWeightPriceDTO.setId(111L);
        productWeightPriceDTO.setPurchasePrice(40.50);
        productWeightPriceDTO.setSellingPrice(42.50);
        productWeightPriceDTO.setWeight(1.0);
        productWeightPriceDTO.setProductName("Gaiche Dudh");
        productWeightPriceDTO.setProductId(1000L);
        productWeightPriceDTO.setUnitOfMeasure("Liter");

        //when
        ProductWeightPrice productWeightPrice = productWeightPriceMapper.getProductWeightPrice(productWeightPriceDTO);

        //then
        Assert.assertEquals(productWeightPriceDTO.getId(), productWeightPrice.getId());
        Assert.assertEquals(productWeightPriceDTO.getPurchasePrice(), productWeightPrice.getPurchasePrice());
        Assert.assertEquals(productWeightPriceDTO.getSellingPrice(), productWeightPrice.getSellingPrice());
        Assert.assertEquals(productWeightPriceDTO.getWeight(), productWeightPrice.getWeight());
        Assert.assertEquals(productWeightPriceDTO.getProductId(), productWeightPrice.getProduct().getId());
        Assert.assertEquals(productWeightPriceDTO.getProductName(), productWeightPrice.getProduct().getName());
        Assert.assertEquals(productWeightPrice.getProduct().getUnitOfMeasure(), productWeightPriceDTO.getUnitOfMeasure());

    }


}
