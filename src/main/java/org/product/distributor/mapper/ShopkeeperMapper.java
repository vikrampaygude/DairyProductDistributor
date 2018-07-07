package org.product.distributor.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.product.distributor.dto.ShopkeeperDTO;
import org.product.distributor.model.Shopkeeper;

/**
 * Created by vikram on 04/07/18.
 */
@Mapper
public interface ShopkeeperMapper {

    ShopkeeperMapper INSTANCE = Mappers.getMapper(ShopkeeperMapper.class);

    @Mappings(
            {
                    @Mapping(source = "distributorArea.name", target = "distributorAreaName"),
                    @Mapping(source = "distributorArea.id", target = "distributorAreaId"),
                    @Mapping(source = "distributorArea.distributor.name", target = "distributorName"),
                    @Mapping(source = "distributorArea.distributor.id", target = "distributorId")
            }
    )
    ShopkeeperDTO getShopkeeperDTO(Shopkeeper shopkeeper);
    @Mappings(
            {
                    @Mapping(target = "distributorArea.name", source = "distributorAreaName"),
                    @Mapping(target = "distributorArea.id", source = "distributorAreaId"),
                    @Mapping(target = "distributorArea.distributor.name", source = "distributorName"),
                    @Mapping(target = "distributorArea.distributor.id", source = "distributorId")
            }
    )
    Shopkeeper getShopkeeper(ShopkeeperDTO shopkeeperDTO);

}
