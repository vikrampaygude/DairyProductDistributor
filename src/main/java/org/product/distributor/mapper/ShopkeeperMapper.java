package org.product.distributor.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.product.distributor.dto.ShopkeeperDTO;
import org.product.distributor.model.Shopkeeper;

/**
 * Created by vikram on 04/07/18.
 */
@Mapper
public interface ShopkeeperMapper {

    ShopkeeperMapper INSTANCE = Mappers.getMapper(ShopkeeperMapper.class);

    ShopkeeperDTO getShopkeeperDTO(Shopkeeper shopkeeper);
    Shopkeeper getShopkeeper(ShopkeeperDTO shopkeeperDTO);

}
