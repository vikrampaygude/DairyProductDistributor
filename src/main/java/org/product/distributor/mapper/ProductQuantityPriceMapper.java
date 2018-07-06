package org.product.distributor.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.product.distributor.dto.ProductQuantityPriceDTO;
import org.product.distributor.model.ProductQuantityPrice;

/**
 * Created by vikram on 05/07/18.
 */
@Mapper
public interface ProductQuantityPriceMapper {

    ProductQuantityPriceMapper INSTANCE = Mappers.getMapper(ProductQuantityPriceMapper.class);

    @Mappings({
            @Mapping(source = "product.id", target = "productId"),
            @Mapping(source = "product.name", target = "productName")
    })
    ProductQuantityPriceDTO getProductQuantityPriceDTO(ProductQuantityPrice productQuantityPrice);
    @Mappings({
            @Mapping(target = "product.id", source = "productId"),
            @Mapping(target = "product.name", source = "productName")
    })
    ProductQuantityPrice getProductQuantityPrice(ProductQuantityPriceDTO productQuantityPriceDTO);
}
