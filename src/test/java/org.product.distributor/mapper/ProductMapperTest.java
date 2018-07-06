package org.product.distributor.mapper;

import org.junit.Assert;
import org.junit.Test;
import org.product.distributor.dto.ProductDTO;
import org.product.distributor.model.Product;
import org.product.distributor.model.ProductBrand;
import org.product.distributor.model.ProductQuantityPrice;

/**
 * Created by vikram on 04/07/18.
 * Tests
 */
public class ProductMapperTest {

    private ProductMapper productMapper = ProductMapper.INSTANCE;

    @Test
    public void getProductDTOTest(){
        //given
        Product product = new Product();
        product.setId(111L);
        product.setName("Mhashiche Dudh");

        ProductBrand productBrand = new ProductBrand();
        productBrand.setId(1L);
        productBrand.setName("Chitale Bandu Mithaiwale");

        product.setProductBrand(productBrand);
        //when
        ProductDTO productDTO = productMapper.getProductDTO(product);

        //then
        Assert.assertEquals(product.getId(), productDTO.getId());
        Assert.assertEquals(product.getName(), productDTO.getName());
        Assert.assertEquals(product.getProductBrand().getId(), productDTO.getBrandId());
        Assert.assertEquals(product.getProductBrand().getName(), productDTO.getBrandName());
    }

    @Test
    public void getProductTest(){
        //given
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(111L);
        productDTO.setName("Mhashiche Dudh");
        productDTO.setBrandId(1L);
        productDTO.setBrandName("Chitale Bandu Mithaiwale");
        productDTO.setSellingPrice(42.50);
        productDTO.setPurchasePrice(40.50);
        //when
        Product product = productMapper.getProduct(productDTO);
        //then
        Assert.assertEquals(productDTO.getId(), product.getId());
        Assert.assertEquals(productDTO.getName(), product.getName());
        Assert.assertEquals(productDTO.getBrandId(), product.getProductBrand().getId());
        Assert.assertEquals(productDTO.getBrandName(), product.getProductBrand().getName());

    }
}
