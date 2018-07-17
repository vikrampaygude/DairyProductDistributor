package org.product.distributor.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.product.distributor.dto.ProductWeightPriceDTO;
import org.product.distributor.model.ProductWeightPrice;

/**
 * Created by vikram on 05/07/18.
 */
@Mapper
public interface ProductWeightPriceMapper {

    ProductWeightPriceMapper INSTANCE = Mappers.getMapper(ProductWeightPriceMapper.class);

    @Mappings({
            @Mapping(source = "product.id", target = "productId"),
            @Mapping(source = "product.name", target = "productName"),
            @Mapping(source = "product.unitOfMeasure", target = "unitOfMeasure")
    })
    ProductWeightPriceDTO getProductWeightPriceDTO(ProductWeightPrice productWeightPrice);
    @Mappings({
            @Mapping(target = "product.id", source = "productId"),
            @Mapping(target = "product.name", source = "productName"),
            @Mapping(target = "product.unitOfMeasure", source = "unitOfMeasure")
    })
    ProductWeightPrice getProductWeightPrice(ProductWeightPriceDTO productWeightPriceDTO);
}
