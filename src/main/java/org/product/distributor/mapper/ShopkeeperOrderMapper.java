package org.product.distributor.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.product.distributor.dto.ShopkeeperOrderDTO;
import org.product.distributor.model.ShopkeeperOrder;

import java.util.List;

/**
 * Created by vikram on 05/07/18.
 *
 */
@Mapper
public interface ShopkeeperOrderMapper {

    ShopkeeperOrderMapper INSTANCE = Mappers.getMapper(ShopkeeperOrderMapper.class);

    @Mappings({
            @Mapping(source = "shopkeeper.id", target = "shopkeeperId"),
            @Mapping(source = "shopkeeper.name", target = "shopkeeperName")
    })
    ShopkeeperOrderDTO getShopkeeperOrderDTO(ShopkeeperOrder shopkeeperOrder);


    @Mappings({
            @Mapping(target = "shopkeeper.id",  source = "shopkeeperId"),
            @Mapping(target = "shopkeeper.name", source = "shopkeeperName")
    })
    ShopkeeperOrder getShopkeeperOrder(ShopkeeperOrderDTO shopkeeperOrderDTO);

    List<ShopkeeperOrderDTO> map(List<ShopkeeperOrder> shopkeeperOrders);
}
