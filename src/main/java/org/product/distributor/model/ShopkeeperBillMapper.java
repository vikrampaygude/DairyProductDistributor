package org.product.distributor.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.product.distributor.dto.ShopkeeperBillDTO;

/**
 * Created by vikram on 17/07/18.
 */
@Mapper
public interface ShopkeeperBillMapper {

    ShopkeeperBillMapper INSTANCE = Mappers.getMapper(ShopkeeperBillMapper.class);

    @Mappings({
            @Mapping(source = "shopkeeper.id", target = "shopkeeperId"),
            @Mapping(source = "shopkeeper.name", target = "shopkeeperName")
    })
    ShopkeeperBillDTO map(ShopkeeperBill shopkeeperBill);

    @Mappings({
            @Mapping(target = "shopkeeper.id", source = "shopkeeperId"),
            @Mapping(target = "shopkeeper.name", source = "shopkeeperName")
    })
    ShopkeeperBill map(ShopkeeperBillDTO shopkeeperBillDTO);
}
