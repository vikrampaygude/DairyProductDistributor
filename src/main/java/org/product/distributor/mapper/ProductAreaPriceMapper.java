package org.product.distributor.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.product.distributor.dto.ProductAreaPriceDTO;
import org.product.distributor.model.ProductAreaPrice;

/**
 * Created by vikram on 08/08/18.
 */
@Mapper
public interface ProductAreaPriceMapper {

    @Mappings({
            @Mapping(source = "product.id", target = "productId"),
            @Mapping(source = "distributorArea.id", target = "areaId")

    })
    ProductAreaPriceDTO map(ProductAreaPrice productAreaPrice);

    @Mappings({
            @Mapping(target = "product.id", source = "productId"),
            @Mapping(target = "distributorArea.id", source = "areaId")
    })
    ProductAreaPrice map(ProductAreaPriceDTO productAreaPriceDTO);

}
