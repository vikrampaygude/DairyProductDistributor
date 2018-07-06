package org.product.distributor.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.product.distributor.dto.ProductBrandDTO;
import org.product.distributor.model.ProductBrand;

/**
 * Created by vikram on 05/07/18.
 */
@Mapper
public interface ProductBrandMapper {

    ProductBrandMapper INSTANCE = Mappers.getMapper(ProductBrandMapper.class);

    ProductBrandDTO getProductBrandDTO(ProductBrand productBrand);
    ProductBrand getProductBrand(ProductBrandDTO productBrandDTO);

}
