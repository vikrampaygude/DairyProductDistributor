package org.product.distributor.mapper;

import org.junit.Assert;
import org.junit.Test;
import org.product.distributor.dto.ProductBrandDTO;
import org.product.distributor.model.ProductBrand;

/**
 * Created by vikram on 05/07/18.
 * Tests
 */
public class ProductBrandMapperTest {

    private ProductBrandMapper productBrandMapper = ProductBrandMapper.INSTANCE;

    @Test
    public void getProductBrandDTO(){
        //given
        ProductBrand productBrand = new ProductBrand();
        productBrand.setId(101L);
        productBrand.setName("Chitale Bandhu Mithaiwale");
        productBrand.setShortName("CBM");

        //when
        ProductBrandDTO productBrandDTO = productBrandMapper.getProductBrandDTO(productBrand);

        //then
        Assert.assertEquals(productBrand.getId(), productBrandDTO.getId());
        Assert.assertEquals(productBrand.getName(), productBrandDTO.getName());
        Assert.assertEquals(productBrand.getShortName(), productBrandDTO.getShortName());
    }

    @Test
    public void getProductBrand(){
        //given
        ProductBrandDTO productBrandDto = new ProductBrandDTO();
        productBrandDto.setId(111L);
        productBrandDto.setName("Chitale Bandhu Mithaiwale");
        productBrandDto.setShortName("CBD");

        //when
        ProductBrand productBrand = productBrandMapper.getProductBrand(productBrandDto);
        //then
        Assert.assertEquals(productBrandDto.getId(), productBrand.getId());
        Assert.assertEquals(productBrandDto.getName(), productBrand.getName());
        Assert.assertEquals(productBrand.getShortName(), productBrandDto.getShortName());

    }

}
