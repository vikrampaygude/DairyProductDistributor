package org.product.distributor.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.product.distributor.dto.DistributorAreaDTO;
import org.product.distributor.model.DistributorArea;

/**
 * Created by vikram on 04/07/18.
 */
@Mapper
public interface DistributorAreaMapper {

    DistributorAreaMapper INSTANCE = Mappers.getMapper(DistributorAreaMapper.class);

    @Mappings({
            @Mapping(source = "distributor.id", target = "distributorId"),
            @Mapping(source = "distributor.name", target = "distributorName")

    })
    DistributorAreaDTO getDistributorAreaDTO(DistributorArea distributorArea);


    @Mappings({
            @Mapping(source = "distributorId", target = "distributor.id"),
            @Mapping(source = "distributorName", target = "distributor.name")

    })
    DistributorArea getDistributorArea(DistributorAreaDTO distributorAreaDto);
}
