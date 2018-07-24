package org.product.distributor.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.product.distributor.dto.DistributorAreaDTO;
import org.product.distributor.dto.ProductDTO;
import org.product.distributor.dto.ProductWeightPriceDTO;
import org.product.distributor.model.DistributorArea;
import org.product.distributor.model.Product;
import org.product.distributor.model.ProductWeightPrice;

import java.util.List;

/**
 * Created by vikram on 04/07/18.
 */
@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);


    @Mappings ({
      @Mapping(source = "product.productBrand.id", target = "brandId"),
      @Mapping(source = "product.productBrand.name", target = "brandName"),
      @Mapping(source = "productWeightPriceList", target = "productWeightPriceDTOList"),
      @Mapping(source = "distributorAreaList", target = "distributorAreaDTOList")

    })
    ProductDTO getProductDTO(Product product, List<ProductWeightPrice> productWeightPriceList, List<DistributorArea> distributorAreaList);

    @Mappings ({
            @Mapping(target = "productBrand.id", source = "productDTO.brandId"),
            @Mapping(target = "productBrand.name", source = "productDTO.brandName"),
            @Mapping(target = "productWeightPriceList", source = "productWeightPriceDTOList"),
            @Mapping(target = "distributorAreaList", source = "distributorAreaDTOList")
    })
    Product getProduct(ProductDTO productDTO, List<ProductWeightPriceDTO> productWeightPriceDTOList, List<DistributorAreaDTO> distributorAreaDTOList);

    List<ProductDTO> map(List<Product> products);

}
