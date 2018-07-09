package org.product.distributor.mapper;

import com.sun.tools.javac.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.product.distributor.dto.DistributorAreaDTO;
import org.product.distributor.dto.ProductDTO;
import org.product.distributor.dto.ProductQuantityPriceDTO;
import org.product.distributor.model.DistributorArea;
import org.product.distributor.model.Product;
import org.product.distributor.model.ProductBrand;
import org.product.distributor.model.ProductQuantityPrice;

import java.util.ArrayList;

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
        product.setUnitOfMeasure("Liter");

        ProductBrand productBrand = new ProductBrand();
        productBrand.setId(1L);
        productBrand.setName("Chitale Bandu Mithaiwale");

        product.setProductBrand(productBrand);

        ProductQuantityPrice productQuantityPrice = new ProductQuantityPrice();
        productQuantityPrice.setId(101L);
        productQuantityPrice.setQuantity(1D);
        productQuantityPrice.setPurchasePrice(40.50);
        productQuantityPrice.setSellingPrice(42.50);
        //productQuantityPrice.setProduct(product);

        ProductQuantityPrice productQuantityPrice2 = new ProductQuantityPrice();
        productQuantityPrice2.setId(101L);
        productQuantityPrice2.setQuantity(0.250D);
        productQuantityPrice2.setPurchasePrice(10.50);
        productQuantityPrice2.setSellingPrice(12.50);
        //productQuantityPrice2.setProduct(product);

        product.setProductQuantityPriceList(List.of(productQuantityPrice, productQuantityPrice2));

        DistributorArea distributorArea = new DistributorArea();
        distributorArea.setId(1111L);
        distributorArea.setName("Katraj");

        DistributorArea distributorArea2 = new DistributorArea();
        distributorArea2.setId(2222L);
        distributorArea2.setName("Bibwewadi");

        List<DistributorArea>  distributorAreaList = List.of(distributorArea, distributorArea2);

        product.setDistributorAreaList(distributorAreaList);

        //when
        ProductDTO productDTO = productMapper.getProductDTO(product, product.getProductQuantityPriceList(), distributorAreaList);

        //then
        Assert.assertEquals(product.getId(), productDTO.getId());
        Assert.assertEquals(product.getName(), productDTO.getName());
        Assert.assertEquals(product.getUnitOfMeasure(), productDTO.getUnitOfMeasure());
        Assert.assertEquals(product.getSellingPrice(), productDTO.getSellingPrice());
        Assert.assertEquals(product.getPurchasePrice(), productDTO.getPurchasePrice());
        Assert.assertEquals(product.getProductBrand().getId(), productDTO.getBrandId());
        Assert.assertEquals(product.getProductBrand().getName(), productDTO.getBrandName());
        Assert.assertEquals(product.getProductQuantityPriceList().size(), productDTO.getProductQuantityPriceDTOList().size());
        Assert.assertEquals(product.getDistributorAreaList().size(), productDTO.getDistributorAreaDTOList().size());
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
        productDTO.setUnitOfMeasure("Liter");

        ProductQuantityPriceDTO productQuantityPriceDTO = new ProductQuantityPriceDTO();
        productQuantityPriceDTO.setId(101L);
        productQuantityPriceDTO.setQuantity(1D);
        productQuantityPriceDTO.setPurchasePrice(40.50);
        productQuantityPriceDTO.setSellingPrice(42.50);
        productQuantityPriceDTO.setProductId(productDTO.getId());

        ProductQuantityPriceDTO productQuantityPriceDTO2 = new ProductQuantityPriceDTO();
        productQuantityPriceDTO2.setId(101L);
        productQuantityPriceDTO2.setQuantity(0.250D);
        productQuantityPriceDTO2.setPurchasePrice(10.50);
        productQuantityPriceDTO2.setSellingPrice(12.50);
        productQuantityPriceDTO2.setProductId(productDTO.getId());

        List<ProductQuantityPriceDTO> list = List.of(productQuantityPriceDTO, productQuantityPriceDTO2);
        productDTO.setProductQuantityPriceDTOList(list);

        DistributorAreaDTO distributorAreaDTO = new DistributorAreaDTO();
        distributorAreaDTO.setId(1111L);
        distributorAreaDTO.setName("Katraj");

        DistributorAreaDTO distributorAreaDTO2 = new DistributorAreaDTO();
        distributorAreaDTO2.setId(2222L);
        distributorAreaDTO2.setName("Bibwewadi");

        List<DistributorAreaDTO>  distributorAreaDTOList = List.of(distributorAreaDTO, distributorAreaDTO2);

        productDTO.setDistributorAreaDTOList(distributorAreaDTOList);

        //when
        Product product = productMapper.getProduct(productDTO,list, distributorAreaDTOList );
        //then
        Assert.assertEquals(productDTO.getId(), product.getId());
        Assert.assertEquals(productDTO.getName(), product.getName());
        Assert.assertEquals(productDTO.getUnitOfMeasure(), product.getUnitOfMeasure());
        Assert.assertEquals(productDTO.getSellingPrice(), product.getSellingPrice());
        Assert.assertEquals(productDTO.getPurchasePrice(), product.getPurchasePrice());
        Assert.assertEquals(productDTO.getBrandId(), product.getProductBrand().getId());
        Assert.assertEquals(productDTO.getBrandName(), product.getProductBrand().getName());
        Assert.assertEquals(productDTO.getProductQuantityPriceDTOList().size(), product.getProductQuantityPriceList().size());
        Assert.assertEquals(product.getDistributorAreaList().size(), productDTO.getDistributorAreaDTOList().size());

    }
}
