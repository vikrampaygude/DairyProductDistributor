package org.product.distributor.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.product.distributor.dto.DistributorAreaManagerDTO;
import org.product.distributor.model.DistributorAreaManager;

/**
 * Created by vikram on 05/07/18.
 */
@Mapper
public interface DistributorAreaManagerMapper {

    DistributorAreaManagerMapper INSTANCE = Mappers.getMapper(DistributorAreaManagerMapper.class);


    @Mappings({
            @Mapping(source = "area.name", target = "distributorAreaName"),
            @Mapping(source = "area.id", target = "distributorAreaId")

    })
    DistributorAreaManagerDTO getDistributorAreaManagerDTO(DistributorAreaManager manager);

    @Mappings({
            @Mapping(target = "area.name", source = "distributorAreaName"),
            @Mapping(target = "area.id", source = "distributorAreaId")

    })
    DistributorAreaManager getDistributorAreaManager(DistributorAreaManagerDTO managerDTO);
}
