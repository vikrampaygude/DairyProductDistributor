package org.product.distributor.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.product.distributor.dto.DistributorAreaDTO;
import org.product.distributor.dto.ProductDTO;
import org.product.distributor.dto.ProductQuantityPriceDTO;
import org.product.distributor.model.DistributorArea;
import org.product.distributor.model.Product;
import org.product.distributor.model.ProductQuantityPrice;

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
      @Mapping(source = "productQuantityPriceList", target = "productQuantityPriceDTOList"),
      @Mapping(source = "distributorAreaList", target = "distributorAreaDTOList")

    })
    ProductDTO getProductDTO(Product product, List<ProductQuantityPrice> productQuantityPriceList, List<DistributorArea> distributorAreaList);

    @Mappings ({
            @Mapping(target = "productBrand.id", source = "productDTO.brandId"),
            @Mapping(target = "productBrand.name", source = "productDTO.brandName"),
            @Mapping(target = "productQuantityPriceList", source = "productQuantityPriceDTOList"),
            @Mapping(target = "distributorAreaList", source = "distributorAreaDTOList")
    })
    Product getProduct(ProductDTO productDTO, List<ProductQuantityPriceDTO> productQuantityPriceDTOList, List<DistributorAreaDTO> distributorAreaDTOList);

}
