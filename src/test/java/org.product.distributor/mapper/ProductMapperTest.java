package org.product.distributor.mapper;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.product.distributor.dto.DistributorAreaDTO;
import org.product.distributor.dto.ProductDTO;
import org.product.distributor.dto.ProductWeightPriceDTO;
import org.product.distributor.model.DistributorArea;
import org.product.distributor.model.Product;
import org.product.distributor.model.ProductBrand;
import org.product.distributor.model.ProductWeightPrice;

import java.util.Arrays;
import java.util.List;

/**
 * Created by vikram on 04/07/18.
 * Tests
 */
public class ProductMapperTest {

    private ProductMapper productMapper = ProductMapper.INSTANCE;

    @Test
    @Ignore
    public void getProductDTOTest(){
        //given
        Product product = new Product();
        product.setId(111L);
        product.setName("Mhashiche Dudh");
        product.setUnitOfMeasure("Liter");
        product.setShortName("MD");

        ProductBrand productBrand = new ProductBrand();
        productBrand.setId(1L);
        productBrand.setName("Chitale Bandu Mithaiwale");

        product.setProductBrand(productBrand);

        ProductWeightPrice productWeightPrice = new ProductWeightPrice();
        productWeightPrice.setId(101L);
        productWeightPrice.setWeight(1D);
        productWeightPrice.setPurchasePrice(40.50);
        productWeightPrice.setSellingPrice(42.50);

        ProductWeightPrice productWeightPrice2 = new ProductWeightPrice();
        productWeightPrice2.setId(101L);
        productWeightPrice2.setWeight(0.250D);
        productWeightPrice2.setPurchasePrice(10.50);
        productWeightPrice2.setSellingPrice(12.50);

        product.setProductWeightPriceList(Arrays.asList(productWeightPrice, productWeightPrice2));

        DistributorArea distributorArea = new DistributorArea();
        distributorArea.setId(1111L);
        distributorArea.setName("Katraj");

        DistributorArea distributorArea2 = new DistributorArea();
        distributorArea2.setId(2222L);
        distributorArea2.setName("Bibwewadi");

        List<DistributorArea>  distributorAreaList = Arrays.asList(distributorArea, distributorArea2);

        product.setDistributorAreaList(distributorAreaList);

        //when
        ProductDTO productDTO = productMapper.getProductDTO(product, product.getProductWeightPriceList(), distributorAreaList);

        //then
        Assert.assertEquals(product.getId(), productDTO.getId());
        Assert.assertEquals(product.getName(), productDTO.getName());
        Assert.assertEquals(product.getShortName(), productDTO.getShortName());
        Assert.assertEquals(product.getUnitOfMeasure(), productDTO.getUnitOfMeasure());
        Assert.assertEquals(product.getSellingPrice(), productDTO.getSellingPrice());
        Assert.assertEquals(product.getPurchasePrice(), productDTO.getPurchasePrice());
        Assert.assertEquals(product.getProductBrand().getId(), productDTO.getBrandId());
        Assert.assertEquals(product.getProductBrand().getName(), productDTO.getBrandName());
        Assert.assertEquals(product.getProductWeightPriceList().size(), productDTO.getProductWeightPriceDTOList().size());
        Assert.assertEquals(product.getDistributorAreaList().size(), productDTO.getDistributorAreaDTOList().size());
    }

    @Test
    public void getProductTest(){
        //given
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(111L);
        productDTO.setName("Mhashiche Dudh");
        productDTO.setShortName("MD");
        productDTO.setBrandId(1L);
        productDTO.setBrandName("Chitale Bandu Mithaiwale");
        productDTO.setSellingPrice(42.50);
        productDTO.setPurchasePrice(40.50);
        productDTO.setUnitOfMeasure("Liter");

        ProductWeightPriceDTO productWeightPriceDTO = new ProductWeightPriceDTO();
        productWeightPriceDTO.setId(101L);
        productWeightPriceDTO.setWeight(1D);
        productWeightPriceDTO.setPurchasePrice(40.50);
        productWeightPriceDTO.setSellingPrice(42.50);
        productWeightPriceDTO.setProductId(productDTO.getId());

        ProductWeightPriceDTO productWeightPriceDTO2 = new ProductWeightPriceDTO();
        productWeightPriceDTO2.setId(101L);
        productWeightPriceDTO2.setWeight(0.250D);
        productWeightPriceDTO2.setPurchasePrice(10.50);
        productWeightPriceDTO2.setSellingPrice(12.50);
        productWeightPriceDTO2.setProductId(productDTO.getId());

        List<ProductWeightPriceDTO> list = Arrays.asList(productWeightPriceDTO, productWeightPriceDTO2);
        productDTO.setProductWeightPriceDTOList(list);

        DistributorAreaDTO distributorAreaDTO = new DistributorAreaDTO();
        distributorAreaDTO.setId(1111L);
        distributorAreaDTO.setName("Katraj");

        DistributorAreaDTO distributorAreaDTO2 = new DistributorAreaDTO();
        distributorAreaDTO2.setId(2222L);
        distributorAreaDTO2.setName("Bibwewadi");

        List<DistributorAreaDTO>  distributorAreaDTOList = Arrays.asList(distributorAreaDTO, distributorAreaDTO2);

        productDTO.setDistributorAreaDTOList(distributorAreaDTOList);

        //when
        Product product = productMapper.getProduct(productDTO,list, distributorAreaDTOList );
        //then
        Assert.assertEquals(productDTO.getId(), product.getId());
        Assert.assertEquals(productDTO.getName(), product.getName());
        Assert.assertEquals(productDTO.getShortName(), product.getShortName());
        Assert.assertEquals(productDTO.getUnitOfMeasure(), product.getUnitOfMeasure());
        Assert.assertEquals(productDTO.getSellingPrice(), product.getSellingPrice());
        Assert.assertEquals(productDTO.getPurchasePrice(), product.getPurchasePrice());
        Assert.assertEquals(productDTO.getBrandId(), product.getProductBrand().getId());
        Assert.assertEquals(productDTO.getBrandName(), product.getProductBrand().getName());
        Assert.assertEquals(productDTO.getProductWeightPriceDTOList().size(), product.getProductWeightPriceList().size());
        Assert.assertEquals(product.getDistributorAreaList().size(), productDTO.getDistributorAreaDTOList().size());

    }
}
