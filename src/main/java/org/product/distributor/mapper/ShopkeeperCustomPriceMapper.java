package org.product.distributor.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.product.distributor.dto.ShopkeeperCustomPriceDTO;
import org.product.distributor.model.ShopkeeperCustomPrice;

import java.util.List;

/**
 * Created by vikram on 13/07/18.
 */
@Mapper
public interface ShopkeeperCustomPriceMapper {


    List<ShopkeeperCustomPriceDTO> map(List<ShopkeeperCustomPrice> shopkeeperCustomPrice);

    @Mappings({
            @Mapping(source = "product.id", target = "productId"),
            @Mapping(source = "product.name", target = "productName"),
            @Mapping(source = "shopkeeperOrder.id", target = "shopkeeperOrderId")

    })
    ShopkeeperCustomPriceDTO map(ShopkeeperCustomPrice shopkeeperCustomPrice);

    @Mappings({
            @Mapping(target = "product.id", source = "productId"),
            @Mapping(target = "shopkeeperOrder.id", source = "shopkeeperOrderId")
    })
    ShopkeeperCustomPrice map(ShopkeeperCustomPriceDTO shopkeeperCustomPriceDTO);



}
