package org.product.distributor.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.product.distributor.dto.ProductDTO;
import org.product.distributor.model.Product;

/**
 * Created by vikram on 04/07/18.
 */
@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);


    @Mappings ({
      @Mapping(source = "productBrand.id", target = "brandId"),
      @Mapping(source = "productBrand.name", target = "brandName")
    })
    ProductDTO getProductDTO(Product product);

    @Mappings ({
            @Mapping(target = "productBrand.id", source = "brandId"),
            @Mapping(target = "productBrand.name", source = "brandName")
    })
    Product getProduct(ProductDTO productDTO);

}
